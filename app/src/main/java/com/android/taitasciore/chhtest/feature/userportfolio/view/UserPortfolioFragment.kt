package com.android.taitasciore.chhtest.feature.userportfolio.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.taitasciore.chhtest.R
import com.android.taitasciore.chhtest.domain.model.Coin
import com.android.taitasciore.chhtest.domain.model.UserCoin
import com.android.taitasciore.chhtest.feature.userportfolio.UserPortfolioUiEvent
import com.android.taitasciore.chhtest.feature.userportfolio.UserPortfolioViewState
import com.android.taitasciore.chhtest.feature.userportfolio.viewmodel.UserPortfolioViewModel
import com.android.taitasciore.chhtest.feature.userportfolio.viewmodel.UserPortfolioViewModelFactory
import com.android.taitasciore.chhtest.presentation.base.BaseView
import com.android.taitasciore.chhtest.util.*
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_user_portfolio.*
import kotlinx.android.synthetic.main.layout_list.*
import javax.inject.Inject

class UserPortfolioFragment : Fragment(), BaseView<UserPortfolioUiEvent, UserPortfolioViewState> {

    private var coins: ArrayList<Coin>? = null

    @Inject lateinit var factory: UserPortfolioViewModelFactory

    private val viewModel: UserPortfolioViewModel by lazy {
        ViewModelProviders.of(this, factory)[UserPortfolioViewModel::class.java]
    }

    private lateinit var userPortfolioAdapter: UserPortfolioAdapter

    companion object {
        fun newInstance(coins: List<Coin>? = null): UserPortfolioFragment {
            val fragment = UserPortfolioFragment()
            val extras = Bundle()
            extras.putSerializable("coins", coins as ArrayList)
            fragment.arguments = extras
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_portfolio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBaseApplication()?.component?.inject(this)
        setTitle("Portfolio")

        coins = arguments?.getSerializable("coins") as ArrayList<Coin>

        setupRecyclerView()

        viewModel.states().observe(this, Observer { render(it!!) })
        viewModel.processUiEvents(events())
    }

    private fun setupRecyclerView() {
        list.isNestedScrollingEnabled = false
        list.addDivider(activity!!, R.drawable.divider)
        userPortfolioAdapter = UserPortfolioAdapter(coins!!)
        list.adapter = userPortfolioAdapter
    }

    private fun initialEvent() = Observable.just(UserPortfolioUiEvent.Initial())

    private fun retryEvent() = RxView.clicks(button_retry).map { UserPortfolioUiEvent.Retry() }

    override fun events(): Observable<UserPortfolioUiEvent> {
        return Observable.merge(initialEvent(), retryEvent())
    }

    override fun render(state: UserPortfolioViewState) {
        when {
            state.loading -> renderLoadingState()
            state.userCoins != null -> showUserCoins(state.userCoins)
            state.error != null -> showError(state.error)
        }
    }

    private fun renderLoadingState() {
        showProgress()
        hideView(layout_error)
        hideView(user_portfolio_text_view_total_price)
    }

    private fun showProgress() {
        showView(progress_wheel)
    }

    private fun hideProgress() {
        hideView(progress_wheel)
    }

    private fun showUserCoins(coins: List<UserCoin>) {
        userPortfolioAdapter.add(coins)
        user_portfolio_text_view_total_price.text = "$${viewModel.getTotalPrice(coins)}"
        hideProgress()
        hideView(layout_error)
        showView(user_portfolio_text_view_total_price)
    }

    private fun showError(error: Throwable) {
        hideProgress()
        showView(layout_error)
        hideView(user_portfolio_text_view_total_price)
    }
}