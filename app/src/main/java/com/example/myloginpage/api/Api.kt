package com.example.myloginpage.api

import com.example.myloginpage.models.*
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @Headers("Content_Type:application/json")
    @POST("api/authaccount/registration")
    fun signup(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @POST("api/authaccount/login")
     fun signin(@Body loginRequest: LoginRequest): Call<RegisterResponse>

     @GET("api/users")
     fun getUsers(@Header("Authorization") Authorization: String):Call<GetUserResponse>


}
