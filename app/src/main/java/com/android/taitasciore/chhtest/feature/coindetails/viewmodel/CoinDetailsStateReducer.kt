package com.android.taitasciore.chhtest.feature.coindetails.viewmodel

import com.android.taitasciore.chhtest.feature.coindetails.CoinDetailsViewState
import com.android.taitasciore.chhtest.feature.coindetails.model.CoinDetailsModel
import com.android.taitasciore.chhtest.presentation.base.Resource
import com.android.taitasciore.chhtest.presentation.base.StateReducer
import io.reactivex.functions.BiFunction
import javax.inject.Inject

open class CoinDetailsStateReducer @Inject constructor() : StateReducer<CoinDetailsModel, CoinDetailsViewState> {
    override fun reduce(): BiFunction<CoinDetailsViewState, Resource<CoinDetailsModel>, CoinDetailsViewState> {
        return BiFunction { previousState, result ->
            val historical = result.data?.historical
            val trade = result.data?.trade
            val showSuccessToast = trade != null
            val showErrorLayout = previousState.historical == null
            val showErrorToast = true

            when (result.status) {
                Resource.Status.LOADING -> previousState.copy(
                        loading = true,
                        showErrorLayout = false,
                        showSuccessToast = false,
                        showErrorToast = false
                )
                Resource.Status.SUCCESS -> previousState.copy(
                        loading = false,
                        historical = historical ?: previousState.historical,
                        trade = trade ?: previousState.trade,
                        error = null,
                        showSuccessToast = showSuccessToast,
                        showErrorLayout = false,
                        showErrorToast = false
                )
                Resource.Status.ERROR -> previousState.copy(
                        loading = false,
                        error = result.error,
                        showSuccessToast = false,
                        showErrorLayout = showErrorLayout,
                        showErrorToast = showErrorToast
                )
            }
        }
    }
}