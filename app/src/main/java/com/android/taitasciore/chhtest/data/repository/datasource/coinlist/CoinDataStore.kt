package com.android.taitasciore.chhtest.data.repository.datasource.coinlist

import com.android.taitasciore.chhtest.data.entity.CoinListResponseEntity
import com.android.taitasciore.chhtest.data.entity.CoinResponseEntity
import com.android.taitasciore.chhtest.feature.coindetails.HistoricalListEntity
import io.reactivex.Observable

interface CoinDataStore {

    fun getCoins(page: Int): Observable<CoinListResponseEntity>

    fun getCoin(coinId: Int): Observable<CoinResponseEntity>

    fun getCoinHistorical(id: Int): Observable<HistoricalListEntity>
}