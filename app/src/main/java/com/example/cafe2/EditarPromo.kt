package com.example.cafe2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_editar_promo.btnCancelar4
import kotlinx.android.synthetic.main.activity_editar_promo.btnRegresar4

class EditarPromo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_promo)

        //boton de la flechita
        btnRegresar4.setOnClickListener {
            onBackPressed()
        }

        //CANCELAR
        btnCancelar4.setOnClickListener {
            onBackPressed()
        }
    }
}