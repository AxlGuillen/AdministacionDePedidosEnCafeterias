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
import kotlinx.android.synthetic.main.activity_editar_perfil.edadRegistrar2
import kotlinx.android.synthetic.main.activity_editar_perfil.editTextNumber2
import kotlinx.android.synthetic.main.activity_editar_perfil.nombreEditText
import kotlinx.android.synthetic.main.activity_menu_clientes_admin.imgbtnHistorial2
import kotlinx.android.synthetic.main.activity_menu_clientes_admin.imgbtnNotificaciones
import kotlinx.android.synthetic.main.activity_menu_clientes_admin.imgbtnPerfil
import kotlinx.android.synthetic.main.activity_menu_clientes_admin.imgbtnPromociones
import kotlinx.android.synthetic.main.activity_menu_clientes_admin.imgbtnUsuarios2
import kotlinx.android.synthetic.main.activity_menu_clientes_admin.imgbtnmenu2
import kotlinx.android.synthetic.main.activity_menu_clientes_admin.imgbtnFlecha

class menu_clientes_admin : AppCompatActivity() {
    private lateinit var  recyclerView: RecyclerView
    private lateinit var  userArrayList:ArrayList<clienteModel>
    private lateinit var  adapterClientes: adapterClientes
    private lateinit var  db : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_clientes_admin)

        //sacamos el email para mandarlo a otro lados
        intent.extras
        val bundle = intent.extras
        val email:String? = bundle?.getString("email")

        recyclerView = findViewById(R.id.clientesRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()

        adapterClientes = adapterClientes(userArrayList)

        recyclerView.adapter = adapterClientes

        EventChangeListener()

        //3 se configura
        adapterClientes.onItemClick = {
            val intent = Intent(this,BanearActivar::class.java)
            intent.putExtra("Usuario", it)
            intent.putExtra("email",email)
            startActivity(intent)
        }

        //FLECHITA
        imgbtnFlecha.setOnClickListener {
            onBackPressed()
        }

        //NOTIFICACIONES
        imgbtnNotificaciones.setOnClickListener {

        }

        //PERFIL
        imgbtnPerfil.setOnClickListener {
            val perfilIntent = Intent(this, Perfil::class.java).apply {
                putExtra("email",email)
            }
            startActivity(perfilIntent)
        }

        //HISTORIAL
        imgbtnHistorial2.setOnClickListener {

        }

        //MENU
        imgbtnmenu2.setOnClickListener {
            val menuIntent = Intent(this, menu_admin::class.java).apply {
                putExtra("email",email)
            }
            startActivity(menuIntent)
        }

        //USUARIOS
        imgbtnUsuarios2.setOnClickListener {
            val menuUsuariosIntent = Intent(this, MenuUsuarios::class.java).apply {
                putExtra("email",email)
            }
            startActivity(menuUsuariosIntent)
        }

        //PROMOCIONES
        imgbtnPromociones.setOnClickListener {
            val notificacionIntent = Intent(this, promos_admin::class.java).apply {
                putExtra("email",email)
            }
            startActivity(notificacionIntent)
        }

    }
    private fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("Usuarios").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(
                value: QuerySnapshot?,
                error: FirebaseFirestoreException?
            ) {
                if(error!=null){
                    Log.e("Firestore Error", error.message.toString())
                    return
                }
                for(dc: DocumentChange in value?.documentChanges!!){
                    if(dc.type == DocumentChange.Type.ADDED) {
                        if (dc.document.toObject(clienteModel::class.java).Rol.toString()
                                .equals("Cliente")
                        )
                            userArrayList.add(dc.document.toObject(clienteModel::class.java))
                    }

                }
                adapterClientes.notifyDataSetChanged()
            }
        })
    }

}