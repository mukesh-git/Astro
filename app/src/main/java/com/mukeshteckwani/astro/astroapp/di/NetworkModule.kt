package com.mukeshteckwani.astro.astroapp.di

import com.mukeshteckwani.astro.astroapp.BuildConfig
import com.mukeshteckwani.astro.astroapp.webhelper.AstroAPi
import com.mukeshteckwani.astro.astroapp.webhelper.MockAstroApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("city_id", "10")
                    .build()
                chain.proceed(request)
            })
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideAstroApi(mockAstroApi: MockAstroApi): AstroAPi {
        return mockAstroApi
    }
}
