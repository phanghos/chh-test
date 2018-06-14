package com.android.taitasciore.chhtest.feature.coinlist

import com.android.taitasciore.chhtest.presentation.base.Action

sealed class CoinListAction : Action {

    class LoadCoins : CoinListAction()

    class DisableErrorToast : CoinListAction()
}