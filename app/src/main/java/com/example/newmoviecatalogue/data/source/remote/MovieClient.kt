package com.example.newmoviecatalogue.data.source.remote

import android.content.Context
import com.example.newmoviecatalogue.BuildConfig.BASE_URL
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieClient {
    fun provideOkHttpClient(
        context: Context
    ): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
        client.addInterceptor(interceptor)
//        client.addInterceptor(ChuckerInterceptor(context))
        client
            .addInterceptor { chain ->
                val request = chain.request()
                val requestBuilder = request.newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(requestBuilder)
            }
        return client.build()
    }

    private fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    fun provideClientMovie(context: Context): MovieService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create(provideGson()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(MovieService::class.java)
    }
}