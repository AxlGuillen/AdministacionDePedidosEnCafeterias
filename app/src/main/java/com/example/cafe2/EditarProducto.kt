package com.example.cafe2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_auth.emailEditText
import kotlinx.android.synthetic.main.activity_editar_producto.btnCancelar
import kotlinx.android.synthetic.main.activity_editar_producto.btnNoti4
import kotlinx.android.synthetic.main.activity_editar_producto.btnPerfil4
import kotlinx.android.synthetic.main.activity_editar_producto.btnRegresar5
import kotlinx.android.synthetic.main.activity_editar_producto.btnSave5

class EditarProducto : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_producto)


        //boton de la flechita
        btnRegresar5.setOnClickListener {
            onBackPressed()
        }

        //boton de cancelar
        btnCancelar.setOnClickListener {
            onBackPressed()
        }

        //muestra la interfaz de perfil
        btnPerfil4.setOnClickListener {
            val perfilIntent = Intent(this, Perfil::class.java)
            startActivity(perfilIntent)
        }

        //No hace nada es el de NOTIFICACIONES
        btnNoti4.setOnClickListener {

        }

        //Guarda los cambios
        btnSave5.setOnClickListener {

        }


    }


}