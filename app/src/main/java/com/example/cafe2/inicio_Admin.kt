package com.example.cafe2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_inicio_admin.btnMenu
import kotlinx.android.synthetic.main.activity_inicio_admin.btnNoti3

import kotlinx.android.synthetic.main.activity_inicio_admin.btnPerfilInicioAdm
import kotlinx.android.synthetic.main.activity_inicio_admin.btnPromos
import kotlinx.android.synthetic.main.activity_inicio_admin.btnUsuarios
import kotlinx.android.synthetic.main.activity_inicio_admin.btnVentas

class inicio_Admin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_admin)

        //extraemos el email para mandarlo a las siguiente pantallas, creo que no es necesario mnandarlo aqui
        intent.extras
        val bundle = intent.extras
        val email:String? = bundle?.getString("email")

        //PERFIL
        btnPerfilInicioAdm.setOnClickListener {
            val perfilIntent = Intent(this, Perfil::class.java).apply {
                putExtra("email",email)
            }
            startActivity(perfilIntent)
        }

        //NOTIFICACIONES
        btnNoti3.setOnClickListener {

        }

        //VENTAS
        btnVentas.setOnClickListener {

        }

        //PROMOS
        btnPromos.setOnClickListener {
            val notificacionIntent = Intent(this, promos_admin::class.java).apply {
                putExtra("email",email)
            }
            startActivity(notificacionIntent)
        }

        //MENU
        btnMenu.setOnClickListener {
            val menuIntent = Intent(this, menu_admin::class.java).apply {
                putExtra("email",email)
            }
            startActivity(menuIntent)
        }

        //MENU USUARIOS
        btnUsuarios.setOnClickListener {
            val menuUsuariosIntent = Intent(this, MenuUsuarios::class.java).apply {
                putExtra("email",email)
            }
            startActivity(menuUsuariosIntent)
        }
    }

}