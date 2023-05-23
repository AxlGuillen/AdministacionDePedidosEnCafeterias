package com.example.cafe2

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_editar_producto.btnCancelar
import kotlinx.android.synthetic.main.activity_editar_producto.btnRegresar5
import kotlinx.android.synthetic.main.activity_editar_producto.btnSave5
import kotlinx.android.synthetic.main.activity_editar_producto.button14
import kotlinx.android.synthetic.main.activity_editar_producto.descripcionProducto5
import kotlinx.android.synthetic.main.activity_editar_producto.imageView9
import kotlinx.android.synthetic.main.activity_editar_producto.nombreProducto5
import kotlinx.android.synthetic.main.activity_editar_producto.precioProducto5
import kotlinx.android.synthetic.main.activity_ver_producto.btnAdd
import kotlinx.android.synthetic.main.activity_ver_producto.btnRegresar7
import kotlinx.android.synthetic.main.activity_ver_producto.imageProduc
import kotlinx.android.synthetic.main.activity_ver_producto.numberCantidad
import kotlinx.android.synthetic.main.activity_ver_producto.textComen
import kotlinx.android.synthetic.main.activity_ver_producto.viewDescripcion
import kotlinx.android.synthetic.main.activity_ver_producto.viewName
import kotlinx.android.synthetic.main.activity_ver_producto.viewPrecio
import kotlinx.android.synthetic.main.lista_noti_card.viewDesc
import java.io.File

class VerProducto : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    var firebaseStorage: FirebaseStorage? =  null
    private var ImageUri: Uri? = null
    private var imageUrl: String? = null



    companion object {
        val IMAGE_REQUEST_CODE = 1_000;
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_producto)

        val producto = intent.getParcelableExtra<menuModel>("Producto")
        val email = intent.extras?.getString("email")
        val pre = numberCantidad.text.toString()
        val comentarios = textComen.text


        if (producto != null){
            viewName.setText(producto.Nombre)
            viewPrecio.setText("$" + producto.Precio + ".00")

            viewDescripcion.setText(producto.Descripcion)
        }


        val storageRef = FirebaseStorage.getInstance().reference.child("images/${producto?.Nombre}.jpg")
        val localfile = File.createTempFile("tempImage","jpg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            imageProduc.setImageBitmap(bitmap)
        }.addOnFailureListener{
            Toast.makeText(this,"Failed to download the image", Toast.LENGTH_LONG).show()

        }

        //boton de la flechita
        btnRegresar7.setOnClickListener {
            onBackPressed()
        }

        //Añade al carrito
        btnAdd.setOnClickListener {
            //
            val notificacionIntent = Intent(this, CarritoCompras::class.java).apply {
                putExtra("email",email)
                putExtra("producto", producto)
                putExtra("precio", pre)
                putExtra("comentarios", comentarios)
                putExtra("cantidad", numberCantidad.text)
            }
            startActivity(notificacionIntent)
        }
    }
}