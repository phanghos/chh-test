package com.android.taitasciore.chhtest.data.entity.mapper

/**
 * Interface that every mapper class needs to implement in order to map T1 to T2.
 * This also serves to easily map entities from one layer to another one
 */
interface Mapper<T, R> {
    fun map(input: T): R
}