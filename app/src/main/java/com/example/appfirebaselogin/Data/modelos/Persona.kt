package com.example.appfirebaselogin.Data.modelos

import android.icu.lang.UCharacter
import com.google.gson.annotations.SerializedName

data class Persona(

    @SerializedName("id")
    val id: Int = 6,

    @SerializedName("idtipoidentificacion")
    val idtipoidentificacion: Int,

    @SerializedName("cedula")
    val cedula: String = "",

    @SerializedName("nombre1")
    val nombre1: String = "",

    @SerializedName("nombre2")
    val nombre2: String ="",

    @SerializedName("apellido1")
    val apellido1: String = "",


    @SerializedName("apellido2")
    val apellido2: String = "",

    @SerializedName("iddepartamentoubicacion")
    val iddepartamentoubicacion: Int = 1,

    @SerializedName("idciudadubicacion")
    val idciudadubicacion: Int = 1,

    @SerializedName("idbarrio")
    val idbarrio: Int = 1,

    @SerializedName("idgenero")
    val idgenero: Int = 1,

    @SerializedName("edad")
    val edad: Int = 1,

    @SerializedName("direccion")
    val direccion: String = "",

    @SerializedName("esduenio")
    val esduenio: Boolean = false,

    @SerializedName("escliente")
    val escliente: Boolean =false,

    @SerializedName("idusuario")
    val idusuario: String

)
