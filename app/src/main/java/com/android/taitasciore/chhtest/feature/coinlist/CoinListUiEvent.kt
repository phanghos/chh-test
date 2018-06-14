package com.android.taitasciore.chhtest.feature.coinlist

import com.android.taitasciore.chhtest.presentation.base.UiEvent

sealed class CoinListUiEvent : UiEvent {

    class Initial : CoinListUiEvent()

    class Load : CoinListUiEvent()

    class Retry : CoinListUiEvent()

    class DisableErrorToast : CoinListUiEvent()
}