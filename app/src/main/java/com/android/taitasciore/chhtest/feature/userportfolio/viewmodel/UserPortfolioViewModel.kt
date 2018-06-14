package com.android.taitasciore.chhtest.feature.userportfolio.viewmodel

import com.android.taitasciore.chhtest.domain.model.Coin
import com.android.taitasciore.chhtest.domain.model.UserCoin
import com.android.taitasciore.chhtest.feature.userportfolio.UserPortfolioAction
import com.android.taitasciore.chhtest.feature.userportfolio.UserPortfolioActionProcessor
import com.android.taitasciore.chhtest.feature.userportfolio.UserPortfolioUiEvent
import com.android.taitasciore.chhtest.feature.userportfolio.UserPortfolioViewState
import com.android.taitasciore.chhtest.presentation.base.BaseViewModelImpl
import com.android.taitasciore.chhtest.presentation.base.Resource
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class UserPortfolioViewModel @Inject constructor(private val userPortfolioActionProcessor: UserPortfolioActionProcessor)
    : BaseViewModelImpl<UserPortfolioUiEvent, UserPortfolioViewState>() {

    private val eventFilter = ObservableTransformer<UserPortfolioUiEvent, UserPortfolioUiEvent> { event ->
        event.publish { shared ->
            Observable.merge(
                    shared.ofType(UserPortfolioUiEvent.Initial::class.java).take(1),
                    shared.filter { it !is UserPortfolioUiEvent.Initial }
            )
        }
    }

    init {
        statesObservable = compose()
        addDisposable(statesObservable.subscribe { statesLiveData.value = it })
    }

    private fun actionFromEvent(event: UserPortfolioUiEvent) : UserPortfolioAction {
        return when (event) {
            is UserPortfolioUiEvent.Initial -> UserPortfolioAction.LoadUserPortfolio()
            is UserPortfolioUiEvent.Retry -> UserPortfolioAction.LoadUserPortfolio()
            is UserPortfolioUiEvent.LoadCoinNames -> UserPortfolioAction.LoadCoinNames(event.coins)
        }
    }

    override fun compose(): Observable<UserPortfolioViewState> {
        return eventsSubject
                .compose(eventFilter)
                .map { actionFromEvent(it) }
                .compose(userPortfolioActionProcessor.resultFromAction())
                .map {
                    when (it.status) {
                        Resource.Status.LOADING -> UserPortfolioViewState(true, null, null)
                        Resource.Status.SUCCESS -> UserPortfolioViewState(false, it.data, null)
                        Resource.Status.ERROR -> UserPortfolioViewState(false, null, it.error)
                    }
                }
                .replay(1)
                .autoConnect(0)
    }

    fun sendEvent(event: UserPortfolioUiEvent) {
        eventsSubject.onNext(event)
    }

    fun getTotalPrice(userCoins: List<UserCoin>): Float {
        var priceSum = 0.0f
        userCoins.forEach { priceSum += it.amount!! }
        return priceSum
    }
}