package com.example.appfirebaselogin

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.MultiAutoCompleteTextView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.appfirebaselogin.Data.modelos.Perro
import com.example.appfirebaselogin.Data.modelos.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URI
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DarEnAdopcionActivity : AppCompatActivity() {

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { Uri ->
        //obtener imagen desde el dispositivo
        if (Uri != null) {
            imagen.setImageURI(Uri)
        } else {

        }

    }
    lateinit var btnImage: Button
    lateinit var imagen: ImageView
    var razaSeleccionada:String = ""
    var tamanoSeleccionado:String = ""
    var sexoSeleccionado:String = ""

    private lateinit var txtDate: EditText
    private val calendar: Calendar = Calendar.getInstance()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dar_en_adopcion)

        val name = (findViewById<EditText>(R.id.txtname)).text.toString()
        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.txtraza)
        val sexo = findViewById<AutoCompleteTextView>(R.id.txtsexo)
        val edad = (findViewById<EditText>(R.id.txtedad)).text.toString()
        val tamano = findViewById<AutoCompleteTextView>(R.id.txtTamano)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        txtDate = (findViewById(R.id.txtDate))
        ///////obtener id del bton para la imagen y el id de el imageview
        btnImage = findViewById(R.id.btnImagen)
        imagen = findViewById(R.id.imagAnimal)
        //boton enviar
        val btnEnviar = findViewById<Button>(R.id.btnguardar)

        ////obtener el valor del radio buton selecionado

        // Obtener el ID del RadioButton seleccionado en el RadioGroup
        val radioButtonId = radioGroup.checkedRadioButtonId

        // Verificar si se seleccionó un RadioButton
        if (radioButtonId != -1) {
            // Obtener el RadioButton seleccionado
            val radioButton = findViewById<RadioButton>(radioButtonId)

            // Obtener el texto del RadioButton seleccionado
            val esterilizado = radioButton.text.toString()

            // Ahora puedes hacer algo con el valor de "esterilizado"
            println("Esterilizado: $esterilizado")
        } else {
            // Manejar el caso en que no se seleccionó ningún RadioButton
            println("Ningún RadioButton seleccionado")
        }



        // obtener opciones para el AutoCompleteTextView con los datos del array
        val opcionesRaza = obtenerOpcionesRaza()
        // Crear un adaptador para las opciones
        val adaptador = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, opcionesRaza)

// Configurar el AutoCompleteTextView con el adaptador para opciones de mascota
        autoCompleteTextView.setAdapter(adaptador)

