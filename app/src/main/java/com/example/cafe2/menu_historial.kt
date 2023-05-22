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

class menu_historial : AppCompatActivity() {

    private lateinit var  recyclerView: RecyclerView
    private lateinit var  userArrayList:ArrayList<model_historial>
    private lateinit var  adapter_historial: adapter_historial
    private lateinit var  db : FirebaseFirestore



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

        EventChangeListener()
    }



    private fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        //Le puse de nombre a la Coleccion "Ventas", porque aun no existe
        db.collection("Ventas").addSnapshotListener(object : EventListener<QuerySnapshot> {
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
}