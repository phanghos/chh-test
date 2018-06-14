package com.android.taitasciore.chhtest.data.entity.mapper

import com.android.taitasciore.chhtest.data.entity.HistoricalItemEntity
import com.android.taitasciore.chhtest.domain.model.HistoricalItem
import javax.inject.Inject

open class CoinHistoricalListMapper @Inject constructor() : Mapper<List<HistoricalItemEntity>?, List<HistoricalItem>> {

    override fun map(input: List<HistoricalItemEntity>?): List<HistoricalItem> {
        return input?.let { input.map { map(it) } } ?: ArrayList()
    }

    private fun map(historicalItemEntity: HistoricalItemEntity?): HistoricalItem {
        return HistoricalItem(historicalItemEntity?.priceUsd, historicalItemEntity?.snapshotAt)
    }
}