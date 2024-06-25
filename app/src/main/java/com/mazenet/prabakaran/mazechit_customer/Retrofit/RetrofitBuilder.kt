package com.mazenet.prabakaran.mazechit_customer.Retrofit

import com.mazenet.prabakaran.mazechit_customer.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
//
    private const val URL = BuildConfig.server_url

    // private const val URL = "http://13.233.231.26/boney-belgium/backend/api/"
 //  private const val URL = " http://bestgroups.in/bestchit/backend/api/"

    val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    val okhttp = OkHttpClient.Builder().addInterceptor(logger)

    val builder = Retrofit.Builder()

    val retrofit = builder.baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).client(
        okhttp.build()
    ).build()

    fun <T> buildservice(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }
}