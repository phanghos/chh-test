package com.android.taitasciore.chhtest.feature.coindetails.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.Toast
import com.android.taitasciore.chhtest.BaseApplication
import com.android.taitasciore.chhtest.R
import com.android.taitasciore.chhtest.domain.model.Coin
import com.android.taitasciore.chhtest.domain.model.DateHistoricalItem
import com.android.taitasciore.chhtest.domain.model.HistoricalItem
import com.android.taitasciore.chhtest.feature.coindetails.CoinDetailsUiEvent
import com.android.taitasciore.chhtest.feature.coindetails.CoinDetailsViewState
import com.android.taitasciore.chhtest.feature.coindetails.DateXAxisFormatter
import com.android.taitasciore.chhtest.feature.coindetails.viewmodel.CoinDetailsViewModel
import com.android.taitasciore.chhtest.feature.coindetails.viewmodel.CoinDetailsViewModelFactory
import com.android.taitasciore.chhtest.presentation.base.BaseView
import com.android.taitasciore.chhtest.util.parseSimpleDate
import com.android.taitasciore.chhtest.util.hideView
import com.android.taitasciore.chhtest.util.setTitle
import com.android.taitasciore.chhtest.util.showView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_coin_details.*
import java.util.*
import javax.inject.Inject

class CoinDetailsFragment : Fragment(), BaseView<CoinDetailsUiEvent, CoinDetailsViewState> {

    private lateinit var coin: Coin

    private lateinit var coins: ArrayList<Coin>

    @Inject lateinit var factory: CoinDetailsViewModelFactory

    private val viewModel: CoinDetailsViewModel by lazy {
        ViewModelProviders.of(this, factory)[CoinDetailsViewModel::class.java]
    }

    private val newTradeDialog = NewTradeDialog()

    companion object {

        fun newInstance(coin: Coin, coins: List<Coin>): CoinDetailsFragment {
            val coinDetailsFragment = CoinDetailsFragment()
            val extras = Bundle()
            extras.putSerializable("coin", coin)
            extras.putSerializable("coins", coins as ArrayList)
            coinDetailsFragment.arguments = extras
            return coinDetailsFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_coin_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as BaseApplication).component.inject(this)

        setHasOptionsMenu(true)

        coin = arguments?.getSerializable("coin") as Coin
        coins = arguments?.getSerializable("coins") as ArrayList<Coin>

        coin?.name?.let { setTitle(coin?.name!!) }

        showCoinDetails()

        viewModel.states().observe(this, Observer { render(it!!) })
        viewModel.processUiEvents(events())
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_coin_details, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.item_make_trade -> {
                showNewTradeDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showNewTradeDialog() {
        val extras = Bundle()
        extras.putSerializable("coin", coin)
        newTradeDialog.arguments = extras
        newTradeDialog.show(activity?.supportFragmentManager, "")
        newTradeDialog.getResultLiveDate().observe(this, Observer { viewModel.sendEvent(CoinDetailsUiEvent.MakeTrade(it!!)) })
    }

    private fun initialEvent() = Observable.just(CoinDetailsUiEvent.Initial(coin.id!!))

    private fun retryEvent() = RxView.clicks(button_retry).map { CoinDetailsUiEvent.RetryHistorical(coin.id!!) }

    override fun events(): Observable<CoinDetailsUiEvent> {
        return Observable.merge(initialEvent(), retryEvent())
    }

    override fun render(state: CoinDetailsViewState) {
        if (state.loading) {
            if (viewModel.wasErrorMsgShown) {
                viewModel.wasErrorMsgShown = false
            }
            if (viewModel.wasSuccessMsgShown) {
                viewModel.wasSuccessMsgShown = false
            }

            renderLoadingState()
        } else {
            hideProgress()
            showView(coin_details_layout_details)
            state.historical?.let { renderHistoricalSuccessState(state.historical) }
            state.trade?.let { renderTradeSuccessState(state) }
            state.error?.let { renderErrorState(state) }
        }
    }

    private fun renderLoadingState() {
        showProgress()
        hideView(coin_details_layout_details)
        hideView(coin_details_layout_error)
        hideView(chart)
    }

    private fun showProgress() {
        showView(progress_wheel)
    }

    private fun hideProgress() {
        hideView(progress_wheel)
    }

    private fun renderHistoricalSuccessState(historical: List<HistoricalItem>) {
        showView(coin_details_layout_details)
        hideView(coin_details_layout_error)
        showView(coin_details_layout_details)
        showHistorical(historical)
    }

    private fun renderTradeSuccessState(state: CoinDetailsViewState) {
        showView(coin_details_layout_details)
        hideView(coin_details_layout_error)

        if (state.showSuccessToast) {
            Toast.makeText(activity!!, "Trade made successfully!", Toast.LENGTH_SHORT).show()
            viewModel.wasSuccessMsgShown = true
        }
    }

    private fun showHistorical(historical: List<HistoricalItem>) {
        val mappedHistorical = historical.map { DateHistoricalItem(it.priceUsd!!,  it.snapshotAt!!.parseSimpleDate()) }
        val historicalMap = hashMapOf<Date, Float>()
        val whitelist = arrayListOf<Date>()
        var counter = 0

        val xAxis = chart.xAxis
        xAxis.valueFormatter = DateXAxisFormatter()
        xAxis.labelRotationAngle = -45f
        xAxis.textSize = 7f

        mappedHistorical.forEach { currentItem ->
            if (!whitelist.contains(currentItem.snapshotAt)) {
                val listForDay = mappedHistorical.takeLast(mappedHistorical.size - counter).takeWhile { currentItem.snapshotAt == it.snapshotAt }
                counter += listForDay.size
                var priceSum = 0f
                listForDay.forEach { priceSum += it.priceUsd }
                historicalMap[currentItem.snapshotAt] = priceSum / listForDay.size
                whitelist.add(currentItem.snapshotAt)
            }
        }

        val entries = mutableListOf<Entry>()
        whitelist.forEach {
            val timestamp = it.time
            val float = "${timestamp / 100000}".toFloat()
            entries.add(Entry(float, historicalMap[it]!!))
        }

        val dataset = LineDataSet(entries, "Price in USD")
        dataset.setDrawCircles(false)
        dataset.setDrawCircleHole(false)
        val lineData = LineData(dataset)
        lineData.setValueTextColor(Color.TRANSPARENT)
        chart.data = lineData
        dataset.lineWidth = 2f
        chart.invalidate()
        showView(chart)
    }

    private fun showCoinDetails() {
        coin_details_coin_name.text = coin.name
        text_view_coin_symbol.text = "(${coin.symbol})"
        text_view_coin_price_usd.text = "$${coin.priceUsd}"
        text_view_coin_price_btc.text = "${coin.priceBtc}"
        text_view_coin_change_1h.text = coin.percentChange1h + "%"
        text_view_coin_change_24h.text = coin.percentChange24h + "%"
        text_view_coin_change_7d.text = coin.percentChange7d + "%"
        text_view_coin_total_supply.text = "${coin.totalSupply}"
        text_view_coin_available_supply.text = "${coin.availableSupply}"
    }

    private fun renderErrorState(state: CoinDetailsViewState) {
        if (state.showErrorLayout) {
            showView(coin_details_layout_error)
        }

        if (state.showErrorToast && !viewModel.wasErrorMsgShown) {
            Toast.makeText(activity!!, "Something went wrong!", Toast.LENGTH_SHORT).show()
            viewModel.wasErrorMsgShown = true
        }
    }
}