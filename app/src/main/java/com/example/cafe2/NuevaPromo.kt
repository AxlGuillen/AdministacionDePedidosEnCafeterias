package com.example.cafe2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_nueva_promo.btnRegresar5

class NuevaPromo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_promo)

        //boton de la flechita
        btnRegresar5.setOnClickListener {
            onBackPressed()
        }
    }
}