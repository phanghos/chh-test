package com.android.taitasciore.chhtest.data.entity.mapper

import com.android.taitasciore.chhtest.data.entity.CoinListEntity
import com.android.taitasciore.chhtest.domain.model.CoinList
import javax.inject.Inject

open class CoinListMapper @Inject constructor(private val coinMapper: CoinMapper) : Mapper<CoinListEntity?, CoinList> {
    override fun map(input: CoinListEntity?): CoinList {
        return CoinList(
                total = input?.total,
                data = coinMapper.map(input?.data),
                currentPage = input?.currentPage,
                prevPageUrl = input?.prevPageUrl,
                nextPageUrl = input?.nextPageUrl
        )
    }
}