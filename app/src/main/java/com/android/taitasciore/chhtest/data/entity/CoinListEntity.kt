package com.android.taitasciore.chhtest.data.entity

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class CoinListEntity(

		@field:SerializedName("first_page_url")
	var firstPageUrl: String? = null,

		@field:SerializedName("path")
	var path: String? = null,

		@field:SerializedName("per_page")
	var perPage: Int? = null,

		@field:SerializedName("total")
	var total: Int? = null,

		@field:SerializedName("data")
	var data: RealmList<CoinEntity>? = null,

		@field:SerializedName("last_page")
	var lastPage: Int? = null,

		@field:SerializedName("last_page_url")
	var lastPageUrl: String? = null,

		@field:SerializedName("next_page_url")
	var nextPageUrl: String? = null,

		@field:SerializedName("from")
	var from: Int? = null,

		@field:SerializedName("to")
	var to: Int? = null,

		@field:SerializedName("prev_page_url")
	var prevPageUrl: String? = null,

		@field:SerializedName("current_page")
        @PrimaryKey
	var currentPage: Int? = null
) : RealmModel