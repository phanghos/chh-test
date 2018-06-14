package com.android.taitasciore.chhtest.feature.coindetails.model

import com.android.taitasciore.chhtest.domain.model.HistoricalItem
import com.android.taitasciore.chhtest.domain.model.Trade

data class CoinDetailsModel(
        val historical: List<HistoricalItem>? = null,
        val trade: Trade? = null
)