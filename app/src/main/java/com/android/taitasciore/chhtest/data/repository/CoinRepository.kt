package com.android.taitasciore.chhtest.data.repository

import com.android.taitasciore.chhtest.domain.model.Coin
import com.android.taitasciore.chhtest.domain.model.CoinList
import com.android.taitasciore.chhtest.domain.model.HistoricalItem
import io.reactivex.Observable

interface CoinRepository {

    fun getCoins(page: Int): Observable<CoinList>

    fun getCoin(coinId: Int): Observable<Coin>

    fun getCoinHistorical(id: Int): Observable<List<HistoricalItem>>
}