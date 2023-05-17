package com.example.cafe2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_perfil.btnEditarPerfil
import kotlinx.android.synthetic.main.activity_perfil.btnRegresar2

class Perfil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        intent.extras
        val bundle = intent.extras
        val email:String? = bundle?.getString("email")


        //MUESTRA LA PANTALLA DE EDITAR PERFIL Y LE MANDA EMAIL
        btnEditarPerfil.setOnClickListener {
            val editarPerfilIntent = Intent(this, inicio_Admin::class.java).apply {
                putExtra("email",email)
            }
            startActivity(editarPerfilIntent)
        }

        //boton de la felchita
        btnRegresar2.setOnClickListener {
            onBackPressed()
        }
    }
}