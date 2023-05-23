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
import kotlinx.android.synthetic.main.activity_menu_historial.btnRegresar6
import kotlinx.android.synthetic.main.activity_menu_historial.imgbtnHistorial2
import kotlinx.android.synthetic.main.activity_menu_historial.imgbtnPromociones
import kotlinx.android.synthetic.main.activity_menu_historial.imgbtnUsuarios2
import kotlinx.android.synthetic.main.activity_menu_historial.imgbtnmenu2

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

        //FLECHITA
        btnRegresar6.setOnClickListener {
            onBackPressed()
        }

        //Historial
        imgbtnHistorial2.setOnClickListener {
            val intent = Intent(this, menu_historial::class.java).apply {
                putExtra("email",email)
            }
            startActivity(intent)
        }

        //MENU
        imgbtnmenu2.setOnClickListener {
            val menuIntent = Intent(this, menu_admin::class.java).apply {
                putExtra("email",email)
            }
            startActivity(menuIntent)
        }

        //Usuarios
        imgbtnUsuarios2.setOnClickListener {
            val menuUsuariosIntent = Intent(this, MenuUsuarios::class.java).apply {
                putExtra("email",email)
            }
            startActivity(menuUsuariosIntent)
        }

        //Promociones
        imgbtnPromociones.setOnClickListener {
            val notificacionIntent = Intent(this, promos_admin::class.java).apply {
                putExtra("email",email)
            }
            startActivity(notificacionIntent)
        }
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