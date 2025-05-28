package com.example.proyectoadopcion

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MascotaAdapter(private var mascotas: List<Mascota>, context: Context,
                     private val esCoordinador: Boolean) :
    RecyclerView.Adapter<MascotaAdapter.MascotaViewHolder>() {
    private val db: DBhelper = DBhelper(context)

    class MascotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tipoMascotaTextView: TextView = itemView.findViewById(R.id.tipoMascotaTextView)
        val razaTextView: TextView = itemView.findViewById(R.id.razaTextView)
        val edadTextView: TextView = itemView.findViewById(R.id.edadTextView)
        val pesoTextView: TextView = itemView.findViewById(R.id.pesoTextView)
        val generoTextView: TextView = itemView.findViewById(R.id.generoTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
        val adoptarButton: ImageView = itemView.findViewById(R.id.adoptarButton)
        val image_mascota: ImageView = itemView.findViewById(R.id.image_mascota)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MascotaViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.mascota__item, parent, false)
        return MascotaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mascotas.size
    }


    override fun onBindViewHolder(holder: MascotaViewHolder, position: Int) {
        val mascota = mascotas[position]
        holder.tipoMascotaTextView.text = "Es un ${mascota.tipoMascota}"
        holder.razaTextView.text = "Raza: ${mascota.raza}"
        holder.edadTextView.text = "Tiene ${mascota.edad} años"
        var peso = String.format("%.2f", mascota.peso)
        holder.pesoTextView.text = "Pesa ${peso} kilos"
        holder.generoTextView.text = "Es ${mascota.genero}"
        Glide.with(holder.itemView.context)
            .load(mascota.urlImg)
            .error(R.drawable.cl_linkroto)
            .into(holder.image_mascota)

        if (esCoordinador) {
            holder.updateButton.visibility = View.VISIBLE
            holder.deleteButton.visibility = View.VISIBLE
        } else {
            holder.updateButton.visibility = View.GONE
            holder.deleteButton.visibility = View.GONE
        }


        holder.deleteButton.setOnClickListener {
            db.deleteMascota(mascota.id)
            refresData(db.getAllMascotas())
            Toast.makeText(holder.itemView.context,"Mascota Eliminada", Toast.LENGTH_SHORT).show()
        }
        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateActivity::class.java).apply {
                putExtra("mascota_id",mascota.id)
            }
            holder.itemView.context.startActivity(intent)
        }
        holder.adoptarButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, DescripcionMascotaActivity::class.java).apply {
                putExtra("mascota_id",mascota.id)
            }
            holder.itemView.context.startActivity(intent)
        }


    }


    fun refresData(newMascota: List<Mascota>) {
        mascotas = newMascota
        notifyDataSetChanged()
    }


}