package com.example.cafe2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class EditarMiPedido : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_mi_pedido)

        //sacamos el email para mandarlo a otro lados
        intent.extras
        val bundle = intent.extras
        val email:String? = bundle?.getString("email")
        val producto:String? = bundle?.getString("producto")
        val precio:String? = bundle?.getString("precio")
        val comentarios:String? = bundle?.getString("comentarios")
        val cantidad:String? = bundle?.getString("cantidad")

        Toast.makeText(this,"La cantidad es: " + email, Toast.LENGTH_LONG).show()
    }
}