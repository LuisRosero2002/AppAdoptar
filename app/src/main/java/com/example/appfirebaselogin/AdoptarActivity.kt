package com.example.appfirebaselogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.appfirebaselogin.Data.modelos.RetrofitService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AdoptarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adoptar)

        val Rview = findViewById<RecyclerView>(R.id.RV)


    }

}