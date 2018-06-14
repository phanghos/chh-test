package com.android.taitasciore.chhtest.data.repository.impl

import com.android.taitasciore.chhtest.data.entity.TradeEntity
import com.android.taitasciore.chhtest.data.entity.UserCoinEntity
import com.android.taitasciore.chhtest.data.entity.mapper.TradeMapper
import com.android.taitasciore.chhtest.data.entity.mapper.UserPortfolioMapper
import com.android.taitasciore.chhtest.data.net.CryptoService
import com.android.taitasciore.chhtest.data.repository.PortfolioRepository
import com.android.taitasciore.chhtest.data.repository.datasource.userportfolio.PortfolioCloudDataSource
import com.android.taitasciore.chhtest.data.repository.datasource.userportfolio.PortfolioDiskDataSource
import com.android.taitasciore.chhtest.domain.model.Trade
import com.android.taitasciore.chhtest.domain.model.UserCoin
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import javax.inject.Inject

/**
 * This repository fetches user portfolio, as well a posting a request with a new trade
 * The data returned is mapped to the appropiate domain-layer model using the appropiate mappers
 *
 * The first time, a request to the API will be fired. Then, the data returned will be stored locally
 * using Realm under the hood.
 */
class PortfolioRepositoryImpl @Inject constructor(
        private val portfolioCloudDataSource: PortfolioCloudDataSource,
        private val portfolioDiskDataSource: PortfolioDiskDataSource,
        private val userPortfolioMapper: UserPortfolioMapper,
        private val tradeMapper: TradeMapper) : PortfolioRepository {

    /**
     * This methdo fetches the user portfolio (list of coins the user owns)
     */
    override fun getUserPortfolio(): Observable<List<UserCoin>> {
        val diskObservable = portfolioDiskDataSource.getUserPortfolio()

        val cloudObservable = portfolioCloudDataSource.getUserPortfolio()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { userPortfolioEntity -> portfolioDiskDataSource.storeUserPortfolio(userPortfolioEntity) }
                .map { userPortfolioMapper.map(it.coins!!) }

        // Check local data source first
        // In case of failure, fire request
        return diskObservable.flatMap {
            when {
                it.coins != null -> Observable.just(userPortfolioMapper.map(it.coins!!))
                else -> cloudObservable
            }
        }
    }

    /**
     * This method makes a POST request to the API, send a trade info
     */
    override fun makeTrade(trade: TradeEntity): Observable<Trade> {
        return portfolioCloudDataSource.makeTrade(trade).map { tradeMapper.map(it.tradeEntity!!) }
    }
}