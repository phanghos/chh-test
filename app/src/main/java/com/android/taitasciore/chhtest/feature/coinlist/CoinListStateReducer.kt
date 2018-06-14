package com.android.taitasciore.chhtest.feature.coinlist

import com.android.taitasciore.chhtest.presentation.base.Resource
import com.android.taitasciore.chhtest.presentation.base.StateReducer
import com.android.taitasciore.chhtest.domain.model.Coin
import com.android.taitasciore.chhtest.domain.model.CoinList
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class CoinListStateReducer @Inject constructor() : StateReducer<CoinList, CoinListViewState> {

    override fun reduce(): BiFunction<CoinListViewState, Resource<CoinList>, CoinListViewState> {
        return BiFunction { previousState, result ->
            when (result.status) {
                Resource.Status.LOADING -> previousState.copy(
                        loading = true,
                        error = null,
                        showProgress = previousState.coins == null,
                        showInitialError = false)
                Resource.Status.SUCCESS -> previousState.copy(
                        loading = false,
                        coins = mergeLists(previousState.coins, result?.data?.data),
                        error = null,
                        showProgress = false,
                        showError = false,
                        showInitialError = false)
                Resource.Status.ERROR -> previousState.copy(
                        loading = false,
                        error = result.error,
                        showProgress = false,
                        showError = previousState.error == null,
                        showInitialError = previousState.coins == null)
            }
        }
    }

    private fun mergeLists(previousList: List<Coin>?, newList: List<Coin>?): List<Coin>? {
        if (previousList == null) {
            return newList
        }
        previousList.let { newList?.forEach { (previousList as ArrayList).add(it) } }
        return previousList
    }
}