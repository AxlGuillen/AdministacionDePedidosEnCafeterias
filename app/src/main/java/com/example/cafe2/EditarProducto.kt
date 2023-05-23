package com.example.cafe2


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_editar_producto.btnCancelar
import kotlinx.android.synthetic.main.activity_editar_producto.btnRegresar5
import kotlinx.android.synthetic.main.activity_editar_producto.btnSave5
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_editar_producto.descripcionProducto5
import kotlinx.android.synthetic.main.activity_editar_producto.nombreProducto5
import kotlinx.android.synthetic.main.activity_editar_producto.precioProducto5
import kotlinx.android.synthetic.main.activity_editar_producto.button14
import kotlinx.android.synthetic.main.activity_editar_producto.imageView9
import java.io.File

class EditarProducto : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    var firebaseStorage:FirebaseStorage? =  null
    private var ImageUri: Uri? = null
    private var imageUrl: String? = null



    companion object {
        val IMAGE_REQUEST_CODE = 1_000;
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_producto)

        val producto = intent.getParcelableExtra<menuModel>("Producto")
        val email = intent.extras?.getString("email")



        if (producto != null){
            nombreProducto5.setText(producto.Nombre)
            precioProducto5.setText(producto.Precio)
            descripcionProducto5.setText(producto.Descripcion)
        }


        val storageRef = FirebaseStorage.getInstance().reference.child("images/${producto?.Nombre}.jpg")
        val localfile = File.createTempFile("tempImage","jpg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            imageView9.setImageBitmap(bitmap)
        }.addOnFailureListener{
            Toast.makeText(this,"Failed to download the image", Toast.LENGTH_LONG).show()

        }

        //boton de la flechita
        btnRegresar5.setOnClickListener {
            onBackPressed()
        }

        //boton de eliminar
        btnCancelar.setOnClickListener {
            val intent = Intent(this,PreguntaConfirmacion::class.java)
            intent.putExtra("Mensaje", "¿ELIMINAR PRODUCTO?")
            intent.putExtra("Nombre", producto?.Nombre)
            intent.putExtra("email",email)
            startActivity(intent)
        }

        //Guarda los cambios
        btnSave5.setOnClickListener {

            val a = db.collection("Productos").document(nombreProducto5.text.toString())
            //a.update("Nombre", nombreProducto5.text.toString())
            a.update("Precio", precioProducto5.text.toString())
            a.update("Descripcion", descripcionProducto5.text.toString())


            val OperacionExitosaIntent = Intent(this, OperacionExitosa::class.java).apply {
                putExtra("Mensaje","Producto actualizado.")
                putExtra("email",email)
            }

            subirImagen()

            startActivity(OperacionExitosaIntent)

        }

        button14.setOnClickListener{
                seleccionarImagen()
            }
    }

        //

    //}

    private fun subirImagen() {

        if (ImageUri != null) {
            val fileName = nombreProducto5.text.toString() + ".jpg"

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
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            ImageUri = data?.data!!
            //se modifica
            imageView9.setImageURI(ImageUri)
        }
    }


    



}