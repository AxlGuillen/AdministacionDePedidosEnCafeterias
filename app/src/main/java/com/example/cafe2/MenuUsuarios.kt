package com.example.cafe2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_menu_usuarios.btnCajeros
import kotlinx.android.synthetic.main.activity_menu_usuarios.btnClientes
import kotlinx.android.synthetic.main.activity_menu_usuarios.btnNoti2
import kotlinx.android.synthetic.main.activity_menu_usuarios.btnPerfil2
import kotlinx.android.synthetic.main.activity_menu_usuarios.btnRegresar2
import kotlinx.android.synthetic.main.activity_menu_usuarios.btnMenuuu
import kotlinx.android.synthetic.main.activity_menu_usuarios.btnPromos2
import kotlinx.android.synthetic.main.activity_menu_usuarios.btnUSUARIOSS
import kotlinx.android.synthetic.main.activity_menu_usuarios.btnVentas2

class MenuUsuarios : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_usuarios)

        //sacamos el email para mandarlo a otro lados
        intent.extras
        val bundle = intent.extras
        val email:String? = bundle?.getString("email")

        //FLECHITA
        btnRegresar2.setOnClickListener {
            onBackPressed()
        }

        //PERFIL
        btnPerfil2.setOnClickListener {
            val perfilIntent = Intent(this, Perfil::class.java).apply {
                putExtra("email",email)
            }
            startActivity(perfilIntent)
        }

        //NOTIFICACIONES
        btnNoti2.setOnClickListener {
            val Intent = Intent(this, Notificaciones::class.java).apply {
                putExtra("email",email)
            }
            startActivity(Intent)
        }

        //CAJEROS
        btnCajeros.setOnClickListener {
            val registrarCajerosIntent = Intent(this, menu_cajeros_admin::class.java).apply {
                putExtra("email",email)
            }
            startActivity(registrarCajerosIntent)
        }

        //CLIENTES
        btnClientes.setOnClickListener{
            val menuClientesAdm = Intent(this, menu_clientes_admin::class.java).apply {
                putExtra("email",email)
            }
            startActivity(menuClientesAdm)

        }

        //HISTORIAL
        btnVentas2.setOnClickListener {
            val intent = Intent(this, menu_historial::class.java).apply {
                putExtra("email",email)
            }
            startActivity(intent)
        }

        //MENU
        btnMenuuu.setOnClickListener {
            val menuIntent = Intent(this, menu_admin::class.java).apply {
                putExtra("email",email)
            }
            startActivity(menuIntent)
        }

        //USUARIOS EN ESTE ESTAMOS
        btnUSUARIOSS.setOnClickListener {
            val menuUsuariosIntent = Intent(this, MenuUsuarios::class.java).apply {
                putExtra("email",email)
            }
            startActivity(menuUsuariosIntent)
        }

        //PROMOCIONES
        btnPromos2.setOnClickListener {
            val notificacionIntent = Intent(this, promos_admin::class.java).apply {
                putExtra("email",email)
            }
            startActivity(notificacionIntent)
        }
    }
}