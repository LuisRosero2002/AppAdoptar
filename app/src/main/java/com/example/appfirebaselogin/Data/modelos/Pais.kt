package com.example.appfirebaselogin.Data.modelos

import com.google.gson.annotations.SerializedName

data class Pais(

    @SerializedName("id")
    val id:Int,

    @SerializedName("nombre")
    val nombre:String
)
