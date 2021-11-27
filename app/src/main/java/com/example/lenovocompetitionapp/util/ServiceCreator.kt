package com.example.lenovocompetitionapp.util

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object ServiceCreator {
    var USER_ID: String = "ODcx"

    private const val BASE_URL = "http://smartcity.lenovoedu.com/interface/"

    //构建Retrofit对象
    val retrofit: Retrofit = Retrofit.Builder()
        //设定根地址
        .baseUrl(BASE_URL)
        //设定json转换器
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    //inline fun <reified T> create(): T = create(T::class.java)

    /**
     * 可被await()代替
     */
    suspend fun <T> Call<T>.test() = suspendCoroutine<T> { continuation ->
        enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                if (body != null) {
                    continuation.resume(body)
                }
                else continuation.resumeWithException(RuntimeException("body is null"))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })
    }
}