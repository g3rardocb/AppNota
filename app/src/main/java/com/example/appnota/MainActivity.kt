package com.example.appnota

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appnota.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val listaNotas = mutableListOf<Nota>()
    private lateinit var adapter: NotaAdapter
    private var notaSeleccionada: Int? = null
//    private val colores = listOf(Color.YELLOW, Color.CYAN, Color.RED, Color.GREEN, Color.BLUE)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar DataBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurando RecyclerView
        setupRecyclerView()

        // agregar o editar una nota
        binding.btnAgregar.setOnClickListener {
            guardarNota()
        }

        // eliminar una nota
        binding.btnEliminar.setOnClickListener {
            eliminarNota()
        }

        // Botón para cambiar el color de una nota
        binding.btnCambiarColor.setOnClickListener {
            cambiarColorNota()
        }
    }


    private fun setupRecyclerView() {
        adapter = NotaAdapter(listaNotas) { posicion ->
            notaSeleccionada = posicion
            val nota = listaNotas[posicion]
            binding.inputTitulo.setText(nota.titulo)
            binding.inputDescripcion.setText(nota.descripcion)
        }
        binding.recyclerViewNotas.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewNotas.adapter = adapter
    }

    private fun guardarNota() {
        val titulo = binding.inputTitulo.text.toString()
        val descripcion = binding.inputDescripcion.text.toString()

        if (notaSeleccionada == null) {
            // Agregar nueva nota
            listaNotas.add(Nota(titulo, descripcion, getColor(R.color.white)))
            adapter.notifyItemInserted(listaNotas.size - 1)
            binding.recyclerViewNotas.scrollToPosition(listaNotas.size - 1) // Desplaza al último elemento

        } else {
            // Editar nota existente
            val nota = listaNotas[notaSeleccionada!!]
            nota.titulo = titulo
            nota.descripcion = descripcion
            adapter.notifyItemChanged(notaSeleccionada!!)
            notaSeleccionada = null
        }

        // Limpiar campos de texto después de agregar/editar
        limpiarCampos()
    }

    private fun eliminarNota() {
        notaSeleccionada?.let {
            listaNotas.removeAt(it)
            adapter.notifyItemRemoved(it)
            notaSeleccionada = null
        }
        limpiarCampos()
    }

    private fun cambiarColorNota() {
        if (notaSeleccionada != null) {
            mostrarDialogoColores()
        }
    }

    private fun limpiarCampos() {
        binding.inputTitulo.text.clear()
        binding.inputDescripcion.text.clear()
    }


    // Método para mostrar el diálogo de colores predefinidos
    private fun mostrarDialogoColores() {
        val colores = arrayOf(
            "Amarillo", "Ámbar", "Naranja", "Naranja Oscuro", "Rojo", "Rosa",
            "Púrpura", "Índigo", "Azul", "Verde"
        )

        val colorCodes = intArrayOf(
            getColor(R.color.color1), getColor(R.color.color2), getColor(R.color.color3),
            getColor(R.color.color4), getColor(R.color.color5), getColor(R.color.color6),
            getColor(R.color.color7), getColor(R.color.color8), getColor(R.color.color9),
            getColor(R.color.color10)
        )

        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle("Elige un color")
        builder.setItems(colores) { _, which ->
            notaSeleccionada?.let {
                val nota = listaNotas[it]
                nota.color = colorCodes[which]
                adapter.notifyItemChanged(it)
              //  adapter.notifyDataSetChanged()
            }
        }
        builder.show()
    }

}


