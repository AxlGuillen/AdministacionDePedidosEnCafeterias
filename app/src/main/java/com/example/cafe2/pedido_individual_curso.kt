package com.example.cafe2

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
import kotlinx.android.synthetic.main.activity_pedido_individual_curso.Cancelar
import kotlinx.android.synthetic.main.activity_pedido_individual_curso.Entregar
import kotlinx.android.synthetic.main.activity_pedido_individual_curso.view_id_pedido
import kotlinx.android.synthetic.main.activity_pedido_individual_curso.view_status

class pedido_individual_curso : AppCompatActivity() {

    private lateinit var  recyclerView: RecyclerView
    private lateinit var  userArrayList:ArrayList<model_historial>
    private lateinit var  adapter_pedido_individual: adapter_pedido_individual
    private lateinit var  db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido_individual_curso)

        val pedido = intent.getParcelableExtra<model_pedidos_activos_cliente>("Pedido")
        val etiqueta = intent.extras?.getString("Etiqueta")

        if (etiqueta.toString().equals("Aceptar/Cancelar")){
            Entregar.text = "Aceptar"
        }

        view_status.text = pedido?.email.toString()
        view_id_pedido.text = pedido?.numero.toString()

        recyclerView = findViewById(R.id.pedido_ind_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()

        adapter_pedido_individual = adapter_pedido_individual(userArrayList)

        recyclerView.adapter = adapter_pedido_individual

        EventChangeListener()

        Entregar.setOnClickListener {
            if (etiqueta.toString().equals("Aceptar/Cancelar")){
                val a = db.collection("Pedidos").document(pedido?.numero.toString())
                a.update("status","aceptado")
                //notiicacion de que su pedido fue aceptado
            }
            else{
                val a = db.collection("Pedidos").document(pedido?.numero.toString())
                a.update("estado","entregado")
                //encuesta del producto a la persona
            }
        }

        Cancelar.setOnClickListener {
            val a = db.collection("Pedidos").document(pedido?.numero.toString())
            a.update("status","Cancelado")
            //notiicacion de que su pedido fue CANCELADO
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
                adapter_pedido_individual.notifyDataSetChanged()
            }
        })




    }

}