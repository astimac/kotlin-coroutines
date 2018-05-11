package com.astimac.coroutines.di.network

import android.content.Context
import com.astimac.coroutines.BuildConfig
import com.astimac.coroutines.api.PostsService
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        const val URL = "https://jsonplaceholder.typicode.com/"
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()

    @Singleton
    @Provides
    fun provideApiKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            val url = request.url()
                    .newBuilder()
                    .build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

    @Singleton
    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    @Singleton
    @Provides
    fun provideOkHttpCache(context: Context): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(context.cacheDir, cacheSize.toLong())
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(cache: Cache,
                            httpLoggingInterceptor: HttpLoggingInterceptor,
                            apiKeyInterceptor: Interceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            client.addNetworkInterceptor(httpLoggingInterceptor)
        }

        client.addNetworkInterceptor(apiKeyInterceptor)
        return client.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory,
                        okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(URL)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
                .client(okHttpClient)
                .build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): PostsService = retrofit.create(PostsService::class.java)
}