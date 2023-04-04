package com.example.myloginpage.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetUserResponse(
    @SerializedName("data")
    val users: ArrayList<GetUser>?,
    val page: Int,
    val per_page: Int,
    val total_pages: Int,
    val totalrecord: Int
): Serializable

data class GetUser(
    val name: String,
    val email: String,
    val profilepicture: String,
    val id: Int,
    val createdat: String,
    val location: String,
    ):Serializable