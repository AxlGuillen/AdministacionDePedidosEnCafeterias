package com.example.cafe2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_inicio_cajero.btnNoti
import kotlinx.android.synthetic.main.activity_inicio_cajero.btnNoti3
import kotlinx.android.synthetic.main.activity_inicio_cajero.btnNoti4
import kotlinx.android.synthetic.main.activity_inicio_cajero.btnPerfilInicioAdm

class inicio_cajero : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_cajero)

        //sacamos el email para mandarlo a otro lados
        intent.extras
        val bundle = intent.extras
        val email:String? = bundle?.getString("email")

        //NOTIFICACIONES
        btnNoti.setOnClickListener {
            val Intent = Intent(this, Notificaciones::class.java).apply {
                putExtra("email",email)
            }
            startActivity(Intent)
        }

        //Perfil
        btnPerfilInicioAdm.setOnClickListener {
            val perfilIntent = Intent(this, Perfil::class.java).apply {
                putExtra("email",email)
            }
            startActivity(perfilIntent)
        }

        //PEDIDOS NUEVOS
        btnNoti3.setOnClickListener {
            val perfilIntent = Intent(this, pedidos_activos_cliente::class.java).apply {
                putExtra("email",email)
                putExtra("porAceptar","porAceptar")

            }
            startActivity(perfilIntent)
        }

        //PEDIDOS YA ACEPTADOS
        btnNoti4.setOnClickListener {
            val perfilIntent = Intent(this, pedidos_activos_cliente::class.java).apply {
                putExtra("email",email)
            }
            startActivity(perfilIntent)
        }
    }
}