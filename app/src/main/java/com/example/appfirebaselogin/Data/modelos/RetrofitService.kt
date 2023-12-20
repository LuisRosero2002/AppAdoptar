package com.example.appfirebaselogin.Data.modelos

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("Pais")
    fun obtenerPerros(): Call<Perro>

}