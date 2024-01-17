package com.example.appfirebaselogin

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.appfirebaselogin.Data.modelos.Persona
import com.example.appfirebaselogin.Data.modelos.RetrofitService
import com.example.appfirebaselogin.databinding.ActivityInfoBinding
import com.example.appfirebaselogin.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class InfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoBinding
    private val urlBase = "https://gdg0gqfj-80.use2.devtunnels.ms//ApiPerrosMovil/api/"

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
        val intent = intent
        val idusuario = intent.getStringExtra("idusuario")

        var opcionSeleccionadaTIdenti: String? = null
        var opcionSeleccionadaCiudad: String? = null


        val opciones = arrayOf("C.C", "T.I", "Otro")
        val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, opciones)
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adaptador

        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                opcionSeleccionadaTIdenti = opciones[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Acciones cuando no se selecciona ningún elemento (opcional)
            }
        })

        val txtname1 = findViewById<EditText>(R.id.etNombreP)
        val txtname2 = findViewById<EditText>(R.id.etNombreS)
        val txtapp1 = findViewById<EditText>(R.id.etApellidoP)
        val txtapp2 = findViewById<EditText>(R.id.etApellidoS)
        val txtidenti = findViewById<EditText>(R.id.etTnumeroidenti)
        val txtdir = findViewById<EditText>(R.id.etDir)

        binding.btnGuardar.setOnClickListener {

            val nombreP = txtname1.text.toString()
            val nombreS = txtname2.text.toString()
            val apellidoP = txtapp1.text.toString()
            val apellidoS = txtapp2.text.toString()
            val tipoIdenti = opcionSeleccionadaTIdenti.toString()
            val nIdenti = txtidenti.text.toString()
            val dirreccion = txtdir.text.toString()

        if (nombreP.isNotEmpty() && nombreS.isNotEmpty() && apellidoP.isNotEmpty() && apellidoS.isNotEmpty() &&
            tipoIdenti.isNotEmpty() && dirreccion.isNotEmpty() && nIdenti.isNotEmpty()){
            CoroutineScope(Dispatchers.IO).launch {

                val dataInput = Persona(
                    nombre1 = nombreP,
                    nombre2 = nombreS,
                    apellido1 = apellidoP,
                    apellido2 = apellidoS,
                    idtipoidentificacion = 1,
                    cedula = nIdenti,
                    idgenero = 1,
                    idciudadubicacion = 1,
                    direccion = dirreccion,
                    idusuario = idusuario.toString()
                )



                val response = RetrofitClient.createService().postPersonaInfo(dataInput)
                runOnUiThread() {
                    if (response.isSuccessful) {
                        val builder = AlertDialog.Builder(this@InfoActivity)
                        builder.setTitle("Perfil Completado")
                        builder.setMessage("Datos registrados")
                        builder.setIcon(R.drawable.verificacion)
                        builder.setPositiveButton("Aceptar") { dialog: DialogInterface, _ ->
                            dialog.dismiss()
                            val intent = Intent(this@InfoActivity, PrincipalActivity::class.java)
                            startActivity(intent)
                        }

                        val dialog = builder.create()
                        dialog.show()
                    }
                    else{
                        val builder = AlertDialog.Builder(this@InfoActivity)
                        builder.setTitle("Error en el registro")
                        builder.setMessage("Ingresa tus datos nuevo")
                        builder.setIcon(R.drawable.cancelar)
                        builder.setPositiveButton("Ok") { dialog: DialogInterface, _ ->
                            dialog.dismiss()
                            val intent = Intent(this@InfoActivity, InfoActivity::class.java)
                            startActivity(intent)
                        }

                        val dialog = builder.create()
                        dialog.show()
                    }

                }

            }
        }else{
            val builder = AlertDialog.Builder(this@InfoActivity)
            builder.setTitle("Error campos vacios !!!")
            builder.setMessage("verifica de nuevo")
            builder.setIcon(R.drawable.cancelar)
            builder.setPositiveButton("Ok") { dialog: DialogInterface, _ ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()

             }
        }
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
