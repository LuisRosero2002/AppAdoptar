package com.example.appfirebaselogin

import android.app.DatePickerDialog
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){   Uri ->
        //obtener imagen desde el dispositivo
        if (Uri !=null){
            imagen.setImageURI(Uri)
        }else{

        }

    }
    lateinit var btnImage: Button
    lateinit var imagen: ImageView

    private lateinit var txtDate: EditText
    private val calendar: Calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dar_en_adopcion)

        val autoCompleteTextView: AutoCompleteTextView = findViewById(R.id.txtraza)
        val sexo = findViewById<AutoCompleteTextView>(R.id.txtsexo)

        // Definir opciones para el AutoCompleteTextView
        val opciones = arrayOf("Beagle", "Chihuahua", "Doberman", "Daneses", "Golden Retriever", "Husky siberiano", "Labrador", "Mestizo", "Pastor Alemán",
            "Pug", "Pit bull", "Pitbull Terrier", "Rottweiler", "Sabueso", "San Bernardo", "Yorkshire Terrier", "Abisinio", "AmeriCan Curl",
            "American Bobtail", "American Shorthair", "Angora Turco", "Azul Ruso", "Balinese", "Bengalí", "British Shorthair", "Burmilla",
            "Chartreux", "Cornish Rex", "Devon Rex", "Don Sphynx", "Egipcio Mau", "Exótico de Pelo Corto", "Fold Escocés",
            "Gatito Oriental", "Gato de la Selva", "Gato de Maine Coon", "Gato de Pelo Corto Americano", "Gato de Pelo Corto Británico",
            "Gato de Pelo Largo Británico", "Gato de Pelo Largo Americano", "Gato Persa", "Gato Ragdoll",
            "Gato Sagrado de Birmania", "Gato Siamés", "Gato Sokoke", "Gato Sphynx", "Gato Somali", "Gato Tonkinese", "Himalayés",
            "Manx", "Mau Egipcio", "Munchkin", "Nebelung", "Ocicat", "Oriental", "Persa",
            "Ragdoll", "Savannah", "Scottish Fold", "Selkirk Rex", "Sphynx", "Sokoke", "Siamés", "Siberiano", "Snowshoe",
            "Somali", "Tonkinese", "Turkish Angora", "Van Turco")

        // Crear un adaptador para las opciones
        val adaptador = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, opciones)

        // Configurar el AutoCompleteTextView con el adaptador
        autoCompleteTextView.setAdapter(adaptador)

        //////////////////////////////////////////opciones para sexo
        // Definir opciones para el AutoCompleteTextView
        val opcion = arrayOf("Hembra", "Macho")

        // Crear un adaptador para las opciones
        val adap = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, opcion)

        // Configurar el AutoCompleteTextView con el adaptador
        sexo.setAdapter(adap)

        /////////////////////////////////////OPCIONES TAMAÑO
        val tamano = findViewById<AutoCompleteTextView>(R.id.txtTamano)
        // Definir opciones para el AutoCompleteTextView
        val opc = arrayOf("Grande", "Mediano", "Pequeño")

        // Crear un adaptador para las opciones
        val Mostrar = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, opc)

        // Configurar el AutoCompleteTextView con el adaptador
        tamano.setAdapter(Mostrar)

        txtDate = findViewById(R.id.txtDate)

        // Mostrar el DatePickerDialog cuando se hace clic en el EditText
        txtDate.setOnClickListener {
            showDatePickerDialog()
        }

        ///////obtener id del bton para la imagen y el id de el imageview
        btnImage = findViewById(R.id.btnImagen)
         imagen = findViewById(R.id.imagAnimal)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://gdg0gqfj-80.use2.devtunnels.ms/ApiPerrosMovil/api/") // URL base de tu API
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val miApi = retrofit.create(RetrofitService::class.java)

        btnImage.setOnClickListener {
            ///llamamos a pickmedia
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

            val llamada: Call<Perro> = miApi.obtenerPerros()

// Ejecutar la llamada de manera asíncrona
            llamada.enqueue(object : Callback<Perro> {
                override fun onResponse(call: Call<Perro>, response: Response<Perro>) {
                    if (response.isSuccessful) {
                        val datos: Perro? = response.body()
                        // Hacer algo con los datos
                        println(datos)
                        Log.d("hola", datos.toString())
                    } else {
                        // Manejar el error
                    }
                }

                override fun onFailure(call: Call<Perro>, t: Throwable) {
                    // Manejar el fallo de la llamada
                    }
            })


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

}