package com.example.cafe2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_nuevo_producto.btnSave
import kotlinx.android.synthetic.main.activity_nuevo_producto.descripcionProducto
import kotlinx.android.synthetic.main.activity_nuevo_producto.nombreProducto
import kotlinx.android.synthetic.main.activity_nuevo_producto.precioProducto

class EditarProducto : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_producto)

        btnSave.setOnClickListener {
            AgregarProducto()
            val homeIntent = Intent(this, OperacionExitosa::class.java).apply {
            }
            startActivity(homeIntent)
        }

    }

    private fun AgregarProducto(){
        title = "Editar producto"

        //Declaramos la variable de nombre, precio, descripcion y falta imagen
        val nombre = nombreProducto.text
        val precio = precioProducto.text
        val descripcion = descripcionProducto.text

        //Agregamos los datos a la coleccion productos en la base de datos nombres es la primary key, los otros campos son precio estado y descripcion
        db.collection("Productos").document(nombre.toString()).set(
            hashMapOf("Precio" to precio.toString(),
                "Estado" to true,
                "Descripcion" to descripcion.toString()
            )
            )

        }

}