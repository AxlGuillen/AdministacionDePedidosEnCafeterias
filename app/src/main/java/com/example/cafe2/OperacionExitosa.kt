package com.example.cafe2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_operacion_exitosa.btnSi

class OperacionExitosa : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operacion_exitosa)

        btnSi.setOnClickListener {
            val homeIntent = Intent(this, NuevoProducto::class.java).apply {
            }
            startActivity(homeIntent)
        }

    }
}