package com.android.taitasciore.chhtest.feature.coindetails.viewmodel

import com.android.taitasciore.chhtest.feature.coindetails.CoinDetailsAction
import com.android.taitasciore.chhtest.feature.coindetails.CoinDetailsActionProcessor
import com.android.taitasciore.chhtest.feature.coindetails.CoinDetailsUiEvent
import com.android.taitasciore.chhtest.feature.coindetails.CoinDetailsViewState
import com.android.taitasciore.chhtest.presentation.base.BaseViewModelImpl
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

/**
 * ViewModel for CoinDetailsFragment
 */
class CoinDetailsViewModel @Inject constructor(
        private val coinDetailsActionProcessor: CoinDetailsActionProcessor,
        private val coinDetailsStateReducer: CoinDetailsStateReducer)
    : BaseViewModelImpl<CoinDetailsUiEvent, CoinDetailsViewState>() {

    var wasSuccessMsgShown = false

    var wasErrorMsgShown = false

    /**
     * This method filters incoming events. This is particularly useful for initial events.
     * This ensures that initial events are handled once and only once. Further incoming
     * CoinDetailsUiEvent's wont propagate to the action processor
     */
    private val eventFilter = ObservableTransformer<CoinDetailsUiEvent, CoinDetailsUiEvent> { event ->
        event.publish { shared ->
            Observable.merge(
                    shared.ofType(CoinDetailsUiEvent.Initial::class.java).take(1),
                    shared.filter { it !is CoinDetailsUiEvent.Initial }
            )
        }
    }

    init {
        statesObservable = compose()
        addDisposable(statesObservable.subscribe { statesLiveData.value = it })
    }

    /**
     * This method maps the UiEvent to its corresponding Action
     * CoinDetailsUiEvent -> CoinDetailsAction
     * @param event Event
     * @returns Mapped Action
     */
    private fun actionFromEvent(event: CoinDetailsUiEvent): CoinDetailsAction {
        return when (event) {
            is CoinDetailsUiEvent.Initial -> CoinDetailsAction.LoadHistorical(event.id)
            is CoinDetailsUiEvent.RetryHistorical -> CoinDetailsAction.LoadHistorical(event.id)
            is CoinDetailsUiEvent.MakeTrade -> CoinDetailsAction.MakeTrade(event.tradeEntity)
        }
    }

    // This is where the magic happens
    override fun compose(): Observable<CoinDetailsViewState> {
        return eventsSubject
                .compose(eventFilter) // Filter events i.e. initial
                .map { actionFromEvent(it) } // Map event to action
                .compose(coinDetailsActionProcessor.resultFromAction()) // Process action and generate a Resource
                .scan(CoinDetailsViewState(), coinDetailsStateReducer.reduce()) // State reducer in action
                .replay(1) // Post last emmited event upon subscribing
                .autoConnect(0)
    }

    fun sendEvent(coinDetailsUiEvent: CoinDetailsUiEvent) {
        eventsSubject.onNext(coinDetailsUiEvent)
    }
}