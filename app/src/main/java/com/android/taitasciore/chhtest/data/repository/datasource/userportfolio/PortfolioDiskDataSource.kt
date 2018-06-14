package com.android.taitasciore.chhtest.data.repository.datasource.userportfolio

import com.android.taitasciore.chhtest.data.entity.UserPortfolioEntity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.realm.Realm
import javax.inject.Inject

class PortfolioDiskDataSource @Inject constructor(private val realm: Realm) : PortfolioDataStore {

    override fun getUserPortfolio(): Observable<UserPortfolioEntity> {
        return Observable.create<UserPortfolioEntity> {
            val userPortfolioEntity = realm.where(UserPortfolioEntity::class.java).findFirst()

            if (userPortfolioEntity != null) {
                it.onNext(userPortfolioEntity)
                it.onComplete()
            } else {
                it.onNext(UserPortfolioEntity())
                it.onComplete()
            }
        }
                .subscribeOn(AndroidSchedulers.mainThread())
    }

    fun storeUserPortfolio(userPortfolioEntity: UserPortfolioEntity) {
        realm.executeTransaction { realm.insertOrUpdate(userPortfolioEntity) }
    }
}