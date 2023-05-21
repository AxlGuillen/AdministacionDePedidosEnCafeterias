package com.example.cafe2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_pregunta_confirmacion.btnNo
import kotlinx.android.synthetic.main.activity_pregunta_confirmacion.btnSi
import kotlinx.android.synthetic.main.activity_pregunta_confirmacion.textNombre
import kotlinx.android.synthetic.main.activity_pregunta_confirmacion.textView3

class PreguntaConfirmacion : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pregunta_confirmacion)

        //Extraemos los datos
        intent.extras
        val bundle = intent.extras
        val email:String? = bundle?.getString("email")
        val nombre:String? = bundle?.getString("Nombre")
        val mensaje:String? = bundle?.getString("Mensaje")

        textView3.setText(mensaje)
        textNombre.setText(nombre)

        //Eliminar producto
        btnSi.setOnClickListener {
            //PRODUCTO
            if (mensaje.equals("¿ELIMINAR PRODUCTO?")){
            db.collection("Productos").document(nombre.toString())
                .delete()
                .addOnSuccessListener {
                    val operacionExitosaIntent = Intent(this, OperacionExitosa::class.java).apply {
                        putExtra("Mensaje","Producto eliminado.")
                        putExtra("email",email)
                    }
                    startActivity(operacionExitosaIntent)
                    }
            }
            //PROMOCION
            if (mensaje.equals("¿ELIMINAR PROMOCION?")){
                db.collection("Promociones").document(nombre.toString())
                    .delete()
                    .addOnSuccessListener {
                        val operacionExitosaIntent = Intent(this, OperacionExitosa::class.java).apply {
                            putExtra("Mensaje","Promocion eliminada.")
                            putExtra("email",email)
                        }
                        startActivity(operacionExitosaIntent)
                    }
            }

        }

        //Cacelar
        btnNo.setOnClickListener {
            val inicioAdminIntent = Intent(this, inicio_Admin::class.java).apply {
                putExtra("email",email)
            }
            startActivity(inicioAdminIntent)
        }
    }
}