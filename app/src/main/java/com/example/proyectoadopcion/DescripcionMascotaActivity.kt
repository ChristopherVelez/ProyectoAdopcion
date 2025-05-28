package com.example.proyectoadopcion

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.proyectoadopcion.databinding.ActivityDescripcionMascotaBinding
import com.example.proyectoadopcion.databinding.ActivityUpdateBinding

class DescripcionMascotaActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDescripcionMascotaBinding
    private lateinit var db: DBhelper
    private var mascotaid: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDescripcionMascotaBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        Glide.with(this)
            .load(mascota.urlImg)
            .error(R.drawable.cl_linkroto)
            .into(binding.Descripcionimage)
        binding.DescripcionTexview.text=mascota.descripcion
        val contacto = "Celular: 0979026743\nCorreo: coordinador@gmail.com"
        binding.ContactoTexview.text=contacto

    }
}