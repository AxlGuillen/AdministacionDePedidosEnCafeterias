package com.example.cafe2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_nueva_promo.btnRegresar5
import kotlinx.android.synthetic.main.activity_nueva_promo.btnSave2
import kotlinx.android.synthetic.main.activity_nueva_promo.descripcionProducto2
import kotlinx.android.synthetic.main.activity_nueva_promo.nombreProducto2
import kotlinx.android.synthetic.main.activity_nueva_promo.precioProducto2
import kotlinx.android.synthetic.main.activity_nuevo_producto.descripcionProducto
import kotlinx.android.synthetic.main.activity_nuevo_producto.nombreProducto
import kotlinx.android.synthetic.main.activity_nuevo_producto.precioProducto
import kotlinx.android.synthetic.main.activity_registrar.btnRegistrarRegistrarCajero
import kotlinx.android.synthetic.main.activity_registrar.edadRegistrarRegistrarCajero
import kotlinx.android.synthetic.main.activity_registrar.emailRegistrarRegistrarCajero
import kotlinx.android.synthetic.main.activity_registrar.nombreRegistrarRegistrarCajero
import kotlinx.android.synthetic.main.activity_registrar.passRegistrarRegistrarCajero
import kotlinx.android.synthetic.main.activity_registrar.telefonoRegistrarRegistrarCajero

class NuevaPromo : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_promo)

        //boton de la flechita
        btnRegresar5.setOnClickListener {
            onBackPressed()
        }

        //se manda llamar el metodo
        btnSave2.setOnClickListener {
            //se valida que no esten vacios los campos
            //las validaciones deberian mostrar errores abajo
            if (nombreProducto2.text.isNotEmpty() && precioProducto2.text.isNotEmpty() && descripcionProducto2.text.isNotEmpty()){
                AgregarPromocion()
            }
            else{
                //mostrar esto como error preguntarle a rodri
                print("Todos los campos necesitan estar llenos.")
            }
        }
    }

    private fun AgregarPromocion(){
        title = "Agregar Promocion"

        //Declaramos la variable de nombre, precio, descripcion y falta imagen
        val nombre = nombreProducto2.text
        val precio = precioProducto2.text
        val descripcion = descripcionProducto2.text

        //Agregamos los datos a la coleccion productos en la base de datos nombres es la primary key, los otros campos son precio estado y descripcion
        db.collection("Promociones").document(nombre.toString()).set(
            hashMapOf("Nombre" to nombre.toString(),
                "Precio" to precio.toString(),
                "Estado" to true,
                "Descripcion" to descripcion.toString()
            )
        ).addOnCompleteListener{if (it.isSuccessful){
            val OperacionExitosaIntent = Intent(this, OperacionExitosa::class.java).apply {
                putExtra("Mensaje","Promocion añadida.")
            }
            startActivity(OperacionExitosaIntent)
        }else{
            showAlert()
        }}

    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error al agregar la promocion.")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }
}