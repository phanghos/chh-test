package com.android.taitasciore.chhtest.feature.coindetails.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.android.taitasciore.chhtest.feature.coindetails.CoinDetailsActionProcessor
import javax.inject.Inject

/**
 * Factory class for CoinDetailsViewModel because it has a non-empty constructor
 */
class CoinDetailsViewModelFactory @Inject constructor(
        private val coinDetailsActionProcessor: CoinDetailsActionProcessor,
        private val coinDetailsStateReducer: CoinDetailsStateReducer) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CoinDetailsViewModel(coinDetailsActionProcessor, coinDetailsStateReducer) as T
    }
}