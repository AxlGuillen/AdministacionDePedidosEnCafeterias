package com.example.cafe2

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_editar_perfil.btnCancelar3
import kotlinx.android.synthetic.main.activity_editar_perfil.btnNoti2
import kotlinx.android.synthetic.main.activity_editar_perfil.btnRegresar2
import kotlinx.android.synthetic.main.activity_editar_perfil.btnSave3
import kotlinx.android.synthetic.main.activity_editar_perfil.btnSeleccionarFoto
import kotlinx.android.synthetic.main.activity_editar_perfil.edadRegistrar2
import kotlinx.android.synthetic.main.activity_editar_perfil.editTextNumber2
import kotlinx.android.synthetic.main.activity_editar_perfil.imageView6
import kotlinx.android.synthetic.main.activity_editar_perfil.nombreEditText
import kotlinx.android.synthetic.main.activity_imagen_prueba.imageView10
import kotlinx.android.synthetic.main.activity_nuevo_producto.imageView3
import kotlinx.android.synthetic.main.activity_nuevo_producto.nombreProducto
import java.io.File

class EditarPerfil : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    var firebaseStorage: FirebaseStorage? =  null
    lateinit var ImageUri: Uri

    companion object {
        val IMAGE_REQUEST_CODE = 1_000;
    }

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



        val storageRef = FirebaseStorage.getInstance().reference.child("images/${email.toString()}.jpg")
        val localfile = File.createTempFile("tempImage","jpg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            imageView6.setImageBitmap(bitmap)
        }.addOnFailureListener{
            Toast.makeText(this,"Failed to download the image", Toast.LENGTH_LONG).show()

        }


        //boton de la felchita
        btnRegresar2.setOnClickListener {
            onBackPressed()
        }

        //NOTIFICACIONES
        btnNoti2.setOnClickListener {

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
                if (ImageUri != null) {
                    val fileName = "$email.jpg"

                    val refStorage = FirebaseStorage.getInstance().reference.child("images/$fileName")

                    refStorage.putFile(ImageUri)
                        .addOnSuccessListener(
                            OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                                taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                                    val imageUrl = it.toString()
                                }
                            })

                        ?.addOnFailureListener(OnFailureListener { e ->
                            print(e.message)
                        })
                }
                startActivity(OperacionExitosaIntent)
            }



        }

        btnSeleccionarFoto.setOnClickListener {
            seleccionarImagen()
        }
    }

    private fun button_download() {

    }

    private fun seleccionarImagen() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, EditarProducto.IMAGE_REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EditarPerfil.IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            ImageUri = data?.data!!
            imageView6.setImageURI(ImageUri)
        }
    }

}