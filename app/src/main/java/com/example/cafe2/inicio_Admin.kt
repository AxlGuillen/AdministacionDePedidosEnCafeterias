package com.example.cafe2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_inicio_admin.btnMenu

class inicio_Admin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_admin)

        //Abre el menu pero provisionalmente solo abre el de agregar nuevo producto
        btnMenu.setOnClickListener {
            val menuIntent = Intent(this, NuevoProducto::class.java)
            startActivity(menuIntent)
        }
    }

}