package com.example.cafe2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_nuevo_producto.btnCancelar
import kotlinx.android.synthetic.main.activity_nuevo_producto.btnRegresar
import kotlinx.android.synthetic.main.activity_nuevo_producto.btnSave
import kotlinx.android.synthetic.main.activity_nuevo_producto.descripcionProducto
import kotlinx.android.synthetic.main.activity_nuevo_producto.nombreProducto
import kotlinx.android.synthetic.main.activity_nuevo_producto.precioProducto

class NuevoProducto : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_producto)


        //boton para guardar el nuevo producto en la base de datos
        btnSave.setOnClickListener {
            AgregarProducto()

        }

        //boton de cancelar regresa al main de adm
        btnCancelar.setOnClickListener {
            val inicioAdmIntent = Intent(this, inicio_Admin::class.java)
            startActivity(inicioAdmIntent)
        }

        //boton de la flechita
        btnRegresar.setOnClickListener {
            onBackPressed()
        }

    }

    private fun AgregarProducto(){
        title = "Agregar Producto"

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
            ).addOnCompleteListener{if (it.isSuccessful){
                val OperacionExitosaIntent = Intent(this, OperacionExitosa::class.java).apply {
                    putExtra("Mensaje","Añadido exitosamente.")
                }
                startActivity(OperacionExitosaIntent)
            }else{
                showAlert()
            }}

        }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error al agregar el producto.")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

}