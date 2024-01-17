package com.example.ciclapp.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appfirebaselogin.Data.modelos.Perro
import com.example.appfirebaselogin.Data.modelos.RetrofitService
import com.example.appfirebaselogin.InfoActivity
import com.example.appfirebaselogin.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class CustomAdapterPets():RecyclerView.Adapter<CustomAdapterPets.ViewHolder>() {

    private val urlBase = "https://gdg0gqfj-80.use2.devtunnels.ms//ApiPerrosMovil/api/"
    //private val datos: List<Perro> = runBlocking {
        //consultarMascotas()
    //}
    private val gson = Gson()
    private val itemType = object : TypeToken<List<Perro>>() {}.type
    private val itemsMascotas: List<Perro> = gson.fromJson("json", itemType)
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_layout,viewGroup, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        /*val currentItem = datos[i]
        viewHolder.itemNombre.text = currentItem.nombre
        viewHolder.itemRaza.text = currentItem.raza
        viewHolder.itemEdad.text = currentItem.edad
        viewHolder.itemEsterilizado.text = if (currentItem.estaesterilizado) "SI" else "NO"*/
    }
    override fun getItemCount(): Int {
        return itemsMascotas.size
    }
    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var itemNombre:TextView
        var itemRaza:TextView
        var itemEsterilizado:TextView
        var itemEdad:TextView
        init {
            itemNombre = itemView.findViewById(R.id.tNombre)
            itemRaza = itemView.findViewById(R.id.tRaza)
            itemEsterilizado = itemView.findViewById(R.id.tEsterilizado)
            itemEdad = itemView.findViewById(R.id.tEdad)
        }


    }

    /*private suspend fun consultarMascotas(): List<Perro> {
        return CoroutineScope(Dispatchers.IO).async {
            val response = RetrofitClient.createService().getPerros()
            //return@async response.body() ?: emptyList() // Si response.body() es nulo, devuelve una lista vacía
        }.await()
    }
*/
    object RetrofitClient {
        private const val BASE_URL =
            "https://gdg0gqfj-80.use2.devtunnels.ms//ApiPerrosMovil/api/"

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun createService(): RetrofitService {
            return retrofit.create(RetrofitService::class.java)
        }


    }

}