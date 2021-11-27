package com.example.lenovocompetitionapp.httpservice

import com.example.lenovocompetitionapp.model.*
import com.example.lenovocompetitionapp.util.ServiceCreator.USER_ID
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface SCHospitalService {
    //http://smartcity.lenovoedu.com/interface/SCHospital/getAll?userId=ODcx
    @GET("SCHospital/getAll")
    fun getSCHospitalAll(@Query("userId") userId: String = USER_ID): Call<SCHospital>

    //http://smartcity.lenovoedu.com/interface/SCHospital/getInfo?userId=ODcx&id=1
    @GET("SCHospital/getInfo")
    fun getSCHospitalInfo(
        @Query("userId") userId: String = USER_ID,
        @Query("id") id: Int
    ): Call<SCHospital>

    //http://smartcity.lenovoedu.com/interface/SCAdministrative/getAll?userId=ODcx
    @GET("SCAdministrative/getAll")
    fun getSCAdministrativeAll(@Query("userId") userId: String = USER_ID): Call<SCAdministrative>

    //http://smartcity.lenovoedu.com/interface/SCOutpatient/getAll?userId=ODcx
    @GET("SCOutpatient/getAll")
    fun getSCOutpatientAll(@Query("userId") userId: String = USER_ID): Call<SCOutpatient>

    @GET("SCDoctor/search")
    fun getSCDoctorSearch(
        @Query("userId") userId: String = USER_ID,
        @Query("SCHospitalId") hId: Int,
        @Query("SCAdministrativeId") aId: Int,
        @Query("SCOutpatientId") oId: Int
    ): Call<SCDoctor>

    @GET("SCVisit/search")
    fun getSCVisitSearch(
        @Query("userId") userId: String = USER_ID,
        @Query("SCHospitalId") hId: Int,
        @Query("SCAdministrativeId") aId: Int,
        @Query("SCOutpatientId") oId: Int
    ): Call<SCVisit>

    @POST("dataInterface/UserWorkEnvironmental/updateLightSwitch")
    fun postTest(@Body data: UserData): Call<ResponseBody>

    @DELETE("data/{id}")
    fun deleteTest(@Path("id") id: Int): Call<ResponseBody>
}