package com.android.taitasciore.chhtest.domain.model

data class TradeRequest(
        val priceUsd: String? = null,
        val amount: String? = null,
        val notes: Any? = null,
        val coinId: String? = null,
        val updatedAt: String? = null,
        val userId: Int? = null,
        val tradedAt: String? = null,
        val totalUsd: Double? = null,
        val createdAt: String? = null,
        val id: Int? = null
)