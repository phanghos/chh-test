package com.android.taitasciore.chhtest.data.repository.datasource.coinlist

import com.android.taitasciore.chhtest.data.entity.CoinListResponseEntity
import com.android.taitasciore.chhtest.data.entity.CoinResponseEntity
import com.android.taitasciore.chhtest.data.net.CryptoService
import com.android.taitasciore.chhtest.feature.coindetails.HistoricalListEntity
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Cloud data source abstraction
 */
open class CoinCloudDataSource @Inject constructor(private val cryptoService: CryptoService) : CoinDataStore {

    /**
     * This method fethes the list of ryptocurrencies from the API
     * @param page Page number
     */
    override fun getCoins(page: Int): Observable<CoinListResponseEntity> {
        return cryptoService.getCoins(page)
    }

    /**
     * This method fetches the detailw for a particular coin
     * @param coinId Coin ID
     */
    override fun getCoin(coinId: Int): Observable<CoinResponseEntity> {
        return cryptoService.getCoin(coinId)
    }

    /**
     * This method fetches the historical data for a particular coin
     * @param id Coin ID
     */
    override fun getCoinHistorical(id: Int): Observable<HistoricalListEntity> {
        return cryptoService.getCoinHistorical(id)
    }
}