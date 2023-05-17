package com.example.cafe2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_inicio_admin.btnMenu
import kotlinx.android.synthetic.main.activity_inicio_admin.btnNoti3
import kotlinx.android.synthetic.main.activity_inicio_admin.btnPerfilInicioAdm
import kotlinx.android.synthetic.main.activity_inicio_admin.btnUsuarios

class inicio_Admin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_admin)

        //extraemos el email para mandarlo a las siguiente pantallas, creo que no es necesario mnandarlo aqui
        //intent.extras
        //val bundle = intent.extras
        //val email:String? = bundle?.getString("email")


        //abre el perfil
        btnPerfilInicioAdm.setOnClickListener {
            val perfilIntent = Intent(this, Perfil::class.java)
            startActivity(perfilIntent)
        }

        //Falta el boton dela notificacion****
        btnNoti3.setOnClickListener {
            //val notificacionIntent = Intent(this, ::class.java)
            //startActivity(notificacionIntent)
        }

        //Abre el menu pero provisionalmente solo abre el de agregar nuevo producto
        btnMenu.setOnClickListener {
            val menuIntent = Intent(this, menu_admin::class.java)
            startActivity(menuIntent)
        }

        //ABRE EL MENU DE USUARIOS (CLIENTES O CAJEROS)
        btnUsuarios.setOnClickListener {
            val menuUsuariosIntent = Intent(this, MenuUsuarios::class.java)
            startActivity(menuUsuariosIntent)
        }
    }

}