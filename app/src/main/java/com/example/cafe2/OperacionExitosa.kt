package com.example.cafe2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_operacion_exitosa.btnSi
import kotlinx.android.synthetic.main.activity_home.textView
import kotlinx.android.synthetic.main.activity_operacion_exitosa.btnOk
import kotlinx.android.synthetic.main.activity_operacion_exitosa.textMensaje


class OperacionExitosa : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operacion_exitosa)


        btnSi.setOnClickListener {
            val homeIntent = Intent(this, NuevoProducto::class.java).apply {

                intent.extras
                val bundle = intent.extras
                val mensaje: String? = bundle?.getString("Mensaje")
                textMensaje.text = mensaje}

                btnOk.setOnClickListener {
                    val homeAdmIntent = Intent(this, inicio_Admin::class.java).apply {

                    }
                    startActivity(homeAdmIntent)
                }


        }
    }
}