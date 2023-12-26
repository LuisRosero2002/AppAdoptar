package com.example.appfirebaselogin.Data.modelos

import okhttp3.RequestBody
import okhttp3.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitService {
    @GET("Usuario/GetUsuario")
    suspend fun iniciarSesion(
        @Query("username") username: String,
        @Query("password") password: String
    ): VerificarIngresoUsuario

    @POST("Usuario/PostUsuario")
    suspend fun registrase(
        @Body requestBody:Usuario
    ): Usuario
}