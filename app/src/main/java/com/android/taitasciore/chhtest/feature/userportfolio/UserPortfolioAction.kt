package com.android.taitasciore.chhtest.feature.userportfolio

import com.android.taitasciore.chhtest.domain.model.UserCoin
import com.android.taitasciore.chhtest.presentation.base.Action

sealed class UserPortfolioAction : Action {

    class LoadUserPortfolio : UserPortfolioAction()

    data class LoadCoinNames(val coins: List<UserCoin>) : UserPortfolioAction()
}