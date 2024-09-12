package com.example.appnota

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appnota.databinding.ItemNotaBinding


class NotaAdapter(private val notas: MutableList<Nota>, private val onNotaClick: (Int) -> Unit) :
    RecyclerView.Adapter<NotaAdapter.NotaViewHolder>() {

    inner class NotaViewHolder(val binding: ItemNotaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(nota: Nota) {
            binding.tituloNota.text = nota.titulo
            binding.descripcionNota.text = nota.descripcion
            binding.root.setCardBackgroundColor(nota.color)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val binding = ItemNotaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val nota = notas[position]
        holder.bind(nota)
        holder.itemView.setOnClickListener { onNotaClick(position) }
    }

    override fun getItemCount(): Int = notas.size
}