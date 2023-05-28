package com.example.cafe2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.util.PatternsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.android.synthetic.main.activity_registrar.btnRegistrarRegistrarCajero
import kotlinx.android.synthetic.main.activity_registrar.edadRegistrarRegistrarCajero
import kotlinx.android.synthetic.main.activity_registrar.emailRegistrarRegistrarCajero
import kotlinx.android.synthetic.main.activity_registrar.nombreRegistrarRegistrarCajero
import kotlinx.android.synthetic.main.activity_registrar.passRegistrarRegistrarCajero
import kotlinx.android.synthetic.main.activity_registrar.telefonoRegistrarRegistrarCajero
import kotlinx.android.synthetic.main.activity_registrar_cajeros_adm.btnFlechita

class RegistrarCajerosAdm : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_cajeros_adm)

        AgregarCajero()

        //boton de la flechita
        btnFlechita.setOnClickListener {
            onBackPressed()
        }
    }

    private fun AgregarCajero(){
        title = "Agregar Cajero"

        btnRegistrarRegistrarCajero.setOnClickListener{


            if (validarDatos()){
                print(emailRegistrarRegistrarCajero)
                Toast.makeText(this, "DatosValidados", Toast.LENGTH_SHORT).show()
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(emailRegistrarRegistrarCajero.text.toString(),passRegistrarRegistrarCajero.text.toString())
                    .addOnCompleteListener{ if (it.isSuccessful){
                        showOperacionExitosaAgregando()
                    }
                    else { showAlert() }
                    }
                val email = emailRegistrarRegistrarCajero.text
                //Aqui se guarda en la base de datos
                db.collection("Usuarios").document(email.toString()).set(
                    hashMapOf("Edad" to edadRegistrarRegistrarCajero.text.toString(),
                        "Estado" to true,
                        "Nombre" to nombreRegistrarRegistrarCajero.text.toString(),
                        "Privilegios" to false,
                        "Rol" to "Cajero",
                        "Telefono" to telefonoRegistrarRegistrarCajero.text.toString()
                    )
                )//datos a guardar: Edad, estado, Nombre,Privilegios,Rol,Telefono


            }

        }
    }

    private fun validarDatos(): Boolean {
        //&& passRegistrar.text.isNotEmpty()
        var isValid = true
        //se valida el Email
        if(emailRegistrarRegistrarCajero.text.isBlank()){
            isValid = false;
            emailRegistrarRegistrarCajero.error = "Se requiere email"
        }
        else{
            //se valida si el email es valido o no.
            if(!PatternsCompat.EMAIL_ADDRESS.matcher(emailRegistrarRegistrarCajero.text).matches()){
                isValid = false;
                emailRegistrarRegistrarCajero.error = "Por favor escriba un correo valido"
            }
            else{
                //se resetea el mensaje de error
                emailRegistrarRegistrarCajero.error=null;
            }
        }

        //se valida que la contraseña no este vacia
        if(passRegistrarRegistrarCajero.text.isEmpty()){
            isValid = false;
            passRegistrarRegistrarCajero.error = "Se requiere contraseña"

        } else{
            //se valida que la contraseña tenga mas de 7 digitos
            if(passRegistrarRegistrarCajero.length()<=7){
                isValid = false;
                passRegistrarRegistrarCajero.error = "La contraseña debe ser mas larga"}
            else
                passRegistrarRegistrarCajero.error=null}
        //se valida que el nombre no este vacio
        if(nombreRegistrarRegistrarCajero.text.isBlank()){
            isValid = false;
            nombreRegistrarRegistrarCajero.error = "Se requiere nombre"
        }else{
            //se resetea el error
            nombreRegistrarRegistrarCajero.error = null}

        //se comprueba que el campo del telefono no este vacio
        if(telefonoRegistrarRegistrarCajero.text.isBlank()){
            isValid = false;
            telefonoRegistrarRegistrarCajero.error = "Se requiere telefono"
        }else{telefonoRegistrarRegistrarCajero.error = null}

        //se comprueba que el campo de edad no este vacio
        if(edadRegistrarRegistrarCajero.text.isBlank()){
            isValid = false;
            edadRegistrarRegistrarCajero.error = "Se requiere edad"
        }else{edadRegistrarRegistrarCajero.error = null}

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

    private fun showOperacionExitosaAgregando(){
        intent.extras
        val bundle = intent.extras
        val email: String? = bundle?.getString("email")

        val OperacionExitosaIntent = Intent(this, OperacionExitosa::class.java).apply {
            putExtra("Mensaje","El cajero fue agregado correctamente.")
            putExtra("email",email)
        }
        startActivity(OperacionExitosaIntent)

    }
}