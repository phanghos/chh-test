package com.android.taitasciore.chhtest.data.repository

import com.android.taitasciore.chhtest.data.entity.TradeEntity
import com.android.taitasciore.chhtest.domain.model.Trade
import com.android.taitasciore.chhtest.domain.model.UserCoin
import io.reactivex.Observable

interface PortfolioRepository {

    fun getUserPortfolio(): Observable<List<UserCoin>>

    fun makeTrade(trade: TradeEntity): Observable<Trade>
}