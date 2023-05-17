package com.example.cafe2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_editar_perfil.btnRegresar2

class EditarPerfil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)

        //boton de la felchita
        btnRegresar2.setOnClickListener {
            onBackPressed()
        }
    }
}