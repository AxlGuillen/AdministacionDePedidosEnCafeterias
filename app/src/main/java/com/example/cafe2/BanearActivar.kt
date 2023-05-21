package com.example.cafe2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ToggleButton
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_banear_activar.actulizarCambios
import kotlinx.android.synthetic.main.activity_banear_activar.button4
import kotlinx.android.synthetic.main.activity_banear_activar.correo
import kotlinx.android.synthetic.main.activity_banear_activar.estado2
import kotlinx.android.synthetic.main.activity_banear_activar.nombre
import kotlinx.android.synthetic.main.activity_editar_perfil.edadRegistrar2
import kotlinx.android.synthetic.main.activity_editar_perfil.editTextNumber2
import kotlinx.android.synthetic.main.activity_editar_perfil.nombreEditText

class BanearActivar : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banear_activar)

        val usuario = intent.getParcelableExtra<clienteModel>("Usuario")
        val email = intent.extras?.getString("email")
        var x = false

        if (usuario != null){
            correo.setText(usuario.Email)
            nombre.setText(usuario.Nombre)
            db.collection("Usuarios").document(usuario.Email.toString()).get().addOnSuccessListener {
                val estado = (it.get("Estado") as Boolean?)
                    if (estado == true){
                        estado2.toggle()
                        x = true
                    }
                }

        }

        estado2.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                x = true
            } else {
                x = false
            }
        }

        //FLECHITA
        button4.setOnClickListener {
            onBackPressed()
        }

        //ACTUALIZAR
        actulizarCambios.setOnClickListener {

            val a = db.collection("Usuarios").document(usuario?.Email.toString())
            a.update("Estado", x)

            val intent = Intent(this, OperacionExitosa::class.java).apply {
                putExtra("Mensaje","Usuario actualizado.")
                putExtra("email",email)
            }
            startActivity(intent)
        }
    }
}