package com.assignment.bookspot.API

import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private var IP: String = "192.168.1.1"
    private var retrofit: Retrofit? = null

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .connectionPool(ConnectionPool(10, 5, TimeUnit.MINUTES))
        .build()

    private fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://$IP:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

    fun updateIP(newIP: String) {
        retrofit = null
        IP = newIP
        println("IP updated to: $newIP")  // Debug log
    }

    val api: APIService
        get() {
            if (retrofit == null) {
                retrofit = getRetrofit()
            }
            return retrofit!!.create(APIService::class.java)
        }
}
