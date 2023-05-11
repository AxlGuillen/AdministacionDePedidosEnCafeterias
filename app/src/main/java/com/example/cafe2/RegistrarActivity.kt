package com.example.cafe2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.util.PatternsCompat
import kotlinx.android.synthetic.main.activity_registrar.btnRegistrar
import kotlinx.android.synthetic.main.activity_registrar.emailRegistrar
import kotlinx.android.synthetic.main.activity_registrar.passRegistrar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_registrar.edadRegistrar
import kotlinx.android.synthetic.main.activity_registrar.nombreRegistrar
import kotlinx.android.synthetic.main.activity_registrar.telefonoRegistrar


class RegistrarActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)

        Registrarse()

    }

    private fun Registrarse(){
        title = "Registrarse"

        btnRegistrar.setOnClickListener{


            if (validarDatos()){
                print(emailRegistrar)
                Toast.makeText(this, "DatosValidados", Toast.LENGTH_SHORT).show()
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(emailRegistrar.text.toString(),passRegistrar.text.toString())
                    .addOnCompleteListener{ if (it.isSuccessful){
                        showAuth()
                    }
                    else { showAlert() }
                    }
                val email = emailRegistrar.text
                //Aqui se guarda en la base de datos
                db.collection("Usuarios").document(email.toString()).set(
                    hashMapOf("Edad" to edadRegistrar.text.toString(),
                    "Estado" to true,
                        "Nombre" to nombreRegistrar.text.toString(),
                        "Privilegios" to false,
                        "Rol" to "Cliente",
                        "Telefono" to telefonoRegistrar.text.toString()
                        )
                )//datos a guardar: Edad, estado, Nombre,Privilegios,Rol,Telefono


            }

        }
        }

    private fun validarDatos(): Boolean {
        //&& passRegistrar.text.isNotEmpty()
        var isValid = true
        //se valida el Email
        if(emailRegistrar.text.isBlank()){
            isValid = false;
            emailRegistrar.error = "Se requiere email"
        }
        else{
            //se valida si el email es valido o no.
        if(!PatternsCompat.EMAIL_ADDRESS.matcher(emailRegistrar.text).matches()){
            isValid = false;
            emailRegistrar.error = "Por favor escriba un correo valido"
        }
            else{
                //se resetea el mensaje de error
                emailRegistrar.error=null;
            }
        }

    //se valida que la contraseña no este vacia
        if(passRegistrar.text.isEmpty()){
            isValid = false;
            passRegistrar.error = "Se requiere contraseña"

        } else{
            //se valida que la contraseña tenga mas de 7 digitos
            if(passRegistrar.length()<7)
            passRegistrar.error = "La contraseña debe ser mas larga"
            else
            passRegistrar.error=null}
        //se valida que el nombre no este vacio
        if(nombreRegistrar.text.isBlank()){
            isValid = false;
            nombreRegistrar.error = "Se requiere nombre"
        }else{
            //se resetea el error
            nombreRegistrar.error = null}

        //se comprueba que el campo del telefono no este vacio
        if(telefonoRegistrar.text.isBlank()){
            isValid = false;
            telefonoRegistrar.error = "Se requiere telefono"
        }else{telefonoRegistrar.error = null}

        //se comprueba que el campo de edad no este vacio
        if(edadRegistrar.text.isBlank()){
            isValid = false;
            edadRegistrar.error = "Se requiere edad"
        }else{edadRegistrar.error = null}

        // este es el valor que indica si los datos fueron validados o no
        return isValid
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
