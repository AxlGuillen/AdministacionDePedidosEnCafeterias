package com.example.cafe2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_menu_usuarios.btnCajeros
//import kotlinx.android.synthetic.main.activity_menu_usuarios.btnClientes
import kotlinx.android.synthetic.main.activity_menu_usuarios.btnPerfil2
import kotlinx.android.synthetic.main.activity_menu_usuarios.btnRegresar2
import kotlinx.android.synthetic.main.activity_menu_usuarios.btnUsuarios2

class MenuUsuarios : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_usuarios)

        //manda proviisonalmente a registrar cajero pero deberia llevar al recycle de cajeros
        btnCajeros.setOnClickListener {
            val registrarCajerosIntent = Intent(this, menu_cajeros_admin::class.java).apply {  }
            startActivity(registrarCajerosIntent)
        }
        /*btnClientes.setOnClickListener{
            val menuClientesAdm = Intent(this, menu_clientes_admin::class.java).apply {  }
            startActivity(menuClientesAdm)

        }*/

        //boton de la flechita
        btnRegresar2.setOnClickListener {
            onBackPressed()
        }

        //abre el perfil
        btnPerfil2.setOnClickListener {
            val perfilIntent = Intent(this, Perfil::class.java)
            startActivity(perfilIntent)
        }
    }
}