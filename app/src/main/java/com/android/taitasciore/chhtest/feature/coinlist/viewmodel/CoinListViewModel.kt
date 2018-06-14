package com.android.taitasciore.chhtest.feature.coinlist.viewmodel

import com.android.taitasciore.chhtest.domain.model.Coin
import com.android.taitasciore.chhtest.presentation.base.BaseViewModelImpl
import com.android.taitasciore.chhtest.feature.coinlist.*
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

/**
 * ViewModel for CoinListFragment
 */
class CoinListViewModel(private val coinListActionProcessor: CoinListActionProcessor,
        private val coinListStateReducer: CoinListStateReducer) : BaseViewModelImpl<CoinListUiEvent, CoinListViewState>() {

    private var shouldFetchNextPage = true

    private var currentState: CoinListViewState = CoinListViewState()

    private var coins: List<Coin>? = null

    /**
     * Event filter ensures initial event is only handled once
     */
    private val eventFilter = ObservableTransformer<CoinListUiEvent, CoinListUiEvent> { event ->
        event.publish { shared ->
            Observable.merge(
                    shared.ofType(CoinListUiEvent.Initial::class.java).take(1),
                    shared.filter { it !is CoinListUiEvent.Initial && shouldFetchNextPage }
            )
        }
    }

    init {
        statesObservable = compose()
        addDisposable(statesObservable.doOnNext { currentState = it }.subscribe { statesLiveData.value = it })
    }

    /**
     * Map event to action
     */
    private fun eventFromAction(event: CoinListUiEvent): CoinListAction {
        return when (event) {
            is CoinListUiEvent.Initial -> CoinListAction.LoadCoins()
            is CoinListUiEvent.Load -> CoinListAction.LoadCoins()
            is CoinListUiEvent.Retry -> CoinListAction.LoadCoins()
            is CoinListUiEvent.DisableErrorToast -> CoinListAction.DisableErrorToast()
        }
    }

    override fun compose(): Observable<CoinListViewState> {
        return eventsSubject
                .compose(eventFilter) // Filter event
                .map { eventFromAction(it) } // Map event to action
                .compose(coinListActionProcessor.resultFromAction()) // Process action and generate Resource
                .doOnNext { it.data?.apply { shouldFetchNextPage = nextPageUrl != null} }
                .scan(CoinListViewState(), coinListStateReducer.reduce()) // Reduce Resource to get a ViewState
                .doOnNext { coins = it.coins }
                .replay(1) // Send last emitted item upon subscription
                .autoConnect()
    }

    fun getCoins() = coins

    fun sendEvent(event: CoinListUiEvent) {
        eventsSubject.onNext(event)
    }
}