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
import com.example.appfirebaselogin.Data.modelos.RetrofitService
import com.example.appfirebaselogin.Data.modelos.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RegistrarseActivity : AppCompatActivity() {

    val urlBase = "https://gdg0gqfj-80.use2.devtunnels.ms//ApiPerrosMovil/api/"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)

        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnAtras = findViewById<Button>(R.id.btnAtras)

        btnAtras.setOnClickListener(){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }



        btnGuardar.setOnClickListener(){

            val txtUsuario = findViewById<EditText>(R.id.etUsuario)
            val usuario = txtUsuario.text.toString().trim()

            val txtTelefono = findViewById<EditText>(R.id.etTel)
            val telefono = txtTelefono.text.toString().trim()

            val txtCorreo = findViewById<EditText>(R.id.etCorreo)
            val Correo = txtCorreo.text.toString().trim()

            val txtPassword1 = findViewById<EditText>(R.id.etPassword1)
            val Password1 = txtPassword1.text.toString().trim()

            val txtPassword2 = findViewById<EditText>(R.id.etPassword2)
            val Password2 = txtPassword2.text.toString().trim()

            val context: Context = this


            if (usuario.isNotEmpty() && Correo.isNotEmpty() && Password1.isNotEmpty() && Password2.isNotEmpty()
                 && telefono.isNotEmpty()) {

                if (Password1 != Password2){
                    // Mostrar un AlertDialog si algún campo está vacío
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Las Contraseñas No Coinciden")
                    builder.setMessage("Por favor, Verifica de nuevo")
                    builder.setIcon(R.drawable.cancelar)
                    builder.setPositiveButton("Aceptar") { dialog: DialogInterface, _ ->
                        dialog.dismiss() // Cierra el diálogo cuando se hace clic en el botón "Aceptar"
                    }

                    val dialog = builder.create()
                    dialog.show()

                }else{

                    val requestBody = Usuario(
                        username = usuario,
                        password = Password1,
                        email = Correo,
                        telefono = telefono
                    )

                    CoroutineScope(Dispatchers.IO).launch {
                        val call = retrofitApi().create(RetrofitService::class.java)
                            .registrase(requestBody)

                        runOnUiThread() {

                            Log.i("idUSer",call.id_usuario)
                            Log.i("User",call.username)
                            Log.i("Pass",call.password)

                            if(call.username.isNotEmpty()){

                                val builder = AlertDialog.Builder(context)
                                builder.setTitle("Datos Registrados")
                                builder.setMessage("Bienvenido")
                                builder.setIcon(R.drawable.verificacion)
                                builder.setPositiveButton("Aceptar") { dialog: DialogInterface, _ ->
                                    dialog.dismiss() // Cierra el diálogo cuando se hace clic en el botón "Aceptar"
                                    val intent = Intent(context, PrincipalActivity::class.java)
                                    startActivity(intent)
                                }
                                val dialog = builder.create()
                                dialog.show()


                            }else{
                                val builder = AlertDialog.Builder(context)
                                builder.setTitle("Error de registro")
                                builder.setMessage("Por favor, Ingrese los datos de nuevo")
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
            } else {
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

    private fun retrofitApi(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }
}