// Manejar la selección de elementos en el AutoCompleteTextView
        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            // Obtener el valor seleccionado del adaptador
            razaSeleccionada = adaptador.getItem(position).toString()

            // Ahora puedes hacer algo con la raza seleccionada
            // Por ejemplo, imprimirlo
            println("Raza seleccionada: $razaSeleccionada")
        }

        //////////////////////////////////////////opciones para sexo
        // Definir opciones para el AutoCompleteTextView
        val opcion = arrayOf("Hembra", "Macho")

        // Crear un adaptador para las opciones
        val adap = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, opcion)

        // Configurar el AutoCompleteTextView con el adaptador
        sexo.setAdapter(adap)
        // Manejar la selección de elementos en el AutoCompleteTextView
        sexo.setOnItemClickListener { _, _, position, _ ->
            // Obtener el valor seleccionado del adaptador
            sexoSeleccionado = adap.getItem(position).toString()

            // Ahora puedes hacer algo con la raza seleccionada
            // Por ejemplo, imprimirlo
            println("Raza seleccionada: $sexoSeleccionado")
        }

        /////////////////////////////////////OPCIONES TAMAÑO
        // Definir opciones para el AutoCompleteTextView
        val opc = arrayOf("Grande", "Mediano", "Pequeño")

        // Crear un adaptador para las opciones
        val Mostrar = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, opc)

        // Configurar el AutoCompleteTextView con el adaptador
        tamano.setAdapter(Mostrar)

        // Manejar la selección de elementos en el AutoCompleteTextView
        tamano.setOnItemClickListener { _, _, position, _ ->
            // Obtener el valor seleccionado del adaptador
            tamanoSeleccionado = Mostrar.getItem(position).toString()

            // Ahora puedes hacer algo con la raza seleccionada
            // Por ejemplo, imprimirlo
            println("Raza seleccionada: $tamanoSeleccionado")
        }


        // Mostrar el DatePickerDialog cuando se hace clic en el EditText
        txtDate.setOnClickListener {
            showDatePickerDialog()
        }


        val retrofit = Retrofit.Builder()
            .baseUrl("https://gdg0gqfj-80.use2.devtunnels.ms/ApiPerrosMovil/api/") // URL base de tu API
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val miApi = retrofit.create(RetrofitService::class.java)

        btnImage.setOnClickListener {
            ///llamamos a pickmedia
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
/*
            val llamada: Call<List<Perro>> = miApi.obtenerPerros()

            // Ejecutar la llamada de manera asíncrona
            llamada.enqueue(object : Callback<List<Perro>> {
                override fun onResponse(call: Call<List<Perro>>, response: Response<List<Perro>>) {
                    if (response.isSuccessful) {
                        val listaPerros: List<Perro>? = response.body()
                        // Hacer algo con los datos de la lista
                        println(listaPerros)
                        Log.d("hola", listaPerros.toString())

                        // Verificar que datos no sea nulo
                        if (listaPerros != null && listaPerros.isNotEmpty()) {
                            // Mostrar los datos de cada perro en un bucle for
                            for (perro in listaPerros) {
                                println("ID: ${perro.id}")
                                println("Raza: ${perro.raza}")
                                println("Nombre: ${perro.nombre}")
                                println("Peso: ${perro.peso}")
                                println("Tamaño: ${perro.tamanio}")
                                println("Edad: ${perro.edad}")
                                println("ID Genero: ${perro.idgenero}")
                                println("Descripción: ${perro.descripcion}")
                                println("Fecha: ${perro.fecha}")
                                println("----------------------------------")
                            }
                        } else {
                            println("Datos nulos")
                        }

                    } else {

                        // Obtener el cuerpo de la respuesta de error
                        val errorBody = response.errorBody()?.string()

                        // Obtener el código de estado HTTP
                        val statusCode = response.code()
                        println("Error en la llamada. Código de estado: $statusCode")

                        // Si hay información en el cuerpo del error, también la mostramos
                        if (errorBody != null && errorBody.isNotEmpty()) {
                            println("Cuerpo del error: $errorBody")
                        }
                    }
                }

                override fun onFailure(call: Call<List<Perro>>, t: Throwable) {
                    // Manejar el fallo de la llamada
                    println("Error en la llamada: ${t.message}")

                }
            })


 */

        }

        btnEnviar.setOnClickListener{
            if(name.isNotEmpty() or razaSeleccionada.isNotEmpty() or tamanoSeleccionado.isNotEmpty() or sexoSeleccionado.isNotEmpty() ){

            }else{
                // Mostrar un AlertDialog si algún error ocurrio
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Error Campos Vacios")
                builder.setMessage("Por favor, Verifica de nuevo")
                builder.setIcon(R.drawable.cancelar)
                builder.setPositiveButton("Aceptar") { dialog: DialogInterface, _ ->
                    dialog.dismiss() // Cierra el diálogo cuando se hace clic en el botón "Aceptar"
                }

                val dialog = builder.create()
                dialog.show()
            }

        }
        val btnregresar = findViewById<Button>(R.id.btnAtras)
        btnregresar.setOnClickListener{
            val intent = Intent(this, PrincipalActivity::class.java)
            startActivity(intent)
        }

    }

    ///////calendario
    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                // Actualizar el campo de texto con la fecha seleccionada
                calendar.set(year, month, dayOfMonth)
                updateDateInEditText()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Configurar límites de fecha (opcional)
        // datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000

        datePickerDialog.show()
    }

    private fun updateDateInEditText() {
        val dateFormat = "dd/MM/yyyy"
        val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
        txtDate.setText(simpleDateFormat.format(calendar.time))
    }

    private fun obtenerOpcionesRaza() :Array<String> {
        return arrayOf(
            "Beagle",
            "Chihuahua",
            "Doberman",
            "Daneses",
            "Golden Retriever",
            "Husky siberiano",
            "Labrador",
            "Mestizo",
            "Pastor Alemán",
            "Pug",
            "Pit bull",
            "Pitbull Terrier",
            "Rottweiler",
            "Sabueso",
            "San Bernardo",
            "Yorkshire Terrier",
            "Abisinio",
            "AmeriCan Curl",
            "American Bobtail",
            "American Shorthair",
            "Angora Turco",
            "Azul Ruso",
            "Balinese",
            "Bengalí",
            "British Shorthair",
            "Burmilla",
            "Chartreux",
            "Cornish Rex",
            "Devon Rex",
            "Don Sphynx",
            "Egipcio Mau",
            "Exótico de Pelo Corto",
            "Fold Escocés",
            "Gatito Oriental",
            "Gato de la Selva",
            "Gato de Maine Coon",
            "Gato de Pelo Corto Americano",
            "Gato de Pelo Corto Británico",
            "Gato de Pelo Largo Británico",
            "Gato de Pelo Largo Americano",
            "Gato Persa",
            "Gato Ragdoll",
            "Gato Sagrado de Birmania",
            "Gato Siamés",
            "Gato Sokoke",
            "Gato Sphynx",
            "Gato Somali",
            "Gato Tonkinese",
            "Himalayés",
            "Manx",
            "Mau Egipcio",
            "Munchkin",
            "Nebelung",
            "Ocicat",
            "Oriental",
            "Persa",
            "Ragdoll",
            "Savannah",
            "Scottish Fold",
            "Selkirk Rex",
            "Sphynx",
            "Sokoke",
            "Siamés",
            "Siberiano",
            "Snowshoe",
            "Somali",
            "Tonkinese",
            "Turkish Angora",
            "Van Turco"
        )

    }

}