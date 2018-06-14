package com.android.taitasciore.chhtest.domain.model

import java.io.Serializable

data class Coin(
        val priceUsd: Float? = null,
        val symbol: String? = null,
        val totalSupply: Long? = null,
        val jsonMember24hVolumeUsd: Long? = null,
        val createdAt: String? = null,
        val priceBtc: Float? = null,
        val availableSupply: Long? = null,
        val marketCapUsd: Long? = null,
        val percentChange1h: String? = null,
        val percentChange24h: String? = null,
        val updatedAt: String? = null,
        val name: String? = null,
        val rank: Int? = null,
        val id: Int? = null,
        val percentChange7d: String? = null
) : Serializable