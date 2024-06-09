package com.example.healthprotectorapplication

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

data class UserDTO(
    val id: Int,
    val loginId: String,
    val password: String,
    val username: String,
    val gender: String,
    val email: String,
    val birthday: String
)

data class ApiResponse(
    val success: String
)

interface ApiService {
    @POST("/api/user/join")
    fun join(
        @Query("id") id: Int,
        @Query("loginId") loginId: String,
        @Query("password") password: String,
        @Query("username") username: String,
        @Query("gender") gender: String,
        @Query("email") email: String,
        @Query("birthday") birthday: String
    ): Call<String>

    @GET("/api/user/join/checkId")
    fun checkId(@Query("loginId") loginId: String): Call<String>

    @GET("/api/user/login")
    fun login(@Query("loginId") loginId: String, @Query("password") password: String): Call<String>
}