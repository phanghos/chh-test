package com.android.taitasciore.chhtest.feature.userportfolio

import com.android.taitasciore.chhtest.data.repository.CoinRepository
import com.android.taitasciore.chhtest.data.repository.PortfolioRepository
import com.android.taitasciore.chhtest.domain.model.UserCoin
import com.android.taitasciore.chhtest.presentation.base.ActionProcessor
import com.android.taitasciore.chhtest.presentation.base.Resource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserPortfolioActionProcessor @Inject constructor(
        private val portfolioRepository: PortfolioRepository,
        private val coinRepository: CoinRepository)
    : ActionProcessor<UserPortfolioAction, List<UserCoin>> {

    private fun loadUserPortfolio(): ObservableTransformer<UserPortfolioAction.LoadUserPortfolio, Resource<List<UserCoin>>> {
        return ObservableTransformer { action ->
            action.flatMap {
                portfolioRepository.getUserPortfolio()
                        .map { Resource.success(it) }
                        .onErrorReturn { Resource.error(it) }
                        .startWith(Resource.loading())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
            }
        }
    }

    /*
    private fun loadCoinNames(): ObservableTransformer<UserPortfolioAction.LoadCoinNames, Resource<Coin>> {
        return ObservableTransformer { action ->
            action.flatMap {
                Observable.fromIterable(it.coins)
                        .flatMap {
                            coinRepository.getCoin(it.coinId!!)
                                    .map { Resource.success(it) }
                                    .onErrorReturn { Resource.error(it) }
                                    .startWith(Resource.loading())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                        }
            }
        }
    }
    */

    override fun resultFromAction(): ObservableTransformer<UserPortfolioAction, Resource<List<UserCoin>>> {
        return ObservableTransformer { action ->
            action.publish { shared ->
                shared.ofType(UserPortfolioAction.LoadUserPortfolio::class.java).compose(loadUserPortfolio())
            }
        }
    }
}