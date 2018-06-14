package com.android.taitasciore.chhtest.data.repository.datasource.userportfolio

import com.android.taitasciore.chhtest.data.entity.UserPortfolioEntity
import io.reactivex.Observable

interface PortfolioDataStore {

    fun getUserPortfolio(): Observable<UserPortfolioEntity>
}