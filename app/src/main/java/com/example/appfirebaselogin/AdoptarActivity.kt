package com.example.appfirebaselogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appfirebaselogin.Data.modelos.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AdoptarActivity : AppCompatActivity() {


        private lateinit var adapter:PerroAdapter
        private var listaPokemones=mutableListOf<PerroResult>()
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_adoptar) // Asegúrate de tener el nombre de tu diseño correcto

            initRecycle()
            consultarTodos()



        }


        private fun initRecycle(){
            adapter = PerroAdapter(listaPokemones)
            val recyclerView: RecyclerView = findViewById(R.id.lista) // Reemplaza "lista" con el ID de tu RecyclerView en el diseño
            recyclerView.layoutManager = GridLayoutManager(this, 1)
            recyclerView.adapter = adapter
        }


        private fun getRetrofit():Retrofit{
            var url="https://gdg0gqfj-80.use2.devtunnels.ms/ApiPerrosMovil/api/"
            return  Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        //esta funcion consume la api
        private fun consultarTodos(){
            //este consumo a la api lo hago en un hilo segundo plano por eso es IO, se utiliza corrutinas
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val respuesta = getRetrofit().create(RetrofitService::class.java).getPerros()
                    val pokemones = respuesta

                    withContext(Dispatchers.Main) {
                        var lista: List<PerroResult> = emptyList()
                        lista = pokemones.map {
                            PerroResult(
                                it.id, it.raza, it.nombre, it.peso, it.tamanio, it.edad, it.idgenero,
                                it.descripcion, it.estaesterilizado, it.image
                            )
                        }

                        listaPokemones.clear()
                        listaPokemones.addAll(lista)
                        adapter.notifyDataSetChanged()
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    // Manejar el error, mostrar un mensaje al usuario, etc.
                }

            }
        }
}