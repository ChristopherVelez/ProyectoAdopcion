package com.example.proyectoadopcion

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectoadopcion.databinding.ActivityInicioBinding


class InicioActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInicioBinding
    private lateinit var db: DBhelper
    private lateinit var mascotaAdapter: MascotaAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        db = DBhelper(this)
        mascotaAdapter = MascotaAdapter(db.getAllMascotas(),this)

        binding.noteRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.noteRecyclerView.adapter =mascotaAdapter

        binding.addButton.setOnClickListener {
            val intent=Intent(this,AddMascotaActivity::class.java)
            startActivity(intent)
        }
        binding.UserButton.setOnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }


    override fun onResume() {
        super.onResume()
        mascotaAdapter.refresData(db.getAllMascotas())
    }
}