package com.example.appfirebaselogin.Data.modelos

import com.google.gson.annotations.SerializedName

data class login (
    @SerializedName("username")
    val username:String,

    @SerializedName("password")
    val password:String
)