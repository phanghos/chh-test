package com.android.taitasciore.chhtest.domain.model

data class CoinList(
        val firstPageUrl: String? = null,
        val path: String? = null,
        val perPage: Int? = null,
        val total: Int? = null,
        val data: List<Coin>? = null,
        val lastPage: Int? = null,
        val lastPageUrl: String? = null,
        val nextPageUrl: String? = null,
        val from: Int? = null,
        val to: Int? = null,
        val prevPageUrl: String? = null,
        val currentPage: Int? = null
)