package com.example.myloginpage.models

import java.io.Serializable

data class RegisterResponse(
    val code: Int,
    val data: Data?,
    val message: String
):Serializable

data class Data(
    val Email: String,
    val Id: Int,
    val Name: String,
    val Token: String
)

