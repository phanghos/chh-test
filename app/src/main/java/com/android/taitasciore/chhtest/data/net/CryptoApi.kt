package com.android.taitasciore.chhtest.data.net

import com.android.taitasciore.chhtest.data.entity.*
import com.android.taitasciore.chhtest.feature.coindetails.HistoricalListEntity
import io.reactivex.Observable
import retrofit2.http.*

/**
 * API interface
 */
interface CryptoApi {

    companion object {
        val BASE_URL = "https://test.cryptojet.io" // Base API endpoint
    }

    @GET("coins")
    fun getCoins(@Query("page") page: Int): Observable<CoinListResponseEntity>

    @GET("coins/{coin_id}")
    fun getCoin(@Path("coin_id") coinId: Int): Observable<CoinResponseEntity>

    @GET("coins/{coin_id}/historical")
    fun getCoinHistorical(@Path("coin_id") id: Int): Observable<HistoricalListEntity>

    @GET("portfolio")
    fun getUserPortfolio() : Observable<UserPortfolioEntity>

    @POST("portfolio")
    fun makeTrade(@Body tradeEntity: TradeEntity): Observable<TradeResponseEntity>
}