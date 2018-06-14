package com.android.taitasciore.chhtest.presentation.base

/**
 * Base class representing a status-bound entity
 * to easily handle state
 */
data class Resource<T>(val status: Status, val data: T?, val error: Throwable?) {

    companion object {

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }

        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(error: Throwable): Resource<T> {
            return Resource(Status.ERROR, null, error)
        }
    }

    enum class Status {
        LOADING, SUCCESS, ERROR;
    }
}