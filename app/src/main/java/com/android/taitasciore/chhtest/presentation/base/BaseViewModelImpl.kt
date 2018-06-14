package com.android.taitasciore.chhtest.presentation.base

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

/**
 * Simple BaseViewModel implementation which handles observer unsubscription
 * and sets up necessary Observable, Subject, and LiveData instances used by sub-classes
 */
abstract class BaseViewModelImpl<E : UiEvent, S : ViewState> : ViewModel(), BaseViewModel<E, S> {

    protected val eventsSubject: PublishSubject<E> = PublishSubject.create<E>()

    protected lateinit var statesObservable: Observable<S>

    protected val statesLiveData = MutableLiveData<S>()

    private val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    override fun processUiEvents(events: Observable<E>) {
        events.subscribe(eventsSubject)
    }

    override fun states(): LiveData<S> {
        return statesLiveData
    }

    protected abstract fun compose(): Observable<S>

    protected fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}