package com.example.cafe2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.cafe2.R.layout.activity_auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_auth.btnRestablecerContraseña
import kotlinx.android.synthetic.main.activity_auth.emailLogin
import kotlinx.android.synthetic.main.activity_auth.loginButton
import kotlinx.android.synthetic.main.activity_auth.passwordEditText
import kotlinx.android.synthetic.main.activity_auth.signUpButton


class AuthActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_auth)

        firebaseAuth = Firebase.auth

        var correo: TextView = findViewById(R.id.emailLogin)

        btnRestablecerContraseña.setOnClickListener {
            sendPasswordReset(correo.text.toString())
        }

        //setup
        setup()

    }

    private fun setup() {
        title = "Autentificacion"

        signUpButton.setOnClickListener {
            showRegistrar()
        }

        loginButton.setOnClickListener {
            Log.i("Boton de login clickeadoooo", "Mensaje de prueba")
            if (emailLogin.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
                //Aqui autentificamos que este el usuario dado de alta en la base de datos
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(
                        emailLogin.text.toString(),
                        passwordEditText.text.toString()
                    )
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            //aqui accedemos a la base de datos para checar el rol

                            val Rol = db.collection("Usuarios").document(emailLogin.text.toString()).get().addOnSuccessListener {
                                val Rol = (it.get("Rol") as String?).toString()
                                val Estado = (it.get("Estado") as Boolean?)
                                //Mandamos a la pantalla principal de cliente

                                if(Rol.equals("Cliente") && Estado==true){
                                    //showHome(emailLogin.text.toString())
                                    val homeIntent = Intent(this, inicio_clientes::class.java).apply {
                                        putExtra("email",emailLogin.text.toString())
                                    }
                                    startActivity(homeIntent)
                                    finish()
                                }
                                //Mandamos a la pantalla principal de Administrador
                                if(Rol.equals("Administrador")){
                                    //editar
                                    val homeIntent = Intent(this, inicio_Admin::class.java).apply {
                                        putExtra("email",emailLogin.text.toString())
                                    }
                                    startActivity(homeIntent)
                                }
                                //Mandamos a la pantalla principal de Cajero
                                if(Rol.equals("Cajero")){
                                    val homeIntent = Intent(this, inicio_cajero::class.java).apply {
                                        putExtra("email",emailLogin.text.toString())
                                    }
                        } else {
                            showAlert()
                        }
                    }
            }
        }
    }


    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }


    private fun showHome(email: String) {

        val homeIntent = Intent(this, inicio_Admin::class.java).apply {
            putExtra("email", email)
        }
        startActivity(homeIntent)

    }

    private fun showRegistrar() {
        val RegistrarIntent = Intent(this, RegistrarActivity::class.java)
        startActivity(RegistrarIntent)
    }

    private fun sendPasswordReset(email: String) {
        if (email.toString().isNotEmpty()) {
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    Toast.makeText(
                        baseContext,
                        "Correo de recuperacion enviado.",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(baseContext, "Error al enviar el email.", Toast.LENGTH_LONG)
                        .show()
                }
            }
        } else {
            Toast.makeText(
                baseContext,
                "Ingresa un Email Valido",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}