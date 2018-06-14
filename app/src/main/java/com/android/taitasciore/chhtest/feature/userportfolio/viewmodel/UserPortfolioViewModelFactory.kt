package com.android.taitasciore.chhtest.feature.userportfolio.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.android.taitasciore.chhtest.feature.userportfolio.UserPortfolioActionProcessor
import javax.inject.Inject

class UserPortfolioViewModelFactory @Inject constructor(private val userPortfolioActionProcessor: UserPortfolioActionProcessor)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserPortfolioViewModel(userPortfolioActionProcessor) as T
    }
}