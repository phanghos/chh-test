package com.android.taitasciore.chhtest.feature.coinlist

import com.android.taitasciore.chhtest.presentation.base.ViewState
import com.android.taitasciore.chhtest.domain.model.Coin
import java.io.Serializable

data class CoinListViewState(
        val loading: Boolean = false,
        val coins: List<Coin>? = null,
        val error: Throwable? = null,
        val showProgress: Boolean = true,
        val showError: Boolean = true,
        val showInitialError: Boolean? = false
) : ViewState, Serializable