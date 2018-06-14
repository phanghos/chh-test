package com.android.taitasciore.chhtest.presentation.base

import android.arch.lifecycle.LiveData
import io.reactivex.Observable

/**
 * Base interface that every ViewModel must implement
 */
interface BaseViewModel<E : UiEvent, S : ViewState> {

    fun processUiEvents(events: Observable<E>)

    fun states(): LiveData<S>
}