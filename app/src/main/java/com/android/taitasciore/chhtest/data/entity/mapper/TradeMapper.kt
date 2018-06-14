package com.android.taitasciore.chhtest.data.entity.mapper

import com.android.taitasciore.chhtest.data.entity.TradeEntity
import com.android.taitasciore.chhtest.domain.model.Trade
import javax.inject.Inject

class TradeMapper @Inject constructor() : Mapper<TradeEntity, Trade> {
    override fun map(input: TradeEntity): Trade {
        return Trade(
                id = input.id,
                coinId = input.coinId,
                priceUsd = input.priceUsd,
                amount = input.amount
        )
    }
}