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
import kotlinx.android.synthetic.main.activity_menu_admin.imgbtnFlecha
import kotlinx.android.synthetic.main.activity_menu_admin.imgbtnHistorial
import kotlinx.android.synthetic.main.activity_menu_admin.imgbtnMenuAdm
import kotlinx.android.synthetic.main.activity_menu_admin.imgbtnNotificaciones
import kotlinx.android.synthetic.main.activity_menu_admin.imgbtnPerfil
import kotlinx.android.synthetic.main.activity_menu_admin.imgbtnpromos
import kotlinx.android.synthetic.main.activity_menu_admin.imgbtnusuarios

class promos_admin : AppCompatActivity() {
    private lateinit var  recyclerView: RecyclerView
    private lateinit var  userArrayList:ArrayList<menuModel>
    private lateinit var  myAdapter: MyAdapter
    private lateinit var  db : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_admin)

        //sacamos el email para mandarlo a otro lados
        intent.extras
        val bundle = intent.extras
        val email:String? = bundle?.getString("email")

        recyclerView = findViewById(R.id.clientesRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()

        myAdapter = MyAdapter(userArrayList)

        recyclerView.adapter = myAdapter

        EventChangeListener()

        //3 se configura
        myAdapter.onItemClick = {
            val intent = Intent(this,EditarPromo::class.java)
            intent.putExtra("Promocion", it)
            intent.putExtra("email",email)
            startActivity(intent)
        }

        //boton de la flecha
        imgbtnFlecha.setOnClickListener {
            onBackPressed()
        }

        //NOTIFICACIONES
        imgbtnNotificaciones.setOnClickListener {
            val Intent = Intent(this, Notificaciones::class.java).apply {
                putExtra("email",email)
            }
            startActivity(Intent)
        }

        //boton de perfil
        imgbtnPerfil.setOnClickListener {
            val perfilIntent = Intent(this, Perfil::class.java).apply {
                putExtra("email",email)
            }
            startActivity(perfilIntent)
        }

        //AGREGAR PROMO
        btnAgregarProducto.setOnClickListener {
            val menuIntent = Intent(this, NuevaPromo::class.java).apply {
                putExtra("email",email)
            }
            startActivity(menuIntent)
        }

        //HISTORIAL
        imgbtnHistorial.setOnClickListener {
            val intent = Intent(this, pedidos_activos_cliente::class.java).apply {
                putExtra("email",email)
            }
            startActivity(intent)
        }

        //MENU
        imgbtnMenuAdm.setOnClickListener {
            val menuIntent = Intent(this, menu_admin::class.java).apply {
                putExtra("email",email)
            }
            startActivity(menuIntent)
        }

        //USUARIOS
        imgbtnusuarios.setOnClickListener{
            val menuUsuariosIntent = Intent(this, MenuUsuarios::class.java).apply {
                putExtra("email",email)
            }
            startActivity(menuUsuariosIntent)
        }

        //PROMOCIONES ESTAMOS AQUI
        imgbtnpromos.setOnClickListener {
            val notificacionIntent = Intent(this, promos_admin::class.java).apply {
                putExtra("email",email)
            }
            startActivity(notificacionIntent)
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