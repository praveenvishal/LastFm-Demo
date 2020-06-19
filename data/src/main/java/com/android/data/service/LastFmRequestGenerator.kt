package com.android.data.service

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val PUBLIC_API_KEY_ARG = "api_key"
private const val PUBLIC_API_KEY_ARG_VALUE = "424b29515db473695750e1073a5ae665"
private const val PUBLIC_API_KEY_RESPONSE_FORMAT = "format"
private const val PUBLIC_API_KEY_RESPONSE_FORMAT_ARG = "json"
private const val LAST_FM_BASE_URL = "http://ws.audioscrobbler.com/"
private const val MAX_TRYOUTS = 3
private const val INIT_TRYOUT = 1


class LastFMRequestGenerator {

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .addInterceptor { chain ->
            val defaultRequest = chain.request()
            val defaultHttpUrl = defaultRequest.url()
            val httpUrl = defaultHttpUrl.newBuilder()
                .addQueryParameter(PUBLIC_API_KEY_ARG, PUBLIC_API_KEY_ARG_VALUE)
                .addQueryParameter(PUBLIC_API_KEY_RESPONSE_FORMAT, PUBLIC_API_KEY_RESPONSE_FORMAT_ARG)
                .build()
            val requestBuilder = defaultRequest.newBuilder()
                .url(httpUrl)

            chain.proceed(requestBuilder.build())
        }
        .addInterceptor { chain ->
            val request = chain.request()
            var response = chain.proceed(request)
            var tryOuts = INIT_TRYOUT

            while (!response.isSuccessful && tryOuts < MAX_TRYOUTS) {
                Log.d(
                    this@LastFMRequestGenerator.javaClass.simpleName, "intercept: timeout/connection failure, " +
                            "performing automatic retry ${(tryOuts + 1)}"
                )
                tryOuts++
                response = chain.proceed(request)
            }

            response
        }

    private val builder = Retrofit.Builder()
        .baseUrl(LAST_FM_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    fun <S> createService(serviceClass: Class<S>): S {
        val retrofit = builder.client(httpClient.build()).build()
        return retrofit.create(serviceClass)
    }
}
