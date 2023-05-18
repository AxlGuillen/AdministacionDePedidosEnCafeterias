package com.example.cafe2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.cafe2.AuthActivity
import com.example.cafe2.R.layout.activity_auth
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_auth.emailEditText
import kotlinx.android.synthetic.main.activity_auth.loginButton
import kotlinx.android.synthetic.main.activity_auth.passwordEditText
import kotlinx.android.synthetic.main.activity_auth.signUpButton

class AuthActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_auth)


    //setup
        setup()

    }
    private fun setup(){
        title = "Autentificacion"

       signUpButton.setOnClickListener{
           print("Boton picadoooooo")
           showRegistrar()
       }

        loginButton.setOnClickListener{
            Log.i("Boton de login clickeadoooo","Mensaje de prueba")
            if ( emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()){
                //Aqui autentificamos que este el usuario dado de alta en la base de datos
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(emailEditText.text.toString(),passwordEditText.text.toString())
                    .addOnCompleteListener{
                        if (it.isSuccessful){
                            //aqui accedemos a la base de datos para checar el rol
                            val Rol = db.collection("Usuarios").document(emailEditText.text.toString()).get().addOnSuccessListener {
                                val Rol = (it.get("Rol") as String?).toString()
                                //Mandamos a la pantalla principal de cliente
                                if(Rol.equals("Cliente")){
                                    showHome(emailEditText.text.toString())
                                }
                                //Mandamos a la pantalla principal de Administrador
                                if(Rol.equals("Administrador")){
                                    //editar
                                    showRegistrar()
                                }
                                //Mandamos a la pantalla principal de Cajero
                                if(Rol.equals("Cajero")){}
                            }
                    }
                    else { showAlert() }
                    }
            }
        }
    }




    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }


    private fun showHome(email:String){

        val homeIntent = Intent(this, inicio_Admin::class.java).apply {
            putExtra("email",email)
        }
        startActivity(homeIntent)

    }

    private fun showRegistrar(){
        val RegistrarIntent = Intent (this, RegistrarActivity::class.java)
        startActivity(RegistrarIntent)
    }
}