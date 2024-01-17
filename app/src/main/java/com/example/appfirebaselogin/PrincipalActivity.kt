package com.example.appfirebaselogin


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        val adopcion = findViewById<Button>(R.id.Btn1)//dar en adopcion
        val adoptar = findViewById<Button>(R.id.Btn2)//adoptar

        adopcion.setOnClickListener() {
            val intent = Intent(this, DarEnAdopcionActivity::class.java)
            startActivity(intent)
        }
        adoptar.setOnClickListener() {
            val intent = Intent(this, AdoptarActivity::class.java)
            startActivity(intent)
        }

    }
}