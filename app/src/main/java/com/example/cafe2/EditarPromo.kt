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
import kotlinx.android.synthetic.main.activity_editar_producto.nombreProducto5
import kotlinx.android.synthetic.main.activity_editar_promo.btnCancelar4
import kotlinx.android.synthetic.main.activity_editar_promo.btnRegresar4
import kotlinx.android.synthetic.main.activity_editar_promo.btnSave6
import kotlinx.android.synthetic.main.activity_editar_promo.button3
import kotlinx.android.synthetic.main.activity_editar_promo.descripcionProducto3
import kotlinx.android.synthetic.main.activity_editar_promo.imageView7
import kotlinx.android.synthetic.main.activity_editar_promo.nombreProducto3
import kotlinx.android.synthetic.main.activity_editar_promo.precioProducto3
import java.io.File

class EditarPromo : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private var ImageUri: Uri? = null
    private var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_promo)

        val promocion = intent.getParcelableExtra<menuModel>("Promocion")
        val email = intent.extras?.getString("email")

        if (promocion!=null){
            nombreProducto3.setText(promocion.Nombre)
            precioProducto3.setText(promocion.Precio)
            descripcionProducto3.setText(promocion.Descripcion)
        }

        val storageRef = FirebaseStorage.getInstance().reference.child("images/${promocion?.Nombre}.jpg")
        val localfile = File.createTempFile("tempImage","jpg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            imageView7.setImageBitmap(bitmap)
        }.addOnFailureListener{
            Toast.makeText(this,"Failed to download the image", Toast.LENGTH_LONG).show()

        }

        //boton de la flechita
        btnRegresar4.setOnClickListener {
            onBackPressed()
        }

        button3.setOnClickListener {
            seleccionarImagen()
        }

        //GUARDAR CAMBIOS
        btnSave6.setOnClickListener {
            val a = db.collection("Promociones").document(nombreProducto3.text.toString())
            //a.update("Nombre", nombreProducto3.text.toString())
            a.update("Precio", precioProducto3.text.toString())
            a.update("Descripcion", descripcionProducto3.text.toString())


            val intent = Intent(this, OperacionExitosa::class.java).apply {
                putExtra("Mensaje","Promocion actualizada.")
                putExtra("email",email)
            }

            subirImagen()

            startActivity(intent)
        }

        //ELIMINAR PROMOCION
        btnCancelar4.setOnClickListener {
            val intent = Intent(this,PreguntaConfirmacion::class.java)
            intent.putExtra("Mensaje", "¿ELIMINAR PROMOCION?")
            intent.putExtra("Nombre", promocion?.Nombre)
            intent.putExtra("email",email)
            startActivity(intent)
        }
    }

    private fun subirImagen() {

        if (ImageUri != null) {
            val fileName =  nombreProducto3.text.toString() + ".jpg"

            val refStorage = FirebaseStorage.getInstance().reference.child("images/$fileName")

            refStorage.putFile(ImageUri!!)
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
        startActivityForResult(intent, EditarProducto.IMAGE_REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EditarProducto.IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            ImageUri = data?.data!!
            //se modifica
            imageView7.setImageURI(ImageUri)
        }
}
}