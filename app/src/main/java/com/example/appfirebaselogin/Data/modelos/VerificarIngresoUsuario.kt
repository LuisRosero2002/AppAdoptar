package com.example.appfirebaselogin.Data.modelos

import com.google.gson.annotations.SerializedName

data class VerificarIngresoUsuario (
    @SerializedName("registrado")
    val registrado:Boolean,

    @SerializedName("muestraformulario")
    val muestraformulario:Boolean,

    @SerializedName("idusuario")
    val  idusuario:String,

)