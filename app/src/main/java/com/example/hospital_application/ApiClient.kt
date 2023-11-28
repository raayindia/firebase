package com.example.hospital_application

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

//    private const val baseUrl = "https://laverretail.com/wp-json/wc/v3/"
//private const val baseUrl = "https://limratechnologies.tech/api/"
    private const val baseUrl = "http://save.al/api/"


    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    //create OkHttp Client
    private val okHttp = OkHttpClient.Builder()
        .callTimeout(180, TimeUnit.SECONDS)//by default it is 10 sec for network operations
        .connectTimeout(180, TimeUnit.SECONDS)
        .readTimeout(180, TimeUnit.SECONDS)
        .writeTimeout(180, TimeUnit.SECONDS)
        .addInterceptor(logger)
    //setLenient
    private var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    //create retrofit builder
    private val builder = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttp.build())

    //create retrofit instance
    private val retrofit = builder.build()
    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }

}