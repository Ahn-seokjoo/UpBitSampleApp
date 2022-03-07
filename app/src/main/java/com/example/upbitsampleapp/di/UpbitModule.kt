package com.example.upbitsampleapp.di

import com.example.upbitsampleapp.repository.ExchangeRepository
import com.example.upbitsampleapp.repository.ExchangeRepositoryImpl
import com.example.upbitsampleapp.repository.MarketApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UpbitModule {
    @Singleton
    @Binds
    abstract fun bindRepository(exchangeRepository: ExchangeRepositoryImpl): ExchangeRepository

    companion object {

        @Singleton
        @Provides
        fun providesRetrofit(): Retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

        @Singleton
        @Provides
        fun providesMarketApi(retrofit: Retrofit): MarketApi = retrofit.create(MarketApi::class.java)

        private const val BASE_URL = "https://api.upbit.com/"
    }
}
