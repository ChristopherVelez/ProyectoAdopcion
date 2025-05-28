package com.example.proyectoadopcion

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectoadopcion.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db: DBhelper
    private var mascotaid: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Poblar Spinner de especie
        val especies = listOf("Perro", "Gato")
        val adapterEspecie = ArrayAdapter(this, android.R.layout.simple_spinner_item, especies)
        adapterEspecie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.editarspinnerTipoMascota.adapter = adapterEspecie

        // Poblar Spinner de género
        val generos = listOf("Macho", "Hembra")
        val adapterGenero = ArrayAdapter(this, android.R.layout.simple_spinner_item, generos)
        adapterGenero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.editarspinnerGenero.adapter = adapterGenero


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        db = DBhelper(this)
        mascotaid = intent.getIntExtra("mascota_id", -1)
        if (mascotaid == -1) {
            finish()
            return
        }


        val mascota = db.getMascotaById(mascotaid)
        binding.editaretRaza.setText(mascota.raza)
        binding.editaretEdad.setText(mascota.edad)
        binding.editaretDescripcion.setText(mascota.descripcion)
        binding.editaretPeso.setText(mascota.peso.toString())
        binding.editaretUrlImg.setText(mascota.urlImg)

        // Seleccionar valores actuales en los spinners
        binding.editarspinnerTipoMascota.setSelection(especies.indexOf(mascota.tipoMascota))
        binding.editarspinnerGenero.setSelection(generos.indexOf(mascota.genero))

        binding.btneditar.setOnClickListener {
            val tipomascota = binding.editarspinnerTipoMascota.selectedItem.toString()
            val raza = binding.editaretRaza.text.toString()
            val edad = binding.editaretEdad.text.toString()
            val descripcion = binding.editaretDescripcion.text.toString()
            val peso = binding.editaretPeso.text.toString().toFloatOrNull() ?: 0f
            val genero = binding.editarspinnerGenero.selectedItem.toString()
            val urlImg = binding.editaretUrlImg.text.toString()

            val updateMascota =
                Mascota(mascotaid, tipomascota, raza, edad, descripcion, peso, genero, urlImg)
            db.updateMascota(updateMascota)
            Toast.makeText(this, "Mascota actualizada", Toast.LENGTH_SHORT).show()
            finish()
        }


    }
}


