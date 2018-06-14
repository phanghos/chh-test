package com.android.taitasciore.chhtest.feature.coindetails

import com.android.taitasciore.chhtest.data.repository.CoinRepository
import com.android.taitasciore.chhtest.data.repository.PortfolioRepository
import com.android.taitasciore.chhtest.domain.model.CoinList
import com.android.taitasciore.chhtest.feature.coindetails.model.CoinDetailsModel
import com.android.taitasciore.chhtest.feature.coinlist.CoinListAction
import com.android.taitasciore.chhtest.presentation.base.ActionProcessor
import com.android.taitasciore.chhtest.presentation.base.Resource
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CoinDetailsActionProcessor @Inject constructor(
        private val coinRepository: CoinRepository,
        private val portfolioRepository: PortfolioRepository)
    : ActionProcessor<CoinDetailsAction, CoinDetailsModel> {

    private fun loadHistorical(): ObservableTransformer<CoinDetailsAction.LoadHistorical, Resource<CoinDetailsModel>> {
        return ObservableTransformer { action ->
            action.flatMap {
                coinRepository.getCoinHistorical(it.id)
                        .map { Resource.success(CoinDetailsModel(historical = it)) }
                        .onErrorReturn { Resource.error(it) }
                        .startWith(Resource.loading())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
            }
        }
    }

    private fun makeTrade(): ObservableTransformer<CoinDetailsAction.MakeTrade, Resource<CoinDetailsModel>> {
        return ObservableTransformer { action ->
            action.flatMap {
                portfolioRepository.makeTrade(it.tradeEntity)
                        .map { Resource.success(CoinDetailsModel(trade = it)) }
                        .onErrorReturn { Resource.error(it) }
                        .startWith(Resource.loading())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
            }
        }
    }

    override fun resultFromAction(): ObservableTransformer<CoinDetailsAction, Resource<CoinDetailsModel>> {
        return ObservableTransformer { action ->
            action.publish { shared ->
                Observable.merge(
                        shared.ofType(CoinDetailsAction.LoadHistorical::class.java).compose(loadHistorical()),
                        shared.ofType(CoinDetailsAction.MakeTrade::class.java).compose(makeTrade())
                )
            }
        }
    }
}