package com.example.proyectoadopcion

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectoadopcion.databinding.ActivityAddMascotaBinding

class AddMascotaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMascotaBinding
    private lateinit var  db:DBhelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMascotaBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Poblar Spinner de especie
        val especies = listOf("Perro", "Gato", "Ave", "Otro")
        val adapterEspecie = ArrayAdapter(this, android.R.layout.simple_spinner_item, especies)
        adapterEspecie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTipoMascota.adapter = adapterEspecie

        // Poblar Spinner de género
        val generos = listOf("Masculino", "Femenino")
        val adapterGenero = ArrayAdapter(this, android.R.layout.simple_spinner_item, generos)
        adapterGenero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGenero.adapter = adapterGenero


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        db = DBhelper(this)
        binding.btnGuardar.setOnClickListener {
            val tipomascota = binding.spinnerTipoMascota.selectedItem.toString()
            val raza = binding.etRaza.text.toString()
            val edad = binding.etEdad.text.toString()
            val descripcion = binding.etDescripcion.text.toString()
            val pesoTexto = binding.etPeso.text.toString()
            val peso = pesoTexto.toFloatOrNull() ?: 0f
            val genero = binding.spinnerGenero.selectedItem.toString()
            val urlImg = binding.etUrlImg.text.toString()
            val mascota = Mascota(0,tipomascota,raza,edad,descripcion,peso,genero,urlImg)
            db.insertarMascota(mascota)
            finish()
            Toast.makeText(this,"Mascota Guardada", Toast.LENGTH_SHORT).show()

        }
    }
}