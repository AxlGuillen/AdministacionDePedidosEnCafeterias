package com.example.cafe2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PatternMatcher
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.core.util.PatternsCompat
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_confirmar_pago_con_tarjeta.button2
import kotlinx.android.synthetic.main.activity_confirmar_pago_con_tarjeta.button5
import kotlinx.android.synthetic.main.activity_confirmar_pago_con_tarjeta.editTextNumber
import kotlinx.android.synthetic.main.activity_confirmar_pago_con_tarjeta.editTextNumber3
import kotlinx.android.synthetic.main.activity_confirmar_pago_con_tarjeta.editTextNumber4
import kotlinx.android.synthetic.main.activity_confirmar_pago_con_tarjeta.editTextNumber5
import kotlinx.android.synthetic.main.activity_confirmar_pago_con_tarjeta.editTextText3
import java.util.regex.Pattern

class ConfirmarPagoConTarjeta : AppCompatActivity() {

    private lateinit var  userArrayList:ArrayList<model_Carrito>
    private lateinit var  db : FirebaseFirestore
    var numero = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_pago_con_tarjeta)

        userArrayList = arrayListOf()

        EventChangeListener()

        button2.setOnClickListener {
            onBackPressed()
        }

        button5.setOnClickListener {

            validar()

        }
    }











    private  fun primary(){

        db.collection("Pedidos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("Pruebaaaaaaaaaaa", "${document.id} => ${document.data}")
                    numero = document.id
                }
                numero = (numero.toInt()+1).toString()
                Log.d("PRUEBAAAAAAAAAAAAAAAA", "ESTE ES EL NUMERO $numero ")
                escribirBorrar()
            }
            .addOnFailureListener { exception ->
                Log.d("Pruebaaaaaaaaaaa", "Error getting documents: ", exception)
            }
    }

    private fun escribirBorrar(){

        intent.extras
        val bundle = intent.extras
        val email:String? = bundle?.getString("email")
        val fecha:String? = bundle?.getString("fecha")
        val hora:String? = bundle?.getString("hora")


            for (game in userArrayList) {
                val NombreProducto = game.NombreProducto
                val Cantidad = game.Cantidad
                val Comentarios = game.Comentarios
                val Descripcion = game.Descripcion
                val Precio = game.Precio


                //GENERA EL PEDIDO
                db.collection("Pedidos").document(numero.toString()).set(
                    hashMapOf(  "email" to email,
                                "estado" to "pendiente",
                                "fecha" to fecha,
                                "numero" to numero,
                                "status" to "procesando"
                    )
                )

                db.collection("Pedidos/${numero}/Productos").document(NombreProducto.toString()).set(
                    hashMapOf(  "email" to email,
                        "fecha" to fecha,
                        "hora" to hora,
                        "estado" to "pendiente",
                        "Cantidad" to Cantidad,
                        "Comentarios" to Comentarios,
                        "Descripcion" to Descripcion,
                        "NombreProducto" to NombreProducto,
                        "Precio" to Precio,
                        "idPedido" to numero
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

    private fun validar(){
        val result = arrayOf(validarTarjetaCredito(),validarNombre(),validarMes(),validarAño(),validarDigitos())

        if(false in result){
            return
        }
        primary()
        Toast.makeText(this,"Exitoso",Toast.LENGTH_SHORT).show()
    }

    private fun validarAño(): Boolean {
        val año = editTextNumber4?.text.toString()
        val tarjetaRegex = Pattern.compile(
            "[0-9]{4}$"//VALIDA QUE SEA DE 3 DIGITOS
        )
        return if(año.isEmpty()){
            editTextNumber4.error = "Llene el campo por favor"
            false
        } else if(!tarjetaRegex.matcher(año).matches()){
            editTextNumber4.error = "Tiene que ser 4 digitos"
            false
        }else{
            editTextNumber4.error = null
            true
        }
    }

    private fun validarMes(): Boolean {
        val mes = editTextNumber3?.text.toString()
        val tarjetaRegex = Pattern.compile(
            "[0-9]{2}$"//VALIDA QUE SEA DE 3 DIGITOS
        )
        return if(mes.isEmpty()){
            editTextNumber3.error = "Llene el campo por favor"
            false
        } else if(!tarjetaRegex.matcher(mes).matches()){
            editTextNumber3.error = "Tiene que ser dos digitos"
            false
        }else{
            editTextNumber3.error = null
            true
        }
    }

    private fun validarNombre(): Boolean {

        return if(editTextText3.text.toString().isEmpty()){
            editTextText3.error = "Llene el campo por favor"
            false
        } else{
            editTextText3.error = null
            true
        }
    }

    private fun validarDigitos(): Boolean {
        val digitos = editTextNumber5?.text.toString()
        val tarjetaRegex = Pattern.compile(
            "[0-9]{2}$"//VALIDA QUE SEA DE 3 DIGITOS
        )
        return if(digitos.isEmpty()){
            editTextNumber5.error = "Llene el campo por favor"
            false
        } else if(!tarjetaRegex.matcher(digitos).matches()){
            editTextNumber5.error = "Tienen que ser 2 digitos"
            false
        }else{
            editTextNumber5.error = null
            true
        }
    }


    private fun validarTarjetaCredito() : Boolean{
        val tarjetaC = editTextNumber?.text.toString()
        val tarjetaRegex = Pattern.compile(
            //"5[1-5][0-9]{14}$"+//5555555555554444 TARJETAS MASTERCARD
                    //"^3[47][0-9]{13}\$"+//4222222222222 TARJETAS VISA
                    "[0-9]{16}"//+
                    //"(?=\\S+$)"
        )
        return if(tarjetaC.isEmpty()){
            editTextNumber.error = "Llene el campo por favor"
            false
        } else if(!tarjetaRegex.matcher(tarjetaC).matches()){
            editTextNumber.error = "No es una tarjeta valida"
            false
        }else{
            editTextNumber.error = null
            true
        }
    }
}