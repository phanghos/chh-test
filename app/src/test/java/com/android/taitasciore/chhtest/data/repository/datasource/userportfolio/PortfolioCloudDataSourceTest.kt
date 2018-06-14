package com.android.taitasciore.chhtest.data.repository.datasource.userportfolio

import com.android.taitasciore.chhtest.data.entity.UserCoinEntity
import com.android.taitasciore.chhtest.data.entity.UserPortfolioEntity
import com.android.taitasciore.chhtest.data.net.CryptoService
import io.reactivex.Observable
import io.realm.RealmList
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks
import java.net.UnknownHostException

class PortfolioCloudDataSourceTest {

    @Mock
    lateinit var cryptoService: CryptoService

    private lateinit var portfolioCloudDataSource: PortfolioCloudDataSource

    @Before
    fun setUp() {
        initMocks(this)
        portfolioCloudDataSource = PortfolioCloudDataSource(cryptoService)
    }

    @Test
    fun `getUserPortfolio when response is successful`() {
        // Given
        val userCoinEntity = UserCoinEntity()
        val realmUserCoinListEntity = RealmList<UserCoinEntity>()
        realmUserCoinListEntity.add(userCoinEntity)
        val expected = UserPortfolioEntity(realmUserCoinListEntity)
        `when`(cryptoService.getUserPortfolio()).thenReturn(Observable.just(expected))

        // When
        val result = portfolioCloudDataSource.getUserPortfolio()
        val testObserver = result.test()

        // Then
        verify(cryptoService).getUserPortfolio()
        testObserver.assertSubscribed()
        testObserver.assertComplete()
        testObserver.assertValue(expected)
        testObserver.assertValueCount(1)
        testObserver.assertNoErrors()
        assertEquals(0, testObserver.errorCount())
    }

    @Test
    fun `getUserPortfolio when response fails`() {
        // Given
        val exception = UnknownHostException()
        `when`(cryptoService.getUserPortfolio()).thenReturn(Observable.error(exception))

        // When
        val result = cryptoService.getUserPortfolio()
        val testObserver = result.test()

        // Then
        verify(cryptoService).getUserPortfolio()
        testObserver.assertSubscribed()
        testObserver.assertError(exception)
        assertEquals(1, testObserver.errorCount())
        assertEquals(0, testObserver.valueCount())
    }

    // TODO: Add tests to handle cases when there is some data stored locally
}