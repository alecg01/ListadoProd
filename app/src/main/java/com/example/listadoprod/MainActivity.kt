package com.example.listadoprod

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listadoprod.dataadapter.ProductoAdapter
import com.example.listadoprod.databinding.ActivityMainBinding
import com.example.listadoprod.dataclass.Producto
import java.lang.Exception

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    var listaProd = ArrayList<Producto>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        iniciar()


    }

    private fun limpiar() {
        with(binding) {
            etID.setText("")
            etNombreProd.setText("")
            etPrecio.setText("")
            etID.requestFocus()
        }
    }

    private fun agregarProd() {
        with(binding) {
            try {
                val id: Int = etID.text.toString().toInt()
                val nombre: String = etNombreProd.text.toString()
                val precio: Double = etPrecio.text.toString().toDouble()
                val prod = Producto(id, nombre, precio)
                listaProd.add(prod)
            } catch (ex: Exception) {

                Toast.makeText(
                    this@MainActivity, "Por favor completar los campos.",
                    Toast.LENGTH_LONG
                ).show()
            }

            rcvLista.layoutManager = LinearLayoutManager(this@MainActivity)
            rcvLista.adapter = ProductoAdapter(listaProd,
                                                {producto -> selectItem(producto)},
                                                 {position -> eliminarProd(position) },
                                                {position -> editarProd(position)})
            limpiar()
        }

    }

    private fun eliminarProd(position: Int) {
        with(binding){
            listaProd.removeAt(position)
            rcvLista.adapter?.notifyItemRemoved(position)
        }
        limpiar()
    }

    private fun selectItem(producto: Producto){
        with(binding){
            etID.text = producto.id.toString().toEditable()
            etNombreProd.text = producto.nombre.toEditable()
            etPrecio.text = producto.precio.toString().toEditable()
        }
    }

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    fun editarProd(position: Int){
        with(binding){
            val id: Int = etID.text.toString().toInt()
            val nombre: String = etNombreProd.text.toString()
            val precio: Double = etPrecio.text.toString().toDouble()
            val prod = Producto(id, nombre, precio)
            listaProd.set(position, prod)
            rcvLista.adapter?.notifyItemChanged(position)
        }

        limpiar()
    }


    private fun iniciar() {
        binding.btnAgregar.setOnClickListener {
            agregarProd()
        }
        binding.btnLimpiar.setOnClickListener {
            limpiar()
        }




    }

}