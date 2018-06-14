package com.android.taitasciore.chhtest.dagger

import android.app.Application
import com.android.taitasciore.chhtest.BaseApplication
import com.android.taitasciore.chhtest.feature.coindetails.view.CoinDetailsFragment
import com.android.taitasciore.chhtest.feature.coinlist.view.CoinListFragment
import com.android.taitasciore.chhtest.feature.userportfolio.view.UserPortfolioFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * App component
 */
@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class])
interface AppComponent : AndroidInjector<BaseApplication> {

    fun inject(coinListFragment: CoinListFragment)

    fun inject(coinDetailsFragment: CoinDetailsFragment)

    fun inject(userPortfolioFragment: UserPortfolioFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(app: Application): Builder
        fun build(): AppComponent
    }
}