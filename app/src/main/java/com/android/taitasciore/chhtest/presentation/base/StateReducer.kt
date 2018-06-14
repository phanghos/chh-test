package com.android.taitasciore.chhtest.presentation.base

import io.reactivex.functions.BiFunction

interface StateReducer<R : Any, S : ViewState> {
    fun reduce(): BiFunction<S, Resource<R>, S>
}