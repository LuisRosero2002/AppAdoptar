package com.example.appfirebaselogin.Data.modelos

import android.icu.lang.UCharacter
import com.google.gson.annotations.SerializedName

data class Persona(

    @SerializedName("id")
    val id: Int,

    @SerializedName("idtipoidentificacion")
    val idtipoidentificacion: Int,

    @SerializedName("cedula")
    val cedula: String,

    @SerializedName("nombre1")
    val nombre1: String,

    @SerializedName("nombre2")
    val nombre2: String,

    @SerializedName("apellido1")
    val apellido1: String,


    @SerializedName("apellido2")
    val apellido2: String,

    @SerializedName("iddepartamentoubicacion")
    val iddepartamentoubicacion: Int,

    @SerializedName("idciudadubicacion")
    val idciudadubicacion: Int,

    @SerializedName("idbarrio")
    val idbarrio: Int,


    @SerializedName("idgenero")
    val idgenero: Int,

    @SerializedName("edad")
    val edad: Int,

    @SerializedName("direccion")
    val direccion: String,

    @SerializedName("esduenio")
    val esduenio: Boolean,

    @SerializedName("escliente")
    val escliente: Boolean,

    @SerializedName("idusuario")
    val idusuario: String

)
