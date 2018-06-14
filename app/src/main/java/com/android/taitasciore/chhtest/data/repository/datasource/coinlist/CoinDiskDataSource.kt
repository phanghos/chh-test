package com.android.taitasciore.chhtest.data.repository.datasource.coinlist

import com.android.taitasciore.chhtest.data.entity.CoinListEntity
import com.android.taitasciore.chhtest.data.entity.CoinListResponseEntity
import com.android.taitasciore.chhtest.data.entity.CoinResponseEntity
import com.android.taitasciore.chhtest.feature.coindetails.HistoricalListEntity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.realm.Realm
import javax.inject.Inject

/**
 * Disk data source abstraction
 */
open class CoinDiskDataSource @Inject constructor(private val realm: Realm) : CoinDataStore {

    /**
     * This method fetches the list of locally stored coins, if any
     * @param page Page numer
     */
    override fun getCoins(page: Int): Observable<CoinListResponseEntity> {
        return Observable.create<CoinListResponseEntity> {
            val coinListEntity = realm.where(CoinListEntity::class.java)
                    .equalTo("currentPage", page)
                    .findFirst()
            it.onNext(CoinListResponseEntity(coinListEntity))
            it.onComplete()
        }
                .subscribeOn(AndroidSchedulers.mainThread())
    }

    override fun getCoin(coinId: Int): Observable<CoinResponseEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * This method fetches the cached historical data for a given coin using Realm
     * @param id Coin ID
     */
    override fun getCoinHistorical(id: Int): Observable<HistoricalListEntity> {
        return Observable.create<HistoricalListEntity> {
            val historicalEntity = realm.where(HistoricalListEntity::class.java)
                    .equalTo("coinId", id)
                    .findFirst()
            if (historicalEntity != null) {
                it.onNext(historicalEntity!!)
                it.onComplete()
            } else {
                it.onNext(HistoricalListEntity())
            }
        }
                .subscribeOn(AndroidSchedulers.mainThread())
    }

    /**
     * This method stores the list of coins locally
     * @param coinListEntity List of coins
     */
    fun storeCoins(coinListEntity: CoinListEntity?) {
        coinListEntity?.let { realm.executeTransaction { realm.insertOrUpdate(coinListEntity) } }
    }

    /**
     * This method stores the historical data for a particular coin
     * @param id Coin ID
     * @param historical  Historical data
     */
    fun storeHistorical(id: Int, historical: HistoricalListEntity) {
        historical.coinId = id
        realm.executeTransaction { realm.insertOrUpdate(historical) }
    }
}