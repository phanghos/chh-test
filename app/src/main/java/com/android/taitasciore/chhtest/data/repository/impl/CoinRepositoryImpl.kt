package com.android.taitasciore.chhtest.data.repository.impl

import com.android.taitasciore.chhtest.data.entity.mapper.CoinHistoricalListMapper
import com.android.taitasciore.chhtest.data.entity.mapper.CoinListResponseMapper
import com.android.taitasciore.chhtest.data.entity.mapper.CoinMapper
import com.android.taitasciore.chhtest.data.repository.CoinRepository
import com.android.taitasciore.chhtest.data.repository.datasource.coinlist.CoinCloudDataSource
import com.android.taitasciore.chhtest.data.repository.datasource.coinlist.CoinDiskDataSource
import com.android.taitasciore.chhtest.domain.model.Coin
import com.android.taitasciore.chhtest.domain.model.CoinList
import com.android.taitasciore.chhtest.domain.model.HistoricalItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * This method repository etches the list of cryptocurrencies by pagination,
 * a single coin detail and the historical data for a particular coin.
 * Returned observables are then mapped to the appropiate domain-layer model
 * using the appropiate mappers
 *
 * The firt time, the cloud data soruce is used, by firing a request to the API.
 * The data returned is then stored locally using Realm under the hood with the abstraction
 * of the data sources
 */
class CoinRepositoryImpl @Inject constructor(
        private val coinCloudDataSource: CoinCloudDataSource,
        private val coinDiskDataSource: CoinDiskDataSource,
        private val coinListResponseMapper: CoinListResponseMapper,
        private val coinMapper: CoinMapper,
        private val coinHistoricalListMapper: CoinHistoricalListMapper) : CoinRepository {

    /**
     * This method fetches the list of cryptocurrencies with pagination
     * @param page Page number
     */
    override fun getCoins(page: Int): Observable<CoinList> {
        val diskObservable = coinDiskDataSource.getCoins(page)

        val cloudObservable = coinCloudDataSource.getCoins(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { coinListResponseEntity -> coinDiskDataSource.storeCoins(coinListResponseEntity.coins) }
                .map { coinListResponseMapper.map(it) }

        return diskObservable.flatMap {
            when {
                it.coins != null -> Observable.just(coinListResponseMapper.map(it))
                else -> cloudObservable
            }
        }
    }

    /**
     * This method fetches a single coin details
     * @param coinId Coin ID
     */
    override fun getCoin(coinId: Int): Observable<Coin> {
        return coinCloudDataSource.getCoin(coinId).map { coinMapper.map(it.coin) }
    }

    /**
     * This method fetches the historical data for a particular coin
     * @param id Coin ID
     */
    override fun getCoinHistorical(id: Int): Observable<List<HistoricalItem>> {
        val diskObservavle = coinDiskDataSource.getCoinHistorical(id)

        val cloudObservable = coinCloudDataSource.getCoinHistorical(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { coinHistoricalListEntity -> coinDiskDataSource.storeHistorical(id, coinHistoricalListEntity) }
                .map { coinHistoricalListMapper.map(it.historical) }

        // Check local data source first
        // In case of failure, fire request to the API
        return diskObservavle.flatMap {
            when {
                it.historical != null -> Observable.just(coinHistoricalListMapper.map(it.historical))
                else -> cloudObservable
            }
        }
    }
}