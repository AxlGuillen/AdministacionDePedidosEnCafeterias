package com.example.cafe2

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_auth.emailEditText
import kotlinx.android.synthetic.main.activity_editar_perfil.imageView6
import kotlinx.android.synthetic.main.activity_imagen_prueba.imageView10
import kotlinx.android.synthetic.main.activity_perfil.btnEditarPerfil
import kotlinx.android.synthetic.main.activity_perfil.btnNoti2
import kotlinx.android.synthetic.main.activity_perfil.btnRegresar2
import kotlinx.android.synthetic.main.activity_perfil.edadPerfil
import kotlinx.android.synthetic.main.activity_perfil.imageView5
import kotlinx.android.synthetic.main.activity_perfil.nombrePerfil
import kotlinx.android.synthetic.main.activity_perfil.telefonoPerfil
import java.io.File

class Perfil : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        intent.extras
        val bundle = intent.extras
        val email:String? = bundle?.getString("email")

        Log.d("perfilEmail", email.toString())

        if (email != null) {
            db.collection("Usuarios").document(email).get().addOnSuccessListener {
                val Nombre = (it.get("Nombre") as String?).toString()
                val Edad = (it.get("Edad") as String?)
                val Telefono = (it.get("Telefono") as String?)

                val storageRef = FirebaseStorage.getInstance().reference.child("images/${email.toString()}.jpg")
                val localfile = File.createTempFile("tempImage","jpg")
                storageRef.getFile(localfile).addOnSuccessListener {
                    val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                    imageView5.setImageBitmap(bitmap)
                }.addOnFailureListener{
                    Toast.makeText(this,"Failed to download the image", Toast.LENGTH_LONG).show()

                }

                nombrePerfil.text = Nombre
                edadPerfil.text = Edad.toString()
                telefonoPerfil.text = Telefono.toString()
        }
        }

        //MUESTRA LA PANTALLA DE EDITAR PERFIL Y LE MANDA todos los datos
        btnEditarPerfil.setOnClickListener {
            val editarPerfilIntent = Intent(this, EditarPerfil::class.java).apply {
                putExtra("email",email)
                putExtra("Nombre", nombrePerfil.text.toString())
                putExtra("Edad", edadPerfil.text.toString())
                putExtra("Telefono", telefonoPerfil.text.toString())
            }
            startActivity(editarPerfilIntent)
        }

        btnNoti2.setOnClickListener {
            val Intent = Intent(this, Notificaciones::class.java).apply {
                putExtra("email",email)
            }
            startActivity(Intent)
        }

        //boton de la felchita
        btnRegresar2.setOnClickListener {
            onBackPressed()
        }
    }
}