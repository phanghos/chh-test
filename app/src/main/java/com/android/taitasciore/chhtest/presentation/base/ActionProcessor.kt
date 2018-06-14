package com.android.taitasciore.chhtest.presentation.base

import io.reactivex.ObservableTransformer

interface ActionProcessor<A : Action, R : Any> {
    fun resultFromAction(): ObservableTransformer<A, Resource<R>>
}