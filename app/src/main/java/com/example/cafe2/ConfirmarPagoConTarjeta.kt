package com.example.cafe2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_confirmar_pago_con_tarjeta.button2
import kotlinx.android.synthetic.main.activity_confirmar_pago_con_tarjeta.button5

class ConfirmarPagoConTarjeta : AppCompatActivity() {

    private lateinit var  userArrayList:ArrayList<model_Carrito>
    private lateinit var  db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_pago_con_tarjeta)

        userArrayList = arrayListOf()

        EventChangeListener()

        button2.setOnClickListener {
            onBackPressed()
        }

        button5.setOnClickListener {
            escribirBorrar()
        }
    }

    private fun escribirBorrar(){

        intent.extras
        val bundle = intent.extras
        val email:String? = bundle?.getString("email")
        val fecha:String? = bundle?.getString("fecha")
        val hora:String? = bundle?.getString("hora")

        val citiesRef = db.collection("Pedidos")
        var numero = 2



            for (game in userArrayList) {
                val NombreProducto = game.NombreProducto
                val Cantidad = game.Cantidad
                val Comentarios = game.Comentarios
                val Descripcion = game.Descripcion
                val Precio = game.Precio




                //GENERA EL PEDIDO
                db.collection("Pedidos/${numero.toString()}/Productos").document(NombreProducto.toString()).set(
                    hashMapOf(  "email" to email,
                        "fecha" to fecha,
                        "estado" to "pendiente",
                        "Cantidad" to Cantidad,
                        "Comentarios" to Comentarios,
                        "Descripcion" to Descripcion,
                        "NombreProducto" to NombreProducto,
                        "Precio" to Precio
                    )
                ).addOnCompleteListener{if (it.isSuccessful){
                    Toast.makeText(this,"Pedido realizado.", Toast.LENGTH_LONG).show()
                    val menuIntent = Intent(this, inicio_clientes::class.java).apply {
                        putExtra("email",email)
                    }
                    startActivity(menuIntent)
                    finish()
                }

                }

                //elimina los productos del carrito
                db.collection("Carrito/${email}/Productos").document(NombreProducto.toString())
                    .delete()
                    .addOnSuccessListener {
                        Toast.makeText(this,"Eliminado del carrito.", Toast.LENGTH_LONG).show()
                    }

            }

    }

    private fun EventChangeListener() {
        intent.extras
        val bundle = intent.extras
        val email:String? = bundle?.getString("email")

        db = FirebaseFirestore.getInstance()
        db.collection("Carrito/${email}/Productos").addSnapshotListener(object :
            EventListener<QuerySnapshot> {
            override fun onEvent(
                value: QuerySnapshot?,
                error: FirebaseFirestoreException?
            ) {
                if(error!=null){
                    Log.e("Firestore Error", error.message.toString())
                    return
                }
                for(dc: DocumentChange in value?.documentChanges!!){
                    if(dc.type == DocumentChange.Type.ADDED){
                        userArrayList.add(dc.document.toObject(model_Carrito::class.java))


                    }
                }
            }
        })
    }
}