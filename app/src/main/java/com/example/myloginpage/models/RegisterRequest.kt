package com.example.myloginpage.models

data class RegisterRequest(
    val email: String,
    val name: String,
    val password: String
)