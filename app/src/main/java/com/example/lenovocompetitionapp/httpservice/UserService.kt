package com.example.lenovocompetitionapp.httpservice

import com.example.lenovocompetitionapp.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    //http://smartcity.lenovoedu.com/interface/getUserInfoLoginNameAndPwd?loginName=fengjia&password=17520439994
    @GET("getUserInfoLoginNameAndPwd")
    fun getLoginUser(
        @Query("loginName") loginName: String,
        @Query("password") password: String
    ): Call<User>
}