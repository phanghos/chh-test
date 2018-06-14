package com.android.taitasciore.chhtest.feature.coinlist

import com.android.taitasciore.chhtest.presentation.base.Resource
import com.android.taitasciore.chhtest.presentation.base.ActionProcessor
import com.android.taitasciore.chhtest.data.repository.CoinRepository
import com.android.taitasciore.chhtest.domain.model.Coin
import com.android.taitasciore.chhtest.domain.model.CoinList
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CoinListActionProcessor @Inject constructor(private val coinRepository: CoinRepository) : ActionProcessor<CoinListAction, CoinList> {

    private var currentPage = 1

    override fun resultFromAction(): ObservableTransformer<CoinListAction, Resource<CoinList>> {
        return ObservableTransformer { action ->
            action.publish { shared ->
                Observable.merge(
                        shared.ofType(CoinListAction.LoadCoins::class.java).compose(loadCoins()),
                        shared.ofType(CoinListAction.DisableErrorToast::class.java).compose(disableErrorToast())
                )
            }
        }
    }

    private fun loadCoins(): ObservableTransformer<CoinListAction.LoadCoins, Resource<CoinList>> {
        return ObservableTransformer { action ->
            action.flatMap {
                coinRepository.getCoins(currentPage)
                        .map {
                            it.nextPageUrl?.let { currentPage++ }
                            Resource.success(it)
                        }
                        .onErrorReturn { Resource.error(it) }
                        .startWith(Resource.loading())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
            }
        }
    }

    private fun disableErrorToast(): ObservableTransformer<CoinListAction.DisableErrorToast, Resource<CoinList>> {
        return ObservableTransformer { action ->
            action.flatMap { Observable.fromCallable { Resource.error<CoinList>(NullPointerException()) } }
        }
    }
}