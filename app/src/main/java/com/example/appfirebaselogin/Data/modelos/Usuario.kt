package com.example.appfirebaselogin.Data.modelos

import com.google.gson.annotations.SerializedName

data class Usuario(
    @SerializedName("id")
    val id:String,

    @SerializedName("nombre")
    val nombre:String,

    @SerializedName("username")
    val username:String,

    @SerializedName("password")
    val password:String,

    @SerializedName("email")
    val email:String,

    @SerializedName("telefono")
    val telefono:String
)
