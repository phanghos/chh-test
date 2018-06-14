package com.android.taitasciore.chhtest.data.entity

import com.google.gson.annotations.SerializedName
import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class HistoricalItemEntity(
		@field:SerializedName("price_usd") var priceUsd: Float? = null,
		@field:SerializedName("snapshot_at") var snapshotAt: String? = null
) : RealmModel