package com.android.taitasciore.chhtest.feature.coindetails

import com.android.taitasciore.chhtest.data.entity.TradeEntity
import com.android.taitasciore.chhtest.presentation.base.Action

sealed class CoinDetailsAction : Action {

    data class LoadHistorical(val id: Int) : CoinDetailsAction()

    data class MakeTrade(val tradeEntity: TradeEntity) : CoinDetailsAction()
}