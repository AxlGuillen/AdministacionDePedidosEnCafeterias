package com.example.cafe2

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_editar_mi_pedido.btnCancelar2
import kotlinx.android.synthetic.main.activity_editar_mi_pedido.btnRegresar8
import kotlinx.android.synthetic.main.activity_editar_mi_pedido.btnSave7
import kotlinx.android.synthetic.main.activity_editar_mi_pedido.imageProduc2
import kotlinx.android.synthetic.main.activity_editar_mi_pedido.numberCantidad2
import kotlinx.android.synthetic.main.activity_editar_mi_pedido.textComen2
import kotlinx.android.synthetic.main.activity_editar_mi_pedido.viewDescripcion2
import kotlinx.android.synthetic.main.activity_editar_mi_pedido.viewName2
import kotlinx.android.synthetic.main.activity_editar_producto.descripcionProducto5
import kotlinx.android.synthetic.main.activity_editar_producto.nombreProducto5
import kotlinx.android.synthetic.main.activity_editar_producto.precioProducto5
import kotlinx.android.synthetic.main.activity_editar_promo.imageView7
import java.io.File

class EditarMiPedido : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_mi_pedido)

        //sacamos el email para mandarlo a otro lados
        val carrito = intent.getParcelableExtra<model_Carrito>("Carrito")
        val email = intent.extras?.getString("email")

        Log.e("Datossssss", carrito?.NombreProducto.toString() )

        if (carrito != null){
            viewName2.setText(carrito.NombreProducto)
            viewDescripcion2.setText(carrito.Descripcion)
            textComen2.setText(carrito.Comentarios)
            numberCantidad2.setText(carrito.Cantidad)
        }

        val storageRef = FirebaseStorage.getInstance().reference.child("images/${carrito?.NombreProducto}.jpg")
        val localfile = File.createTempFile("tempImage","jpg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            imageProduc2.setImageBitmap(bitmap)
        }.addOnFailureListener{
            Toast.makeText(this,"Failed to download the image", Toast.LENGTH_LONG).show()

        }

        //Flecha
        btnRegresar8.setOnClickListener {
            onBackPressed()
        }

        //ACTUALIZAR
        btnSave7.setOnClickListener {
            val a = db.collection("Carrito/${email}/Productos").document(viewName2.text.toString())
            a.update("Comentarios", textComen2.text.toString())
            a.update("Cantidad", numberCantidad2.text.toString())


            val OperacionExitosaIntent = Intent(this, OperacionExitosa::class.java).apply {
                putExtra("Mensaje","Carrito actualizado.")
                putExtra("email",email)
            }

            startActivity(OperacionExitosaIntent)
        }

        btnCancelar2.setOnClickListener {
            val intent = Intent(this,PreguntaConfirmacion::class.java)
            intent.putExtra("Mensaje", "¿ELIMINAR DEL CARRITO?")
            intent.putExtra("Nombre", carrito?.NombreProducto)
            intent.putExtra("email",email)
            startActivity(intent)
        }

    }
}