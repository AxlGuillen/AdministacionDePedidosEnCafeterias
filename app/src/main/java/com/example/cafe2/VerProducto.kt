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
import kotlinx.android.synthetic.main.activity_editar_promo.imageView7
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
import android.view.View;
import androidx.core.util.PatternsCompat
import kotlinx.android.synthetic.main.activity_registrar.edadRegistrarRegistrarCajero
import kotlinx.android.synthetic.main.activity_registrar.emailRegistrarRegistrarCajero
import kotlinx.android.synthetic.main.activity_registrar.nombreRegistrarRegistrarCajero
import kotlinx.android.synthetic.main.activity_registrar.passRegistrarRegistrarCajero
import kotlinx.android.synthetic.main.activity_registrar.telefonoRegistrarRegistrarCajero
import kotlinx.android.synthetic.main.activity_ver_producto.textView16

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
        val promocion = intent.getParcelableExtra<menuModel>("Promocion")
        val email = intent.extras?.getString("email")
        val pre = numberCantidad.text.toString()
        val comentarios = textComen.text


        if (producto != null){
            viewName.setText(producto.Nombre)
            viewPrecio.setText("$" + producto.Precio + ".00")
            viewDescripcion.setText(producto.Descripcion)

            val storageRef = FirebaseStorage.getInstance().reference.child("images/${producto?.Nombre}.jpg")
            val localfile = File.createTempFile("tempImage","jpg")
            storageRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                imageProduc.setImageBitmap(bitmap)
            }.addOnFailureListener{
                Toast.makeText(this,"Failed to download the image", Toast.LENGTH_LONG).show()

            }
        } else if(promocion != null){

            viewName.setText(promocion.Nombre)
            viewPrecio.setText("$" + promocion.Precio + ".00")
            viewDescripcion.setText(promocion.Descripcion)
            val storageRef = FirebaseStorage.getInstance().reference.child("images/${promocion?.Nombre}.jpg")
            val localfile = File.createTempFile("tempImage","jpg")
            storageRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                imageProduc.setImageBitmap(bitmap)
            }.addOnFailureListener{
                Toast.makeText(this,"Failed to download the image", Toast.LENGTH_LONG).show()

            }
        }


        //boton de la flechita
        btnRegresar7.setOnClickListener {
            onBackPressed()
        }

        //Añade al carrito
        btnAdd.setOnClickListener {

            val NombreProducto = viewName.text.toString()
            val Precio = viewPrecio.text.toString()
            val Descripcion = viewDescripcion.text.toString()
            val Comentarios = textComen.text.toString()
            val Cantidad = numberCantidad.text.toString()

            if (producto != null && validarDatos()){
                db.collection("Carrito/${email}/Productos").document(NombreProducto).set(
                    hashMapOf(  "NombreProducto" to NombreProducto,
                                "Precio" to Precio,
                                "Descripcion" to Descripcion,
                                "Comentarios" to Comentarios,
                                "Cantidad" to Cantidad
                    )
                ).addOnCompleteListener{if (it.isSuccessful){
                    Toast.makeText(this,"Añadido exitosamente al carrito.", Toast.LENGTH_LONG).show()

                    val intent = Intent(this, CarritoCompras::class.java).apply {
                        putExtra("email",email)
                    }


                    startActivity(intent)
                }
                }
            }

            if (promocion != null && validarDatos()){
                db.collection("Carrito/${email}/Productos").document(NombreProducto).set(
                    hashMapOf(  "NombreProducto" to NombreProducto,
                        "Precio" to Precio,
                        "Descripcion" to Descripcion,
                        "Comentarios" to Comentarios,
                        "Cantidad" to Cantidad
                    )
                ).addOnCompleteListener{if (it.isSuccessful){
                    Toast.makeText(this,"Añadido exitosamente al carrito.", Toast.LENGTH_LONG).show()

                    val intent = Intent(this, CarritoCompras::class.java).apply {
                        putExtra("email",email)
                    }

                    startActivity(intent)
                }
                }
            }
        }
    }
    private fun validarDatos(): Boolean {
        //&& passRegistrar.text.isNotEmpty()
        var isValid = true

        //se valida que la contraseña no este vacia
        if(numberCantidad.text.isEmpty()){
            isValid = false;
            numberCantidad.error = "Por favor indica la cantidad"

        } else numberCantidad.error = null

        // este es el valor que indica si los datos fueron validados o no
        return isValid
    }

}