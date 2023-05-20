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
import kotlinx.android.synthetic.main.activity_menu_admin.btnAgregarProducto

class promos_admin : AppCompatActivity() {
    private lateinit var  recyclerView: RecyclerView
    private lateinit var  userArrayList:ArrayList<menuModel>
    private lateinit var  myAdapter: MyAdapter
    private lateinit var  db : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_menu_admin)

        recyclerView = findViewById(R.id.clientesRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()

        myAdapter = MyAdapter(userArrayList)

        recyclerView.adapter = myAdapter

        EventChangeListener()

        btnAgregarProducto.setOnClickListener {
            val menuIntent = Intent(this, NuevaPromo::class.java)
            startActivity(menuIntent)
        }

    }

    private fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("Promociones").addSnapshotListener(object : EventListener<QuerySnapshot> {
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
                        userArrayList.add(dc.document.toObject(menuModel::class.java))

                    }
                }
                myAdapter.notifyDataSetChanged()
            }
        })
    }
}