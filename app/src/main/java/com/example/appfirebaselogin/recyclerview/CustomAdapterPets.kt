package com.example.ciclapp.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appfirebaselogin.Data.modelos.Perro
import com.example.appfirebaselogin.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class CustomAdapterPets():RecyclerView.Adapter<CustomAdapterPets.ViewHolder>() {

    private val urlBase = "https://gdg0gqfj-80.use2.devtunnels.ms//ApiPerrosMovil/api/"
    private val gson = Gson()
    private val itemType = object : TypeToken<List<Perro>>() {}.type
    private val itemsMascotas: List<Perro> = gson.fromJson("json", itemType)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_layout,viewGroup, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val currentItem = itemsMascotas[i]
        viewHolder.itemTitle.text = currentItem.nombre
        viewHolder.itemDetail.text = currentItem.descripcion

    }
    override fun getItemCount(): Int {
        return itemsMascotas.size
    }
    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var itemTitle:TextView
        var itemDetail:TextView

        init {
            itemTitle = itemView.findViewById(R.id.item_title)
            itemDetail = itemView.findViewById(R.id.item_detail)

        }
    }
    init {
        consultarMascotas()
    }

    private fun consultarMascotas() {

    }

    private fun retrofitApi(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

}