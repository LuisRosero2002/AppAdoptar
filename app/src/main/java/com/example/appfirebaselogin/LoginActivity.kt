package com.example.appfirebaselogin

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.appfirebaselogin.Data.modelos.RetrofitService
import com.example.appfirebaselogin.Data.modelos.VerificarIngresoUsuario
import com.example.appfirebaselogin.Data.modelos.login
import com.example.appfirebaselogin.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Response
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import kotlin.math.log


class LoginActivity : AppCompatActivity() {
    // Write a message to the database
    private lateinit var binding: ActivityLoginBinding
    val urlBase = "https://gdg0gqfj-80.use2.devtunnels.ms//ApiPerrosMovil/api/"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        screenSplash.setKeepOnScreenCondition{false}

        setContentView(R.layout.activity_login)

        val btnIngresar = findViewById<Button>(R.id.btnIngresar)
        val btnRegistrarse = findViewById<Button>(R.id.btnRegistrarse)

        btnRegistrarse.setOnClickListener(){
            val intent = Intent(this, RegistrarseActivity::class.java)
            startActivity(intent)
        }

        btnIngresar.setOnClickListener(){

            val txtUsuario = findViewById<EditText>(R.id.etUser)
            val usuario:String = txtUsuario.text.toString().trim()

            val txtPassword = findViewById<EditText>(R.id.etPassword)
            val contrasena:String = txtPassword.text.toString().trim()

            val context: Context = this

            if (usuario.isNotEmpty() && contrasena.isNotEmpty()){

                CoroutineScope(Dispatchers.IO).launch {
                    val call = retrofitApi().create(RetrofitService::class.java)
                        .iniciarSesion(usuario, contrasena)

                    runOnUiThread() {
                        Log.i("Form",call.muestraformulario.toString())
                        Log.i("REg",call.registrado.toString())

                        if(call.registrado.toString() == "true"){
                            val builder = AlertDialog.Builder(context)
                            builder.setTitle("Bienvenido")
                            builder.setMessage("Gracias por utilizar nuestra app")
                            builder.setIcon(R.drawable.verificacion)
                            builder.setPositiveButton("Aceptar") { dialog: DialogInterface, _ ->
                                dialog.dismiss() // Cierra el diálogo cuando se hace clic en el botón "Aceptar"
                                if(call.muestraformulario.toString() == "true"){
                                    Log.i("Hola",call.muestraformulario.toString())
                                }else{
                                    val intent = Intent(context, PrincipalActivity::class.java)
                                    startActivity(intent)
                                }
                            }
                            val dialog = builder.create()
                            dialog.show()




                        }else{
                            val builder = AlertDialog.Builder(context)
                            builder.setTitle("Usuario no registrado")
                            builder.setMessage("Por favor, Registrese")
                            builder.setIcon(R.drawable.cancelar)
                            builder.setPositiveButton("Aceptar") { dialog: DialogInterface, _ ->
                                dialog.dismiss() // Cierra el diálogo cuando se hace clic en el botón "Aceptar"
                            }

                            val dialog = builder.create()
                            dialog.show()
                        }


                    }
                }
            }
            else{
                // Mostrar un AlertDialog si algún campo está vacío
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Campos vacíos")
                builder.setMessage("Por favor, completa todos los campos.")
                builder.setIcon(R.drawable.cancelar)
                builder.setPositiveButton("Aceptar") { dialog: DialogInterface, _ ->
                    dialog.dismiss() // Cierra el diálogo cuando se hace clic en el botón "Aceptar"
                }

                val dialog = builder.create()
                dialog.show()
            }
        }

    }

     private fun retrofitApi():Retrofit{
        return Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }
}