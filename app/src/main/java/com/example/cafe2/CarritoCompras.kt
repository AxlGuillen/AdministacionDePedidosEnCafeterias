package com.example.cafe2

import android.annotation.SuppressLint
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
import kotlinx.android.synthetic.main.activity_carrito_compras.imgbtnFlecha4
import kotlinx.android.synthetic.main.activity_carrito_compras.imgbtnMenuAdm4
import kotlinx.android.synthetic.main.activity_carrito_compras.imgbtnNotificaciones4
import kotlinx.android.synthetic.main.activity_carrito_compras.imgbtnPerfil4
import kotlinx.android.synthetic.main.activity_carrito_compras.imgbtnpromos4
import kotlinx.android.synthetic.main.activity_menu_admin.btnAgregarProducto
import kotlinx.android.synthetic.main.activity_menu_admin.imgbtnFlecha
import kotlinx.android.synthetic.main.activity_menu_admin.imgbtnHistorial
import kotlinx.android.synthetic.main.activity_menu_admin.imgbtnMenuAdm
import kotlinx.android.synthetic.main.activity_menu_admin.imgbtnNotificaciones
import kotlinx.android.synthetic.main.activity_menu_admin.imgbtnPerfil
import kotlinx.android.synthetic.main.activity_menu_admin.imgbtnpromos
import kotlinx.android.synthetic.main.activity_menu_admin.imgbtnusuarios
import kotlinx.android.synthetic.main.activity_ver_producto.numberCantidad

class CarritoCompras : AppCompatActivity() {
    private lateinit var  recyclerView: RecyclerView
    private lateinit var  userArrayList:ArrayList<menuModel>
    private lateinit var  myAdapter: MyAdapter
    private lateinit var  db : FirebaseFirestore
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito_compras)

        //sacamos el email para mandarlo a otro lados
        intent.extras
        val bundle = intent.extras
        val email:String? = bundle?.getString("email")
        val producto:String? = bundle?.getString("producto")
        val precio:String? = bundle?.getString("precio")
        val comentarios:String? = bundle?.getString("comentarios")
        val cantidad:String? = bundle?.getString("cantidad")

        recyclerView = findViewById(R.id.carritoRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)


        userArrayList = arrayListOf()

        myAdapter = MyAdapter(userArrayList)

        recyclerView.adapter = myAdapter

        EventChangeListener()

        myAdapter.onItemClick = {
            val notificacionIntent = Intent(this, EditarMiPedido::class.java).apply {
                putExtra("email", email)
                putExtra("producto", producto)
                putExtra("precio", precio)
                putExtra("comentarios", comentarios)
                putExtra("cantidad", cantidad)
            }
            startActivity(notificacionIntent)
        }


        //boton de la flecha
        imgbtnFlecha4.setOnClickListener {
            onBackPressed()
        }

        //NOTIFICACIONES
        imgbtnNotificaciones4.setOnClickListener {

        }

        //boton de perfil
        imgbtnPerfil4.setOnClickListener {
            val perfilIntent = Intent(this, Perfil::class.java).apply {
                putExtra("email",email)
            }
            startActivity(perfilIntent)
        }


        //MENU ESTAMOS AQUI
        imgbtnMenuAdm4.setOnClickListener {
            val menuIntent = Intent(this, menu_admin::class.java).apply {
                putExtra("email",email)
            }
            startActivity(menuIntent)
        }


        //PROMOCIONES
        imgbtnpromos4.setOnClickListener {
            val notificacionIntent = Intent(this, promos_admin::class.java).apply {
                putExtra("email",email)
            }
            startActivity(notificacionIntent)
        }

    }


    private fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("Productos").addSnapshotListener(object : EventListener<QuerySnapshot> {
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