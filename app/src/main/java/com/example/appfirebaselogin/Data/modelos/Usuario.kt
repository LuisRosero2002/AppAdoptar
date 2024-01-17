package com.example.appfirebaselogin.Data.modelos

import com.google.gson.annotations.SerializedName

data class Usuario(
    @SerializedName("username")
    val username:String,

    @SerializedName("password")
    val password:String,

    @SerializedName("email")
    val email:String,

    @SerializedName("telefono")
    val telefono:String,

    @SerializedName("id")
    val id:String = ""
)
