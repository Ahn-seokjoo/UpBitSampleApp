package com.example.upbitsampleapp.di

import com.example.upbitsampleapp.entities.dto.WebSocketTickerResult
import com.example.upbitsampleapp.repository.ExchangeRepository
import com.example.upbitsampleapp.repository.ExchangeRepositoryImpl
import com.example.upbitsampleapp.repository.MarketApi
import com.example.upbitsampleapp.util.UpBitWebSocket
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.channels.Channel
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
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
        fun providesMarketApi(retrofit: Retrofit): MarketApi = retrofit.create()

        @Singleton
        @Provides
        fun providesClient(): OkHttpClient = OkHttpClient()

        @Singleton
        @Provides
        fun providesRequest(): Request = Request.Builder()
            .url(WEBSOCKET_URL)
            .build()

        // 지양...하자 수정
        @Singleton
        @Provides
        fun providesChannel(): Channel<WebSocketTickerResult.WebSocketTickerResultItem> = Channel()

        @Singleton
        @Provides
        fun providesWebSocket(client: OkHttpClient, request: Request, channel: Channel<WebSocketTickerResult.WebSocketTickerResultItem>) =
            UpBitWebSocket(client, request, channel)


        private const val BASE_URL = "https://api.upbit.com/"
        private const val WEBSOCKET_URL = "wss://api.upbit.com/websocket/v1"
    }
}
