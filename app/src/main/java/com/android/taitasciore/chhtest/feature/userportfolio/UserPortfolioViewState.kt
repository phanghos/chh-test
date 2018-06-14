package com.android.taitasciore.chhtest.feature.userportfolio

import com.android.taitasciore.chhtest.domain.model.UserCoin
import com.android.taitasciore.chhtest.presentation.base.ViewState

data class UserPortfolioViewState(
        val loading: Boolean,
        val userCoins: List<UserCoin>?,
        val error: Throwable?
) : ViewState