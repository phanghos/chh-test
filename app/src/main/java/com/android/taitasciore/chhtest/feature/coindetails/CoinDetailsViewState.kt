package com.android.taitasciore.chhtest.feature.coindetails

import com.android.taitasciore.chhtest.domain.model.HistoricalItem
import com.android.taitasciore.chhtest.domain.model.Trade
import com.android.taitasciore.chhtest.presentation.base.ViewState

data class CoinDetailsViewState(
        val loading: Boolean = false,
        val historical: List<HistoricalItem>? = null,
        val trade: Trade? = null,
        val error: Throwable? = null,
        val showSuccessToast: Boolean = true,
        val showErrorLayout: Boolean = false,
        val showErrorToast: Boolean = true
) : ViewState