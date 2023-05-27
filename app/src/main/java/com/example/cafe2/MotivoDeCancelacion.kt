package com.example.cafe2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_motivo_de_cancelacion.enviar
import kotlinx.android.synthetic.main.activity_motivo_de_cancelacion.motivo

class MotivoDeCancelacion : AppCompatActivity() {

    private lateinit var  db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motivo_de_cancelacion)

        val destinatario = intent.extras?.getString("destinatario")
        val numero = intent.extras?.getString("numero")
        val email = intent.extras?.getString("email")


        enviar.setOnClickListener {
            db = FirebaseFirestore.getInstance()
            val a = db.collection("Pedidos").document(numero.toString())
            a.update("status","cancelado").addOnCompleteListener {
                //notiicacion de que su pedido fue aceptado
                db.collection("Notificaciones").document().set(
                    hashMapOf("Destinatario" to destinatario.toString(),
                        "Descripcion" to motivo.text.toString(),
                        "Estado" to false,
                        "Titulo" to "Pedido cancelado"
                    )
                ).addOnCompleteListener {
                    val perfilIntent = Intent(this, inicio_cajero::class.java).apply {
                        putExtra("email",email)
                    }
                    startActivity(perfilIntent)
                }
            }
        }
    }
}