package com.example.appfirebaselogin

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.appfirebaselogin.Data.modelos.Perro
import com.example.appfirebaselogin.Data.modelos.RetrofitService
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class DarEnAdopcionActivity : AppCompatActivity() {

    lateinit var btnImage: Button
    lateinit var imagen: ImageView
    private var razaSeleccionada: String = ""
    private var tamanoSeleccionado: String = ""
    private var sexoSeleccionado: String = ""
    private var esterilizado: String = ""
    private var date: String = ""
    private var peso: Double = 0.0
    private lateinit var file: File

    private lateinit var txtDate: EditText
    private val calendar: Calendar = Calendar.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dar_en_adopcion)


        //boton enviar
        val btnEnviar = findViewById<Button>(R.id.btnguardar)




//
        btnEnviar.setOnClickListener {
            val txtname = findViewById<EditText>(R.id.txtname)
            val name = txtname.text.toString()
            val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.txtraza)
            val sexo = findViewById<AutoCompleteTextView>(R.id.txtsexo)
            val txtedad = findViewById<EditText>(R.id.txtedad)
            val edad = txtedad.text.toString()
            val txtpeso = findViewById<EditText>(R.id.txtpeso)
            val pesoString = txtpeso.text.toString()
            val tamano = findViewById<AutoCompleteTextView>(R.id.txtTamano)
            val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
            txtDate = (findViewById(R.id.txtDate))
            val txtdescripcion = findViewById<EditText>(R.id.txtdescripcion)
            val descripcion = txtdescripcion.text.toString()
            ///////obtener id del bton para la imagen y el id de el imageview
            btnImage = findViewById(R.id.btnImagen)
            imagen = findViewById(R.id.imagAnimal)

            btnImage.setOnClickListener {
                ///llamamos a pickmedia
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }

            ////obtener el valor del radio buton selecionado
            val radioButtonId = radioGroup.checkedRadioButtonId

            if (radioButtonId != -1) {
                val radioButton = findViewById<RadioButton>(radioButtonId)
                esterilizado = radioButton.text.toString()
            } else {
                println("Ningún RadioButton seleccionado")
            }


            // obtener opciones para el AutoCompleteTextView con los datos del array
            val opcionesRaza = obtenerOpcionesRaza()
            // Crear un adaptador para las opciones
            val adaptador =
                ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, opcionesRaza)

// Configurar el AutoCompleteTextView con el adaptador para opciones de mascota
            autoCompleteTextView.setAdapter(adaptador)

