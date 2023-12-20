package com.example.appfirebaselogin.Data.modelos

import com.google.gson.annotations.SerializedName

data class Genero(

    @SerializedName("id")
    val id:Int,

    @SerializedName("nombre")
    val nombre:String
)
