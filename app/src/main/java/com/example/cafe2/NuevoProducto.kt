package com.example.cafe2

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_nuevo_producto.btnPerfil
import kotlinx.android.synthetic.main.activity_nuevo_producto.btnRegresar
import kotlinx.android.synthetic.main.activity_nuevo_producto.btnSave
import kotlinx.android.synthetic.main.activity_nuevo_producto.descripcionProducto
import kotlinx.android.synthetic.main.activity_nuevo_producto.imageView3
import kotlinx.android.synthetic.main.activity_nuevo_producto.nombreProducto
import kotlinx.android.synthetic.main.activity_nuevo_producto.precioProducto
import kotlinx.android.synthetic.main.activity_nuevo_producto.seleccionar_foto

class NuevoProducto : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private var ImageUri: Uri? = null
    private var imageUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_producto)


        //boton para guardar el nuevo producto en la base de datos
        btnSave.setOnClickListener {
            AgregarProducto()

        }

        //boton de la flechita
        btnRegresar.setOnClickListener {
            onBackPressed()
        }

        //abre el perfil
        btnPerfil.setOnClickListener {
            val perfilIntent = Intent(this, Perfil::class.java)
            startActivity(perfilIntent)
        }

        //SELECCIONA IMAGEN
        seleccionar_foto.setOnClickListener {
            seleccionarImagen()
        }

    }

    private fun AgregarProducto(){
        title = "Agregar Producto"

        //Declaramos la variable de nombre, precio, descripcion y falta imagen
        val nombre = nombreProducto.text
        val precio = precioProducto.text
        val descripcion = descripcionProducto.text

        //Agregamos los datos a la coleccion productos en la base de datos nombres es la primary key, los otros campos son precio estado y descripcion
        db.collection("Productos").document(nombre.toString()).set(
            hashMapOf("Nombre" to nombre.toString(),
                "Precio" to precio.toString(),
                "Estado" to true,
                "Descripcion" to descripcion.toString(),
                "Nombre" to nombre.toString()
            )
            ).addOnCompleteListener{if (it.isSuccessful){
                val OperacionExitosaIntent = Intent(this, OperacionExitosa::class.java).apply {
                    putExtra("Mensaje","Añadido exitosamente.")
                }

                subirImagen()

                startActivity(OperacionExitosaIntent)
            }else{
                showAlert()
            }}

        }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error al agregar el producto.")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    private fun subirImagen() {

        if (ImageUri != null) {
            val fileName = nombreProducto.text.toString() + ".jpg"

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
            imageView3.setImageURI(ImageUri)
        }
    }

}