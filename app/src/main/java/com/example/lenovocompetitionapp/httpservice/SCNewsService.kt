package com.example.lenovocompetitionapp.httpservice

import com.example.lenovocompetitionapp.model.SCNews
import com.example.lenovocompetitionapp.util.ServiceCreator.USER_ID
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SCNewsService {
    //http://smartcity.lenovoedu.com/interface/SCNews/getAll?userId=ODcx
    @GET("SCNews/getAll")
    fun getAll(@Query("userId") userId: String = USER_ID): Call<SCNews>
}