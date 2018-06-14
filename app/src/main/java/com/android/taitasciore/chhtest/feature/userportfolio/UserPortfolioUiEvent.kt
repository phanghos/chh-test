package com.android.taitasciore.chhtest.feature.userportfolio

import com.android.taitasciore.chhtest.domain.model.UserCoin
import com.android.taitasciore.chhtest.presentation.base.UiEvent

sealed class UserPortfolioUiEvent : UiEvent {

    class Initial : UserPortfolioUiEvent()

    class Retry : UserPortfolioUiEvent()

    class LoadCoinNames(val coins: List<UserCoin>) : UserPortfolioUiEvent()
}