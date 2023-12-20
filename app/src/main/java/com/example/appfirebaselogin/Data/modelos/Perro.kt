package com.example.appfirebaselogin.Data.modelos

import android.icu.lang.UCharacter.NumericType
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.util.Date

data class Perro(

    @SerializedName("id")
    val id:Int,

    @SerializedName("raza")
    val raza:String,

    @SerializedName("nombre")
    val nombre:String,

    @SerializedName("peso")
    val peso:BigDecimal,

    @SerializedName("tamanio")
    val tamanio:BigDecimal,

    @SerializedName("edad")
    val edad:String,


    @SerializedName("idgenero")
    val idgenero:Int,

    @SerializedName("descripcion")
    val descripcion:String,


    @SerializedName("fecha")
    val fecha:Date?
)
