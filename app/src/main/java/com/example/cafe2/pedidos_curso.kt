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

class pedidos_curso : AppCompatActivity() {

    private lateinit var  recyclerView: RecyclerView
    private lateinit var  userArrayList:ArrayList<pedidosCursoModel>
    private lateinit var  adapterPedidosCurso: adapterPedidosCurso
    private lateinit var  db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedidos_curso)
        //sacamos el email para mandarlo a otro lados

        intent.extras
        val bundle = intent.extras
        val email: String? = bundle?.getString("email")

        recyclerView = findViewById(R.id.pedidosCursoRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()

        adapterPedidosCurso = adapterPedidosCurso(userArrayList)

        recyclerView.adapter = adapterPedidosCurso

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
                        userArrayList.add(dc.document.toObject(pedidosCursoModel::class.java))

                    }
                }
                adapterPedidosCurso.notifyDataSetChanged()
            }
        })
    }

}