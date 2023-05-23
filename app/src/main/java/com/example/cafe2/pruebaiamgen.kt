package com.example.cafe2

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_imagen_prueba.BotonCargarImagen
import kotlinx.android.synthetic.main.activity_imagen_prueba.imageView10
import java.io.File

class pruebaiamgen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imagen_prueba)
        BotonCargarImagen.setOnClickListener{
            button_download()

        }


    }

    private fun button_download() {
    val storageRef = FirebaseStorage.getInstance().reference.child("images/e57ce305-f278-41bd-ba02-d4bc4aefb624.jpg")
        val localfile = File.createTempFile("tempImage","jpg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            imageView10.setImageBitmap(bitmap)
        }.addOnFailureListener{
            Toast.makeText(this,"Failed to download the image",Toast.LENGTH_LONG).show()

        }


    }
}