package com.android.taitasciore.chhtest.data.entity.mapper

import com.android.taitasciore.chhtest.data.entity.CoinEntity
import com.android.taitasciore.chhtest.domain.model.Coin
import javax.inject.Inject

open class CoinMapper @Inject constructor() : Mapper<List<CoinEntity>?, List<Coin>> {

    override fun map(input: List<CoinEntity>?): List<Coin> {
        return input?.map { map(it) }!!
    }

    open fun map(input: CoinEntity): Coin {
        return Coin(
                id = input?.id,
                name = input?.name,
                symbol = input?.symbol,
                priceUsd = input?.priceUsd,
                priceBtc = input?.priceBtc,
                percentChange1h = input?.percentChange1h,
                percentChange7d = input?.percentChange7d,
                percentChange24h = input?.percentChange24h,
                availableSupply = input?.availableSupply,
                totalSupply = input?.totalSupply
        )
    }
}