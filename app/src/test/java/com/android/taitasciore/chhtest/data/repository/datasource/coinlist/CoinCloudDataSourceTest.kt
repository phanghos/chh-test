package com.android.taitasciore.chhtest.data.repository.datasource.coinlist

import com.android.taitasciore.chhtest.data.entity.CoinEntity
import com.android.taitasciore.chhtest.data.entity.CoinListEntity
import com.android.taitasciore.chhtest.data.entity.CoinListResponseEntity
import com.android.taitasciore.chhtest.data.entity.HistoricalItemEntity
import com.android.taitasciore.chhtest.data.net.CryptoService
import com.android.taitasciore.chhtest.feature.coindetails.HistoricalListEntity
import io.reactivex.Observable
import io.realm.RealmList
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks
import java.net.UnknownHostException

class CoinCloudDataSourceTest {

    @Mock
    lateinit var cryptoService: CryptoService

    private lateinit var coinCloudDataSource: CoinCloudDataSource

    @Before
    fun setUp() {
        initMocks(this)
        coinCloudDataSource = CoinCloudDataSource(cryptoService)
    }

    @Test
    fun `getCoins when response is successful`() {
        // Given
        val coinEntity = CoinEntity()
        val realmCoinListEntity = RealmList<CoinEntity>()
        realmCoinListEntity.add(coinEntity)
        val coinListEntity = CoinListEntity(data = realmCoinListEntity)
        val expected = CoinListResponseEntity(coinListEntity)
        `when`(cryptoService.getCoins(1)).thenReturn(Observable.just(expected))

        // When
        val result = coinCloudDataSource.getCoins(1)
        val testObserver = result.test()

        // Then
        verify(cryptoService).getCoins(1)
        testObserver.assertSubscribed()
        testObserver.assertComplete()
        testObserver.assertValue(expected)
        testObserver.assertValueCount(1)
        testObserver.assertNoErrors()
        assertEquals(0, testObserver.errorCount())
    }

    @Test
    fun `getCoins when response fails`() {
        // Given
        val exception = UnknownHostException()
        `when`(cryptoService.getCoins(1)).thenReturn(Observable.error(exception))

        // When
        val result = cryptoService.getCoins(1)
        val testObserver = result.test()

        // Then
        verify(cryptoService).getCoins(1)
        testObserver.assertSubscribed()
        testObserver.assertError(exception)
        assertEquals(1, testObserver.errorCount())
        assertEquals(0, testObserver.valueCount())
    }

    @Test
    fun `getCoinHistorical when response is successful`() {
        // Given
        val historicalItemEntity = HistoricalItemEntity()
        val realmHistoricalListEntity = RealmList<HistoricalItemEntity>()
        realmHistoricalListEntity.add(historicalItemEntity)
        val expected = HistoricalListEntity(historical = realmHistoricalListEntity)
        `when`(cryptoService.getCoinHistorical(1)).thenReturn(Observable.just(expected))

        // When
        val result = coinCloudDataSource.getCoinHistorical(1)
        val testObserver = result.test()

        // Then
        verify(cryptoService).getCoinHistorical(1)
        testObserver.assertSubscribed()
        testObserver.assertComplete()
        testObserver.assertValue(expected)
        testObserver.assertValueCount(1)
        testObserver.assertNoErrors()
        assertEquals(0, testObserver.errorCount())
    }

    @Test
    fun `getCoinHistorical when response fails`() {
        // Given
        val exception = UnknownHostException()
        `when`(cryptoService.getCoinHistorical(1)).thenReturn(Observable.error(exception))

        // When
        val result = cryptoService.getCoinHistorical(1)
        val testObserver = result.test()

        // Then
        verify(cryptoService).getCoinHistorical(1)
        testObserver.assertSubscribed()
        testObserver.assertError(exception)
        assertEquals(1, testObserver.errorCount())
        assertEquals(0, testObserver.valueCount())
    }

    // TODO: Add tests to handle cases when there is some data stored locally
}