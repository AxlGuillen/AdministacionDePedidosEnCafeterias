package com.example.cafe2

import android.app.ProgressDialog
import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_nuevo_producto.btnSave
import kotlinx.android.synthetic.main.activity_nuevo_producto.descripcionProducto
import kotlinx.android.synthetic.main.activity_nuevo_producto.nombreProducto
import kotlinx.android.synthetic.main.activity_nuevo_producto.precioProducto
import kotlinx.android.synthetic.main.activity_nuevo_producto.seleccionar_foto
import kotlinx.android.synthetic.main.activity_nuevo_producto.imageView3
import java.net.URI
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class EditarProducto : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    var firebaseStorage:FirebaseStorage? =  null
    lateinit var ImageUri: Uri



    companion object {
        val IMAGE_REQUEST_CODE = 1_000;
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_producto)

        btnSave.setOnClickListener {
            AgregarProducto()
            subirImagen()
            //val homeIntent = Intent(this, OperacionExitosa::class.java)
            //startActivity(homeIntent)
        }
        seleccionar_foto.setOnClickListener{
            seleccionarImagen()
        }

    }

    private fun subirImagen() {

        if (ImageUri != null) {
            val fileName = nombreProducto.toString() + ".jpg"

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




    }

    private fun seleccionarImagen() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            ImageUri = data?.data!!
            imageView3.setImageURI(ImageUri)
        }
    }


    private fun AgregarProducto(){
        title = "Editar producto"

        //Declaramos la variable de nombre, precio, descripcion y falta imagen
        val nombre = nombreProducto.text
        val precio = precioProducto.text
        val descripcion = descripcionProducto.text

        //Agregamos los datos a la coleccion productos en la base de datos nombres es la primary key, los otros campos son precio estado y descripcion
        db.collection("Productos").document(nombre.toString()).set(
            hashMapOf("Precio" to precio.toString(),
                "Estado" to true,
                "Descripcion" to descripcion.toString()
            )
            )

        }


}