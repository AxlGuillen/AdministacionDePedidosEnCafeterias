package com.example.cafe2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.activity_auth.emailLogin
import kotlinx.android.synthetic.main.activity_menu_historial.btnRegresar6

class menu_historial : AppCompatActivity() {

    private lateinit var  recyclerView: RecyclerView
    //productos de la venta tiene que cambiar a otro modelo
    private lateinit var  userArrayList:ArrayList<model_historial>


    private lateinit var  adapter_historial: adapter_historial
    private lateinit var  db : FirebaseFirestore
    val listNum = arrayListOf<String>()
    var Rol = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_historial)

        //sacamos el email para mandarlo a otro lados
        intent.extras
        val bundle = intent.extras
        val email: String? = bundle?.getString("email")


        recyclerView = findViewById(R.id.notiRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()

        adapter_historial = adapter_historial(userArrayList)

        recyclerView.adapter = adapter_historial


        db = FirebaseFirestore.getInstance()
        obtenerRol()




        //FLECHITA
        btnRegresar6.setOnClickListener {
            onBackPressed()
        }


    }



    private fun EventChangeListener() {

            val pedido = intent.getParcelableExtra<model_pedidos_activos_cliente>("Pedido")
            db = FirebaseFirestore.getInstance()
            //Le puse de nombre a la Coleccion "Ventas", porque aun no existe MODIFICARLO



            db.collection("Pedidos/${pedido?.numero.toString()}/Productos").addSnapshotListener(object : EventListener<QuerySnapshot> {
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

                                userArrayList.add(dc.document.toObject(model_historial::class.java))

                        }
                    }
                    adapter_historial.notifyDataSetChanged()
                }
            })




    }



    private  fun  obtenerRol(){
        intent.extras
        val bundle = intent.extras
        val email: String? = bundle?.getString("email")

       db.collection("Usuarios").document(email.toString()).get().addOnSuccessListener {
            Rol = (it.get("Rol") as String?).toString()

           EventChangeListener()

       }
    }
}