package com.example.appfirebaselogin.fragments

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.appfirebaselogin.Data.modelos.RetrofitService
import com.example.appfirebaselogin.PrincipalActivity
import com.example.appfirebaselogin.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DarAdopcionFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_dar_adopcion, container, false)

        val btnEnviar = root.findViewById<Button>(R.id.btnguardar)
        val name = (root.findViewById<EditText>(R.id.txtname)).text.toString()
        val autoCompleteTextView = root.findViewById<AutoCompleteTextView>(R.id.txtraza)
        val sexo = root.findViewById<AutoCompleteTextView>(R.id.txtsexo)
        val edad = (root.findViewById<EditText>(R.id.txtedad)).text.toString()
        val tamano = root.findViewById<AutoCompleteTextView>(R.id.txtTamano)
        val radioGroup = root.findViewById<RadioGroup>(R.id.radioGroup)
        val radioButtonId = radioGroup.checkedRadioButtonId

        txtDate = (root.findViewById(R.id.txtDate))
        btnImage = root.findViewById(R.id.btnImagen)
        imagen = root.findViewById(R.id.imagAnimal)

        if (radioButtonId != -1) {

            val radioButton = root.findViewById<RadioButton>(radioButtonId)
            val esterilizado = radioButton.text.toString()

        } else {


        }

        val opcionesRaza = obtenerOpcionesRaza()
        val adaptador = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, opcionesRaza)
        autoCompleteTextView.setAdapter(adaptador)

        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            razaSeleccionada = adaptador.getItem(position).toString()
        }

        val opcion = arrayOf("Hembra", "Macho")
        val adap = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, opcion)
        sexo.setAdapter(adap)

        sexo.setOnItemClickListener { _, _, position, _ ->
            sexoSeleccionado = adap.getItem(position).toString()
        }

        val opc = arrayOf("Grande", "Mediano", "Pequeño")
        val Mostrar = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, opc)
        tamano.setAdapter(Mostrar)

        tamano.setOnItemClickListener { _, _, position, _ ->
            tamanoSeleccionado = Mostrar.getItem(position).toString()
        }


        txtDate.setOnClickListener {
            showDatePickerDialog()
        }

        btnImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        }

        btnEnviar.setOnClickListener{

            if(name.isNotEmpty() or razaSeleccionada.isNotEmpty() or tamanoSeleccionado.isNotEmpty() or sexoSeleccionado.isNotEmpty() ){

            }else{
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Error Campos Vacios")
                builder.setMessage("Por favor, Verifica de nuevo")
                builder.setIcon(R.drawable.cancelar)
                builder.setPositiveButton("Aceptar") { dialog: DialogInterface, _ ->
                    dialog.dismiss()
                }

                val dialog = builder.create()
                dialog.show()
            }

        }

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DarAdopcionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
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