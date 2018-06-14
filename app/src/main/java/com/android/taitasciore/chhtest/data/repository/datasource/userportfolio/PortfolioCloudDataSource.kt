package com.android.taitasciore.chhtest.data.repository.datasource.userportfolio

import com.android.taitasciore.chhtest.data.entity.TradeEntity
import com.android.taitasciore.chhtest.data.entity.TradeResponseEntity
import com.android.taitasciore.chhtest.data.entity.UserPortfolioEntity
import com.android.taitasciore.chhtest.data.net.CryptoService
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Cloud data soruce abstraction
 */
class PortfolioCloudDataSource @Inject constructor(private val cryptoService: CryptoService) : PortfolioDataStore {

    override fun getUserPortfolio(): Observable<UserPortfolioEntity> {
        return cryptoService.getUserPortfolio()
    }

    fun makeTrade(tradeEntity: TradeEntity): Observable<TradeResponseEntity> {
        return cryptoService.makeTrade(tradeEntity)
    }
}