// Manejar la selección de elementos en el AutoCompleteTextView
            autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
                // Obtener el valor seleccionado del adaptador
                razaSeleccionada = adaptador.getItem(position).toString()
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

            }


            // Mostrar el DatePickerDialog cuando se hace clic en el EditText
            txtDate.setOnClickListener {
                showDatePickerDialog()
            }

           /* if (name.isNotEmpty() && razaSeleccionada.isNotEmpty() && tamanoSeleccionado.isNotEmpty() && sexoSeleccionado.isNotEmpty()
                && edad.isNotEmpty() && descripcion.isNotEmpty() && esterilizado.isNotEmpty() && pesoString.isNotEmpty() && date.isNotEmpty()) {*/
            if (name.isNotEmpty()){
                peso = pesoString.toDouble()

                val dateFormat = "dd/MM/yyyy"
                val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
                val fecha: Date? = simpleDateFormat.parse(date)


                // Convertir objeto Perro a JSON y convertirlo a RequestBody
                val gson = Gson()
                val perroJson = gson.toJson(
                    Perro(
                        id = 0,
                        raza = razaSeleccionada,
                        nombre = name,
                        peso = peso,
                        tamanio = 1.0,
                        edad = edad,
                        idgenero = 1,
                        descripcion = descripcion,
                        estaesterilizado = true,
                        fecharegistro = fecha,
                        image = ""
                    )
                )
                /// Crear parte (part) del objeto Perro para la solicitud multipart
                val perroRequestBody =
                    RequestBody.create(MediaType.parse("application/json"), perroJson)
                val perroPart = MultipartBody.Part.createFormData("perro", null, perroRequestBody)

                // Crear parte (part) de la imagen para la solicitud multipart
                val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                val body = MultipartBody.Part.createFormData("file", file.name, requestFile)



                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        // Hacer la llamada a la API usando Retrofit

                        val response =
                            RetrofitClient.createService().registrarPerro(perroPart, body)

                        withContext(Dispatchers.Main) {
                            // Verificar el código de respuesta
                            if (response.isSuccessful) {
                                // Acceder al cuerpo de la respuesta
                                val perroResponse: Perro? = response.body()

                                // Verificar si el cuerpo de la respuesta no es nulo
                                if (perroResponse != null) {
                                    // Operaciones con el cuerpo de la respuesta
                                    Log.i("idUSer", perroResponse.id.toString())
                                    Log.i("Pass", perroResponse.nombre)
                                    Log.i("User", perroResponse.peso.toString())

                                    // Mostrar un AlertDialog si algún error ocurrio
                                    val builder = AlertDialog.Builder(this@DarEnAdopcionActivity)
                                    builder.setTitle("EXITO")
                                    builder.setMessage("Datos De La Mascota Registrados")
                                    builder.setIcon(R.drawable.verificacion)
                                    builder.setPositiveButton("Aceptar") { dialog: DialogInterface, _ ->
                                        dialog.dismiss() // Cierra el diálogo cuando se hace clic en el botón "Aceptar"
                                    }

                                    val dialog = builder.create()
                                    dialog.show()
                                } else {
                                    // Mostrar un AlertDialog si algún error ocurrio
                                    val builder = AlertDialog.Builder(this@DarEnAdopcionActivity)
                                    builder.setTitle("A Ocurrido Un Error")
                                    builder.setMessage("Por favor intenta de nuevo")
                                    builder.setIcon(R.drawable.cancelar)
                                    builder.setPositiveButton("Aceptar") { dialog: DialogInterface, _ ->
                                        dialog.dismiss() // Cierra el diálogo cuando se hace clic en el botón "Aceptar"
                                    }

                                    val dialog = builder.create()
                                    dialog.show()
                                }
                            } else {
                                // Mostrar un AlertDialog si algún error ocurrio
                                val builder = AlertDialog.Builder(this@DarEnAdopcionActivity)
                                builder.setTitle("Error")
                                builder.setMessage("Por favor intenta de nuevo")
                                builder.setIcon(R.drawable.cancelar)
                                builder.setPositiveButton("Aceptar") { dialog: DialogInterface, _ ->
                                    dialog.dismiss() // Cierra el diálogo cuando se hace clic en el botón "Aceptar"
                                }

                                val dialog = builder.create()
                                dialog.show()
                            }
                        }
                    } catch (e: Exception) {
                        // Manejar errores de red u otros problemas
                        e.printStackTrace()
                        withContext(Dispatchers.Main) {
                            // Mostrar un AlertDialog si algún error ocurrio
                            val builder = AlertDialog.Builder(this@DarEnAdopcionActivity)
                            builder.setTitle("Error")
                            builder.setMessage("Por favor intenta de nuevo")
                            builder.setIcon(R.drawable.cancelar)
                            builder.setPositiveButton("Aceptar") { dialog: DialogInterface, _ ->
                                dialog.dismiss() // Cierra el diálogo cuando se hace clic en el botón "Aceptar"
                            }

                            val dialog = builder.create()
                            dialog.show()
                        }
                    }
                }


            } else {
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
        btnregresar.setOnClickListener {
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
        datePickerDialog.show()
    }

    private fun updateDateInEditText() {
        val dateFormat = "dd/MM/yyyy"
        val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
        date = simpleDateFormat.format(calendar.time)
        txtDate.setText(date)
    }


    object RetrofitClient {
        private const val BASE_URL =
            "https://gdg0gqfj-80.use2.devtunnels.ms//ApiPerrosMovil/api/" // Reemplaza con la URL de tu API

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

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        //obtener imagen desde el dispositivo
        if (uri != null) {
            imagen.setImageURI(uri)
            file = File(uri.path)
        } else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Error! al cargar la imagen")
            builder.setMessage("Por favor, Intenta de nuevo")
            builder.setIcon(R.drawable.cancelar)
            builder.setPositiveButton("Aceptar") { dialog: DialogInterface, _ ->
                dialog.dismiss() // Cierra el diálogo cuando se hace clic en el botón "Aceptar"
            }

            val dialog = builder.create()
            dialog.show()
        }

    }

    private fun obtenerOpcionesRaza(): Array<String> {
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