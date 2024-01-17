package com.example.appfirebaselogin.Data.modelos

import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.Part

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

    @Multipart
    @POST("Perro/PostPerro")
    suspend fun registrarPerro(
        @Part("perro") perro: Perro,
        @Part image: MultipartBody.Part
    ): Response<Perro>

    @GET("Persona/GetPersona")
    suspend fun getPersona(
        @Query("idusuario") idusaurio: String,
    ): Persona
    @POST("Persona/PostPersona")
    suspend fun postPersonaInfo(
        @Body requestBody:Persona
    ): Response<Persona>

}