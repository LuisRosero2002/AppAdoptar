package com.example.appfirebaselogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appfirebaselogin.Data.modelos.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AdoptarActivity : AppCompatActivity() {


        private lateinit var adapter:PerroAdapter
        private var listaPokemones=mutableListOf<PerroResult>()
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_adoptar);

            initRecycle()
            consultarTodos()


        }


        private fun initRecycle(){
            adapter=PerroAdapter(listaPokemones)

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

                val respuesta=getRetrofit().create(RetrofitService::class.java).getPerros()
                val pokemones=respuesta
                runOnUiThread(){
                    var lista:List<PerroResult> = emptyList()
                    pokemones?.results?.let{ pokemonlista->
                        lista = pokemonlista.map{perro->
                            PerroResult(it, perro)
                        }
                    }

                    listaPokemones.clear()

                    listaPokemones.addAll(lista)

                    adapter.notifyDataSetChanged()

                }

            }
        }
}