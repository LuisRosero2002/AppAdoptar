package com.example.appfirebaselogin.Data.modelos

import com.google.gson.annotations.SerializedName

data class UsuarioRol(
    @SerializedName("id")
    val id:Int,

    @SerializedName("idusuario")
    val idusuario:String,

    @SerializedName("idrol")
    val idrol:Int
)
