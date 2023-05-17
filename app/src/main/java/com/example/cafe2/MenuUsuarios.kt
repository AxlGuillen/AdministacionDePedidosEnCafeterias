package com.example.cafe2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_menu_usuarios.btnCajeros

class MenuUsuarios : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_usuarios)

        btnCajeros.setOnClickListener {
            val registrarCajerosIntent = Intent(this, RegistrarCajerosAdm::class.java).apply {  }
            startActivity(registrarCajerosIntent)
        }
    }
}