package com.example.appfirebaselogin

import android.text.Editable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.appfirebaselogin.databinding.ItemPerroBinding
import com.squareup.picasso.Picasso


class PerroViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding=ItemPerroBinding.bind(view)

    fun bind(perro:PerroResult){

        Picasso.get().load("https://gdg0gqfj-80.use2.devtunnels.ms/ApiPerrosMovil/api/Imagenes/GetImagen?url="+perro.image).into(binding.image)

        binding.editTextText.text= Editable.Factory.getInstance().newEditable("Nombre: "+perro.nombre)
        binding.nombre.text=Editable.Factory.getInstance().newEditable("Raza: "+perro.raza)
    }
}
