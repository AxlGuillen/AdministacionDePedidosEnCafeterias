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
import kotlinx.android.synthetic.main.activity_pedidos_activos_cliente.btnPerfilInicioAdm
import kotlinx.android.synthetic.main.activity_pedidos_activos_cliente.btnRegresar2
import kotlinx.android.synthetic.main.activity_pedidos_activos_cliente.imgbtnHome2

class pedidos_activos_cliente : AppCompatActivity() {

    private lateinit var  recyclerView: RecyclerView
    private lateinit var  userArrayList:ArrayList<model_pedidos_activos_cliente>
    private lateinit var  adapter_pedidos_activos_cliente: adapter_pedidos_activos_cliente
    private lateinit var  db : FirebaseFirestore
    var Rol = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedidos_activos_cliente)


        intent.extras
        val bundle = intent.extras
        val email: String? = bundle?.getString("email")
        val porAceptar: String? = bundle?.getString("porAceptar")

        recyclerView = findViewById(R.id.pedidosClieRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()

        adapter_pedidos_activos_cliente = adapter_pedidos_activos_cliente(userArrayList)

        recyclerView.adapter = adapter_pedidos_activos_cliente

        db = FirebaseFirestore.getInstance()

        if (porAceptar == null){
            obtenerRol()
        }
        if (porAceptar.equals("porAceptar")) {

            EventChangeListenerCajeros()
        }

        adapter_pedidos_activos_cliente.onItemClick = {
            if (porAceptar == null){
            val intent = Intent(this,pedido_individual_curso::class.java)
            intent.putExtra("Pedido", it)
            intent.putExtra("email",email)

            startActivity(intent)
            }
            if (porAceptar.equals("porAceptar")) {
                val intent = Intent(this,pedido_individual_curso::class.java)
                intent.putExtra("Pedido", it)
                intent.putExtra("email",email)
                intent.putExtra("Etiqueta","Aceptar/Cancelar")
                startActivity(intent)
            }
        }

        btnRegresar2.setOnClickListener {
            onBackPressed()
        }

        btnPerfilInicioAdm.setOnClickListener {
            val perfilIntent = Intent(this, Perfil::class.java).apply {
                putExtra("email",email)
            }
            startActivity(perfilIntent)
        }

        //casita
        imgbtnHome2.setOnClickListener {
            val menuIntent = Intent(this, inicio_cajero::class.java).apply {
                putExtra("email",email)
            }
            startActivity(menuIntent)
            finish()
        }

        //histor

    }

    private fun EventChangeListenerCajeros() {
        db = FirebaseFirestore.getInstance()
        //Le puse de nombre a la Coleccion "Pedidos", porque aun no existe, Creo jajaja
        db.collection("Pedidos").addSnapshotListener(object : EventListener<QuerySnapshot> {
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
                        if(dc.document.toObject(model_pedidos_activos_cliente::class.java).status.toString().equals("procesando")
                            && dc.document.toObject(model_pedidos_activos_cliente::class.java).estado.toString().equals("pendiente")) {
                            userArrayList.add(dc.document.toObject(model_pedidos_activos_cliente::class.java))
                        }
                    }
                }
                adapter_pedidos_activos_cliente.notifyDataSetChanged()
            }
        })
    }

    private fun EventChangeListenerCajeros2() {
        db = FirebaseFirestore.getInstance()
        //Le puse de nombre a la Coleccion "Pedidos", porque aun no existe, Creo jajaja
        db.collection("Pedidos").addSnapshotListener(object : EventListener<QuerySnapshot> {
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
                        if(dc.document.toObject(model_pedidos_activos_cliente::class.java).status.toString().equals("aceptado")
                            && dc.document.toObject(model_pedidos_activos_cliente::class.java).estado.toString().equals("pendiente")) {
                            userArrayList.add(dc.document.toObject(model_pedidos_activos_cliente::class.java))
                        }
                    }
                }
                adapter_pedidos_activos_cliente.notifyDataSetChanged()
            }
        })
    }

    private fun EventChangeListenerClientes() {
        intent.extras
        val bundle = intent.extras
        val email: String? = bundle?.getString("email")
        db = FirebaseFirestore.getInstance()
        //Le puse de nombre a la Coleccion "Pedidos", porque aun no existe, Creo jajaja
        db.collection("Pedidos").addSnapshotListener(object : EventListener<QuerySnapshot> {
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
                        if(dc.document.toObject(model_pedidos_activos_cliente::class.java).status.toString().equals("aceptado")
                            && dc.document.toObject(model_pedidos_activos_cliente::class.java).estado.toString().equals("pendiente")
                            && dc.document.toObject(model_pedidos_activos_cliente::class.java).email.toString().equals(email)) {
                            userArrayList.add(dc.document.toObject(model_pedidos_activos_cliente::class.java))
                        }
                    }
                }
                adapter_pedidos_activos_cliente.notifyDataSetChanged()
            }
        })
    }

    private fun EventChangeListenerAdm() {
        intent.extras
        val bundle = intent.extras
        val email: String? = bundle?.getString("email")
        db = FirebaseFirestore.getInstance()
        //Le puse de nombre a la Coleccion "Pedidos", porque aun no existe, Creo jajaja
        db.collection("Pedidos").addSnapshotListener(object : EventListener<QuerySnapshot> {
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
                        if(dc.document.toObject(model_pedidos_activos_cliente::class.java).status.toString().equals("aceptado")
                            && dc.document.toObject(model_pedidos_activos_cliente::class.java).estado.toString().equals("entregado")) {
                            userArrayList.add(dc.document.toObject(model_pedidos_activos_cliente::class.java))
                        }
                    }
                }
                adapter_pedidos_activos_cliente.notifyDataSetChanged()
            }
        })
    }

    private  fun  obtenerRol(){
        intent.extras
        val bundle = intent.extras
        val email: String? = bundle?.getString("email")

        db.collection("Usuarios").document(email.toString()).get().addOnSuccessListener {
            Rol = (it.get("Rol") as String?).toString()

            if (Rol.equals("Cliente")){
                EventChangeListenerClientes()
            }
            if (Rol.equals("Administrador")){
                EventChangeListenerAdm()
            }
            if (Rol.equals("Cajero")){
                EventChangeListenerCajeros2()
            }
        }
    }

}