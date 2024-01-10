package com.example.appfirebaselogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.appfirebaselogin.databinding.ActivityInfoBinding
import com.example.appfirebaselogin.databinding.ActivityLoginBinding

class InfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initInfo()

    }

    private fun initInfo() {

        var sexo: String? = null
        val spinner: Spinner = findViewById(R.id.sTidentificacion)
        var opcionSeleccionada: String? = null
        val opciones = arrayOf("C.C", "T.I", "Otro")
        val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, opciones)
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adaptador

        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                opcionSeleccionada = opciones[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Acciones cuando no se selecciona ningún elemento (opcional)
            }
        })

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupInfo)
        val radioButtonId = radioGroup.checkedRadioButtonId

        if (radioButtonId != -1) {
            val radioButton = findViewById<RadioButton>(radioButtonId)
            sexo = radioButton.text.toString()

        } else {


        }


        binding.btnGuardar.setOnClickListener{

            val nombreP = (findViewById<EditText>(R.id.etNombreP)).text.toString()
            val nombreS = (findViewById<EditText>(R.id.etNombreS)).text.toString()
            val apellidoP = (findViewById<EditText>(R.id.etApellidoP)).text.toString()
            val apellidoS = (findViewById<EditText>(R.id.etApellidoS)).text.toString()
            val tipoIdenti = opcionSeleccionada.toString()
            val nIdenti = (findViewById<EditText>(R.id.etTnumeroidenti)).text.toString()
            val telefono = (findViewById<EditText>(R.id.etTelefono)).text.toString()




            val intent = Intent(this, PrincipalActivity::class.java)
            startActivity(intent)
        }
    }
}