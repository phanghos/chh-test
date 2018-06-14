package com.android.taitasciore.chhtest.feature.coinlist.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.android.taitasciore.chhtest.feature.coinlist.CoinListActionProcessor
import com.android.taitasciore.chhtest.feature.coinlist.CoinListStateReducer
import javax.inject.Inject

class CoinListViewModelFactory @Inject constructor(
        private val coinListActionProcessor: CoinListActionProcessor,
        private val coinListStateReducer: CoinListStateReducer) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CoinListViewModel(coinListActionProcessor, coinListStateReducer) as T
    }
}