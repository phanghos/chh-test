package com.android.taitasciore.chhtest.feature.coindetails

import com.android.taitasciore.chhtest.data.entity.TradeEntity
import com.android.taitasciore.chhtest.presentation.base.UiEvent

sealed class CoinDetailsUiEvent : UiEvent {

    data class Initial(val id: Int) : CoinDetailsUiEvent()

    data class RetryHistorical(val id: Int) : CoinDetailsUiEvent()

    data class MakeTrade(val tradeEntity: TradeEntity) : CoinDetailsUiEvent()
}