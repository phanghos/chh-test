package com.android.taitasciore.chhtest.data.net

import com.android.taitasciore.chhtest.data.entity.*
import com.android.taitasciore.chhtest.feature.coindetails.HistoricalListEntity
import io.reactivex.Observable
import javax.inject.Inject

open class CryptoService @Inject constructor(private val cryptoApi: CryptoApi) : CryptoApi {

    override fun getCoins(page: Int): Observable<CoinListResponseEntity> {
        return cryptoApi.getCoins(page)
    }

    override fun getCoin(coinId: Int): Observable<CoinResponseEntity> {
        return cryptoApi.getCoin(coinId)
    }

    override fun getCoinHistorical(id: Int): Observable<HistoricalListEntity> {
        return cryptoApi.getCoinHistorical(id)
    }

    override fun getUserPortfolio(): Observable<UserPortfolioEntity> {
        return cryptoApi.getUserPortfolio()
    }

    override fun makeTrade(tradeEntity: TradeEntity): Observable<TradeResponseEntity> {
        return cryptoApi.makeTrade(tradeEntity)
    }
}