package com.android.taitasciore.chhtest.feature.coinlist.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.Toast
import com.android.taitasciore.chhtest.R
import com.android.taitasciore.chhtest.domain.model.Coin
import com.android.taitasciore.chhtest.feature.coindetails.view.CoinDetailsFragment
import com.android.taitasciore.chhtest.feature.coinlist.CoinListUiEvent
import com.android.taitasciore.chhtest.feature.coinlist.CoinListViewState
import com.android.taitasciore.chhtest.feature.coinlist.viewmodel.CoinListViewModel
import com.android.taitasciore.chhtest.feature.coinlist.viewmodel.CoinListViewModelFactory
import com.android.taitasciore.chhtest.feature.userportfolio.view.UserPortfolioFragment
import com.android.taitasciore.chhtest.presentation.base.BaseView
import com.android.taitasciore.chhtest.presentation.view.BaseFragment
import com.android.taitasciore.chhtest.util.addDivider
import com.android.taitasciore.chhtest.util.hideView
import com.android.taitasciore.chhtest.util.showView
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_coin_list.*
import javax.inject.Inject

class CoinListFragment : BaseFragment(), BaseView<CoinListUiEvent, CoinListViewState> {

    @Inject lateinit var factory: CoinListViewModelFactory

    private val viewModel: CoinListViewModel by lazy {
        ViewModelProviders.of(this, factory).get(CoinListViewModel::class.java)
    }

    private val coinAdapter = CoinAdapter()

    private var currentState: CoinListViewState? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_coin_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBaseApplication()?.component?.inject(this)
        setTitle("Cryptocurrencies")
        setupRecyclerView()
        setHasOptionsMenu(true)

        viewModel.states().observe(this, Observer { it?.let { render(it) } })
        viewModel.processUiEvents(events())
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_coin_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.item_portfolio -> {
                onShowUserPortfolioClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupRecyclerView() {
        coinAdapter.getClickLiveData().observe(this, Observer { onCoinClicked(it!!) })
        list.addDivider(activity!!, R.drawable.divider)
        list.adapter = coinAdapter
    }

    private fun initialEvent() = Observable.just(CoinListUiEvent.Initial())

    private fun loadEvent(): Observable<CoinListUiEvent.Load> {
        return RxRecyclerView.scrollEvents(list).filter {
            val layoutManager = list.layoutManager as LinearLayoutManager
            currentState != null
                    //&& currentState !is CoinListViewState.Loading
                    && layoutManager.findLastCompletelyVisibleItemPosition() >= layoutManager.itemCount - 1
        }
                .distinctUntilChanged()
                .map { CoinListUiEvent.Load() }
    }

    private fun retryEvent() = RxView.clicks(button_retry).map { CoinListUiEvent.Retry() }

    override fun events(): Observable<CoinListUiEvent> {
        return Observable.merge(initialEvent(), loadEvent(), retryEvent())
    }

    override fun render(state: CoinListViewState) {
        if (state.loading) {
            showProgress()
            hideView(layout_error)
        } else {
            state.coins?.let { showCoinList(state.coins)
            }
            state.error?.let {
                when {
                    state.showInitialError!! -> showInitialError()
                    state.showError -> showError(state.error)
                }
            }
        }
        currentState = state
    }

    private fun showProgress() {
        showView(progress_wheel)
    }

    private fun hideProgress() {
        hideView(progress_wheel)
    }

    private fun showCoinList(coins: List<Coin>) {
        hideProgress()
        hideView(layout_error)
        coinAdapter.addCoins(coins)
    }

    private fun showInitialError() {
        hideProgress()
        showView(layout_error)
    }

    private fun showError(error: Throwable) {
        hideProgress()
        hideView(layout_error)
        error.message?.let { Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show() }
        viewModel.sendEvent(CoinListUiEvent.DisableErrorToast())
    }

    private fun onCoinClicked(coin: Coin) {
        activity?.apply { supportFragmentManager.beginTransaction()
                .replace(R.id.container, CoinDetailsFragment.newInstance(coin, viewModel.getCoins()!!))
                .addToBackStack(null).commit() }
    }

    private fun onShowUserPortfolioClicked() {
        if (viewModel.getCoins() != null) {
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, UserPortfolioFragment.newInstance(viewModel.getCoins()))
                    ?.addToBackStack(null)?.commit()
        }
    }
}