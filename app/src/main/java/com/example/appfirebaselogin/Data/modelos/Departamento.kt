package com.example.appfirebaselogin.Data.modelos

import com.google.gson.annotations.SerializedName

data class Departamento(

    @SerializedName("id")
    val id:Int,

    @SerializedName("nombre")
    val nombre:String,

    @SerializedName("idpais")
    val idpais:Int
)
