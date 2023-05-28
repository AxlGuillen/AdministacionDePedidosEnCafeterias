package com.example.cafe2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_operacion_exitosa.btnSi
import kotlinx.android.synthetic.main.activity_home.textView
import kotlinx.android.synthetic.main.activity_operacion_exitosa.textMensaje


class OperacionExitosa : AppCompatActivity() {
    private lateinit var  db : FirebaseFirestore
    var Rol = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        db = FirebaseFirestore.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operacion_exitosa)

        intent.extras
        val bundle = intent.extras
        val mensaje: String? = bundle?.getString("Mensaje")
        val email: String? = bundle?.getString("email")
        textMensaje.text = mensaje
        obtenerRol()

        btnSi.setOnClickListener {
            Log.d("Este es el Rol", "ESTE ES EL ROL: $Rol")
            if(Rol.equals("Administrador")){
                    val homeAdmIntent = Intent(this, inicio_Admin::class.java).apply {
                        putExtra("email",email)

                    }
                    startActivity(homeAdmIntent)
                finish()
            }
            if(Rol.equals("Cliente")){
                val homeAdmIntent = Intent(this, inicio_clientes::class.java).apply {
                    putExtra("email",email)
                }
                startActivity(homeAdmIntent)
                finish()
            }
                }
        }

    private  fun  obtenerRol(){

        intent.extras
        val bundle = intent.extras
        val email: String? = bundle?.getString("email")


        db.collection("Usuarios").document(email.toString()).get().addOnSuccessListener {
            Rol = (it.get("Rol") as String?).toString()
            Log.d("Este es el Rol", "ESTE ES EL ROL: $Rol")

        }
    }
    }
