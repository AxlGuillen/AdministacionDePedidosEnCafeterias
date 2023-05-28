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
import kotlinx.android.synthetic.main.activity_promos_clientes.imgbtnCarrito2
import kotlinx.android.synthetic.main.activity_promos_clientes.imgbtnFlecha2
import kotlinx.android.synthetic.main.activity_promos_clientes.imgbtnHome
import kotlinx.android.synthetic.main.activity_promos_clientes.imgbtnNotificaciones3
import kotlinx.android.synthetic.main.activity_promos_clientes.imgbtnPerfil3
import kotlinx.android.synthetic.main.activity_promos_clientes.imgbtnhistorial

class promos_clientes : AppCompatActivity() {
    private lateinit var  recyclerView: RecyclerView
    private lateinit var  userArrayList:ArrayList<menuModel>
    private lateinit var  myAdapter: MyAdapter
    private lateinit var  db : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promos_clientes)

        //sacamos el email para mandarlo a otro lados
        intent.extras
        val bundle = intent.extras
        val email:String? = bundle?.getString("email")

        recyclerView = findViewById(R.id.promosClientesRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()

        myAdapter = MyAdapter(userArrayList)

        recyclerView.adapter = myAdapter

        EventChangeListener()

        //3 se configura
        myAdapter.onItemClick = {
            val intent = Intent(this,VerProducto::class.java)
            intent.putExtra("Promocion", it)
            intent.putExtra("email",email)
            startActivity(intent)
        }

        //boton de la flecha
        imgbtnFlecha2.setOnClickListener {
            onBackPressed()
        }

        //NOTIFICACIONES
        imgbtnNotificaciones3.setOnClickListener {
            val Intent = Intent(this, Notificaciones::class.java).apply {
                putExtra("email",email)
            }
            startActivity(Intent)
        }

        //boton de perfil
        imgbtnPerfil3.setOnClickListener {
            val perfilIntent = Intent(this, Perfil::class.java).apply {
                putExtra("email",email)
            }
            startActivity(perfilIntent)
        }

        //HISTORIAL
        imgbtnhistorial.setOnClickListener {
            val intent = Intent(this, pedidos_activos_cliente::class.java).apply {
                putExtra("email",email)
            }
            startActivity(intent)
        }

        //HOME
        imgbtnHome.setOnClickListener {
            val menuIntent = Intent(this, inicio_clientes::class.java).apply {
                putExtra("email",email)
            }
            startActivity(menuIntent)
            finish()
        }

        //CARRITO
        imgbtnCarrito2.setOnClickListener{
            val menuUsuariosIntent = Intent(this, CarritoCompras::class.java).apply {
                putExtra("email",email)
            }
            startActivity(menuUsuariosIntent)
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