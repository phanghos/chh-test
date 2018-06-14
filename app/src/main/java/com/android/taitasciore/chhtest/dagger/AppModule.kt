package com.android.taitasciore.chhtest.dagger

import android.app.Application
import android.content.Context
import com.android.taitasciore.chhtest.data.net.AuthInterceptor
import com.android.taitasciore.chhtest.data.net.CryptoApi
import com.android.taitasciore.chhtest.data.repository.CoinRepository
import com.android.taitasciore.chhtest.data.repository.PortfolioRepository
import com.android.taitasciore.chhtest.data.repository.impl.CoinRepositoryImpl
import com.android.taitasciore.chhtest.data.repository.impl.PortfolioRepositoryImpl
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.realm.Realm
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * App module
 */
@Module(includes = [AppModule.AbstractAppModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(context: Context): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .addInterceptor(ChuckInterceptor(context).showNotification(true))
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(AuthInterceptor())
                .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
                .baseUrl(CryptoApi.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    @Provides
    @Singleton
    fun provideCryptoApi(retrofit: Retrofit): CryptoApi {
        return retrofit.create(CryptoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRealm(): Realm {
        return Realm.getDefaultInstance()
    }

    @Module
    abstract class AbstractAppModule {
        @Binds abstract fun bindCoinRepository(coinRepositoryImpl: CoinRepositoryImpl): CoinRepository
        @Binds abstract fun bindPortfolioRepository(portfolioRepositoryImpl: PortfolioRepositoryImpl): PortfolioRepository
    }
}