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
import kotlinx.android.synthetic.main.activity_banear_activar.estado2
import kotlinx.android.synthetic.main.activity_notificaciones.btnRegresar6

class Notificaciones : AppCompatActivity() {

    private lateinit var  recyclerView: RecyclerView
    private lateinit var  userArrayList:ArrayList<notiModel>
    private lateinit var  myAdapter: notiAdapter
    private lateinit var  db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notificaciones)

        recyclerView = findViewById(R.id.notiRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)


        userArrayList = arrayListOf()

        myAdapter = notiAdapter(userArrayList)

        recyclerView.adapter = myAdapter

        EventChangeListener()

        //Flechita
        btnRegresar6.setOnClickListener {
            onBackPressed()
        }
    }
    fun EventChangeListener() {
        //sacamos el email para mandarlo a otro lados
        intent.extras
        val bundle = intent.extras
        val email:String? = bundle?.getString("email")

        db = FirebaseFirestore.getInstance()
        db.collection("Notificaciones").addSnapshotListener(object : EventListener<QuerySnapshot> {
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
                        if (dc.document.toObject(notiModel::class.java).Destinatario.toString().equals(email)) {
                            userArrayList.add(dc.document.toObject(notiModel::class.java))
                        }

                    }
                }
                myAdapter.notifyDataSetChanged()
            }
        })
    }
}