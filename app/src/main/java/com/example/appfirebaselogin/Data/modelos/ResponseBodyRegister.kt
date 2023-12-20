package com.example.appfirebaselogin.Data.modelos

import com.google.gson.annotations.SerializedName

data class ResponseBodyRegister (

    @SerializedName("id_usuario")
    val id_usuario:String,

    @SerializedName("username")
    val username:String,

    @SerializedName("password")
    val password:String,

)