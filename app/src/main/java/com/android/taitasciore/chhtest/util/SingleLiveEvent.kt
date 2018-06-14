package com.android.taitasciore.chhtest.util

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.support.annotation.MainThread
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Kotlin adaptation of Google's Jose Alcérreca's SingleLiveEvent class used
 * in Google's TO-DO sample app: https://github.com/googlesamples/android-architecture
 * for one-shot events i.e. Toasts, Snackbars, etc...
 */
class SingleLiveEvent<T> : LiveData<T>() {

    private val pending = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
        super.observe(owner, Observer {
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        })
    }

    override fun setValue(value: T) {
        pending.set(true);
        super.setValue(value)
    }

    @MainThread
    fun setSingleValue(t: T?) {
        pending.set(true);
        super.setValue(t);
    }
}