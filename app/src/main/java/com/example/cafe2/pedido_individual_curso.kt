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

class pedido_individual_curso : AppCompatActivity() {

    private lateinit var  recyclerView: RecyclerView
    private lateinit var  userArrayList:ArrayList<model_pedido_ind>
    private lateinit var  adapter_pedido_individual: adapter_pedido_individual
    private lateinit var  db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido_individual_curso)

        recyclerView = findViewById(R.id.pedido_ind_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()

        adapter_pedido_individual = adapter_pedido_individual(userArrayList)

        recyclerView.adapter = adapter_pedido_individual

        EventChangeListener()
    }

    private fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        //Le puse de nombre a la Coleccion "Pedidos_curso", porque aun no existe
        db.collection("Pedidos_curso").addSnapshotListener(object : EventListener<QuerySnapshot> {
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
                        userArrayList.add(dc.document.toObject(model_pedido_ind::class.java))

                    }
                }
                adapter_pedido_individual.notifyDataSetChanged()
            }
        })
    }

}