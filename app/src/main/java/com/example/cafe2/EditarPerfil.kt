package com.example.cafe2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_editar_perfil.btnCancelar3
import kotlinx.android.synthetic.main.activity_editar_perfil.btnRegresar2
import kotlinx.android.synthetic.main.activity_editar_perfil.btnSave3
import kotlinx.android.synthetic.main.activity_editar_perfil.edadRegistrar2
import kotlinx.android.synthetic.main.activity_editar_perfil.editTextNumber2
import kotlinx.android.synthetic.main.activity_editar_perfil.nombreEditText

class EditarPerfil : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)

        intent.extras
        val bundle = intent.extras
        val email:String? = bundle?.getString("email")
        val nombre:String? = bundle?.getString("Nombre")
        val edad:String? = bundle?.getString("Edad")
        val telefono:String? = bundle?.getString("Telefono")

        nombreEditText.setText(nombre.toString())
        edadRegistrar2.setText(edad)
        editTextNumber2.setText(telefono)


        //boton de la felchita
        btnRegresar2.setOnClickListener {
            onBackPressed()
        }

        //boton de cancelar
        btnCancelar3.setOnClickListener {
            onBackPressed()
        }

        btnSave3.setOnClickListener {
            if (email!=null){
                val a = db.collection("Usuarios").document(email)
                a.update("Nombre", nombreEditText.text.toString())
                a.update("Edad", edadRegistrar2.text.toString())
                a.update("Telefono", editTextNumber2.text.toString())

                val OperacionExitosaIntent = Intent(this, OperacionExitosa::class.java).apply {
                    putExtra("Mensaje","Datos actualizados.")
                    putExtra("email",email)
                }
                startActivity(OperacionExitosaIntent)
            }
        }
    }
}