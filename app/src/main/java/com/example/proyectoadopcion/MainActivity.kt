package com.example.proyectoadopcion

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectoadopcion.databinding.ActivityInicioBinding
import com.example.proyectoadopcion.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.BtnLogin.setOnClickListener {
            val usuario = binding.etUsuario.text.toString()
            val clave = binding.etContraseA.text.toString()

            if (usuario == "coordinador" && clave == "1234") {
                // Inicia con rol de coordinador
                val intent = Intent(this, InicioActivity::class.java)
                intent.putExtra("esCoordinador", true)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

         binding.btnAdoptar.setOnClickListener {
             val intent = Intent(this, InicioActivity::class.java)
             intent.putExtra("esCoordinador", false)
             startActivity(intent)
         }




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}