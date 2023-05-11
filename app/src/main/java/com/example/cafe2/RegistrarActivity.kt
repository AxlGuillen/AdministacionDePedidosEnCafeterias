package com.example.cafe2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_registrar.btnRegistrar
import kotlinx.android.synthetic.main.activity_registrar.emailRegistrar
import kotlinx.android.synthetic.main.activity_registrar.passRegistrar
import com.google.firebase.auth.FirebaseAuth


class RegistrarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)

        Registrarse()

    }

    private fun Registrarse(){
        title = "Registrarse"

        btnRegistrar.setOnClickListener{
            if (emailRegistrar.text.isNotEmpty() && passRegistrar.text.isNotEmpty()){
                print(emailRegistrar)
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(emailRegistrar.text.toString(),passRegistrar.text.toString())
                    .addOnCompleteListener{ if (it.isSuccessful){
                        showAuth()
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

    private fun showAuth(){
        val authIntent = Intent(this, AuthActivity::class.java)
        startActivity(authIntent)

    }
}
