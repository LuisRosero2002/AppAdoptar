package com.example.appfirebaselogin


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity



class PrincipalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        val Btn1 = findViewById<Button>(R.id.Btn1)//dar en adopcion
        val Btn2 = findViewById<Button>(R.id.Btn2)//adoptar

        Btn1.setOnClickListener(){
            val intent = Intent(this, DarEnAdopcionActivity::class.java)
            startActivity(intent)
        }
        Btn2.setOnClickListener(){
            val intent = Intent(this, AdoptarActivity::class.java)
            startActivity(intent)
        }

    }

}