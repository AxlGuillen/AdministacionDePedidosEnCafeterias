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
import kotlinx.android.synthetic.main.activity_pedido_individual_curso.Cancelar
import kotlinx.android.synthetic.main.activity_pedido_individual_curso.Entregar
import kotlinx.android.synthetic.main.activity_pedido_individual_curso.imageButton
import kotlinx.android.synthetic.main.activity_pedido_individual_curso.imgbtnFlecha
import kotlinx.android.synthetic.main.activity_pedido_individual_curso.view_id_pedido
import kotlinx.android.synthetic.main.activity_pedido_individual_curso.view_status
import android.view.View;
import androidx.core.view.isVisible

class pedido_individual_curso : AppCompatActivity() {

    private lateinit var  recyclerView: RecyclerView
    private lateinit var  userArrayList:ArrayList<model_historial>
    private lateinit var  adapter_pedido_individual: adapter_pedido_individual
    private lateinit var  db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido_individual_curso)

        val pedido = intent.getParcelableExtra<model_pedidos_activos_cliente>("Pedido")
        val numero = pedido?.numero.toString()
        val destinatario = pedido?.email.toString()
        val etiqueta = intent.extras?.getString("Etiqueta")
        val email = intent.extras?.getString("email")
        val Rol = intent.extras?.getString("Rol")

        if(Rol.equals("Cliente")){
            Entregar.isVisible = false
            Entregar.isFocusable = false
            Cancelar.isVisible = false
            Cancelar.isFocusable = false
        }
        if (etiqueta.toString().equals("Aceptar/Cancelar")){
            Entregar.text = "Aceptar"
        }

        view_status.text = pedido?.email.toString()
        view_id_pedido.text = pedido?.numero.toString()

        recyclerView = findViewById(R.id.pedido_ind_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()

        adapter_pedido_individual = adapter_pedido_individual(userArrayList)

        recyclerView.adapter = adapter_pedido_individual

        EventChangeListener()

        Entregar.setOnClickListener {
            if (etiqueta.toString().equals("Aceptar/Cancelar")){
                val a = db.collection("Pedidos").document(pedido?.numero.toString())
                a.update("status","aceptado").addOnCompleteListener {
                //notiicacion de que su pedido fue aceptado
                db.collection("Notificaciones").document().set(
                    hashMapOf("Destinatario" to pedido?.email,
                        "Descripcion" to "Tu pedido fue aceptado y ya lo estamos preparando.",
                        "Estado" to false,
                        "Titulo" to "Pedido aceptado"
                    )
                ).addOnCompleteListener {
                    val perfilIntent = Intent(this, inicio_cajero::class.java).apply {
                        putExtra("email",email)
                    }
                    startActivity(perfilIntent)
                    finish()
                }
                }
            }
            else{
                val a = db.collection("Pedidos").document(pedido?.numero.toString())
                a.update("estado","entregado")
                val perfilIntent = Intent(this, inicio_cajero::class.java).apply {
                    putExtra("email",email)
                }
                startActivity(perfilIntent)
                finish()
            }
        }

        Cancelar.setOnClickListener {

            val perfilIntent = Intent(this, MotivoDeCancelacion::class.java).apply {
                putExtra("numero",numero)
                putExtra("destinatario",destinatario)
                putExtra("email",email)
            }
            startActivity(perfilIntent)

        }


        //FLECHITA
        imgbtnFlecha.setOnClickListener {
            onBackPressed()
        }

        //CASITA
        imageButton.setOnClickListener {
            val menuIntent = Intent(this, inicio_cajero::class.java).apply {
                putExtra("email",email)
            }
            startActivity(menuIntent)
            finish()
        }
    }

    private fun EventChangeListener() {

        val pedido = intent.getParcelableExtra<model_pedidos_activos_cliente>("Pedido")
        db = FirebaseFirestore.getInstance()
        //Le puse de nombre a la Coleccion "Ventas", porque aun no existe MODIFICARLO



        db.collection("Pedidos/${pedido?.numero.toString()}/Productos").addSnapshotListener(object : EventListener<QuerySnapshot> {
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
                adapter_pedido_individual.notifyDataSetChanged()
            }
        })




    }

}