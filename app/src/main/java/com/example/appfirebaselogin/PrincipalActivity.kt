package com.example.appfirebaselogin


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.appfirebaselogin.Data.modelos.Perro
import com.example.appfirebaselogin.Data.modelos.RetrofitService
import com.example.appfirebaselogin.fragments.AdoptarFragment
import com.example.appfirebaselogin.fragments.DarAdopcionFragment
import com.example.ciclapp.recyclerview.CustomAdapterPets
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PrincipalActivity : AppCompatActivity() {

    private lateinit var navigation :BottomNavigationView
    private val bottomNavigationView =  BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.btnAdoptar -> {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<AdoptarFragment>(R.id.fragmetContainer)
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.btnDarAdopcion -> {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<DarAdopcionFragment>(R.id.fragmetContainer)
                }
                return@OnNavigationItemSelectedListener true
            }
            else -> false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        val perroResponse:List<Perro>
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitClient.createService().getPerros()
            Log.i("HOLAA",(response.body()?.get(0)?.nombre).toString())
            runOnUiThread() {
                val perroResponse: List<Perro>? = response.body()

            }
        }

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<AdoptarFragment>(R.id.fragmetContainer)
        }

        navigation = findViewById(R.id.navMenu)
        navigation.setOnNavigationItemSelectedListener(bottomNavigationView)

    }

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