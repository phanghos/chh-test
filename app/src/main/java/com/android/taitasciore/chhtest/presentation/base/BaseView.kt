package com.android.taitasciore.chhtest.presentation.base

import io.reactivex.Observable

/**
 * Base interface which every activity/fragment that contains a ViewModel
 * must implement in order to send and receive events/states from the ViewModel
 */
interface BaseView<E : UiEvent, S : ViewState> {

    fun events(): Observable<E>

    fun render(state: S)
}