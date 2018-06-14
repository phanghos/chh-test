package com.android.taitasciore.chhtest.data.entity.mapper

import com.android.taitasciore.chhtest.data.entity.CoinListResponseEntity
import com.android.taitasciore.chhtest.domain.model.CoinList
import javax.inject.Inject

open class CoinListResponseMapper @Inject constructor(private val coinListMapper: CoinListMapper) : Mapper<CoinListResponseEntity?, CoinList> {
    override fun map(input: CoinListResponseEntity?): CoinList {
        return coinListMapper.map(input?.coins)
    }
}