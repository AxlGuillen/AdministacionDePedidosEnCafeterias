package com.example.cafe2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_carrito_compras.editTextText
import kotlinx.android.synthetic.main.activity_carrito_compras.editTextText2
import kotlinx.android.synthetic.main.activity_carrito_compras.imgbtnFlecha4
import kotlinx.android.synthetic.main.activity_carrito_compras.imgbtnHistorial5
import kotlinx.android.synthetic.main.activity_carrito_compras.imgbtnHome2
import kotlinx.android.synthetic.main.activity_carrito_compras.imgbtnNotificaciones4
import kotlinx.android.synthetic.main.activity_carrito_compras.imgbtnPerfil4
import kotlinx.android.synthetic.main.activity_carrito_compras.imgbtnpromos4
import kotlinx.android.synthetic.main.activity_carrito_compras.pagar


class CarritoCompras : AppCompatActivity() {
    private lateinit var  recyclerView: RecyclerView
    private lateinit var  userArrayList:ArrayList<model_Carrito>
    private lateinit var  myAdapter: adapterCarrito
    private lateinit var  db : FirebaseFirestore
    private lateinit var fecha:String
    private lateinit var hora:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito_compras)

        //sacamos el email para mandarlo a otro lados
        intent.extras
        val bundle = intent.extras
        val email:String? = bundle?.getString("email")

        recyclerView = findViewById(R.id.carritoRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)


        userArrayList = arrayListOf()

        myAdapter = adapterCarrito(userArrayList)

        recyclerView.adapter = myAdapter

        EventChangeListener()

        myAdapter.onItemClick = {
            val intent = Intent(this, EditarMiPedido::class.java)
            intent.putExtra("Carrito", it)
            intent.putExtra("email",email)
            startActivity(intent)
        }
        editTextText2.setOnClickListener { showTimePickerDialog() }

        //boton de la flecha
        imgbtnFlecha4.setOnClickListener {
            onBackPressed()
        }

        //NOTIFICACIONES
        imgbtnNotificaciones4.setOnClickListener {
            val Intent = Intent(this, Notificaciones::class.java).apply {
                putExtra("email",email)
            }
            startActivity(Intent)
        }

        //boton de perfil
        imgbtnPerfil4.setOnClickListener {
            val perfilIntent = Intent(this, Perfil::class.java).apply {
                putExtra("email",email)
            }
            startActivity(perfilIntent)
        }

        //Historial
        imgbtnHistorial5.setOnClickListener {
            val intent = Intent(this, menu_historial::class.java).apply {
                putExtra("email",email)
            }
            startActivity(intent)
        }


        //HOME
        imgbtnHome2.setOnClickListener {
            val menuIntent = Intent(this, inicio_clientes::class.java).apply {
                putExtra("email",email)
            }
            startActivity(menuIntent)
            finish()
        }


        //PROMOCIONES
        imgbtnpromos4.setOnClickListener {
            val notificacionIntent = Intent(this, promos_clientes::class.java).apply {
                putExtra("email",email)
            }
            startActivity(notificacionIntent)
        }
        editTextText.setOnClickListener { showDatePickerDialog() }

        //PAGAR
        pagar.setOnClickListener {
            intent.extras
            val bundle = intent.extras
            val email:String? = bundle?.getString("email")

            val intent = Intent(this, ConfirmarPagoConTarjeta::class.java).apply {
                putExtra("email",email)
                putExtra("fecha",fecha)
                putExtra("hora",hora)
                putExtra("arreglo",userArrayList)
            }
            startActivity(intent)
                }
            }

    private fun showTimePickerDialog() {
        val timePicker = HoraPickerFragment { onTimeSelected(it) }
        timePicker.show(supportFragmentManager, "timePicker")
    }

    private fun onTimeSelected(time: String) {
        editTextText2.setText("Reserva para las $time")
        hora = time
    }

    private fun showDatePickerDialog() {
        val datePicker = FechaDateFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        editTextText.setText("$day del $month del año $year")
        fecha = "${day}-${month}-${year}"
    }



    private fun EventChangeListener() {
        intent.extras
        val bundle = intent.extras
        val email:String? = bundle?.getString("email")

        db = FirebaseFirestore.getInstance()
        db.collection("Carrito/${email}/Productos").addSnapshotListener(object : EventListener<QuerySnapshot> {
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
                        userArrayList.add(dc.document.toObject(model_Carrito::class.java))


                    }
                }
                myAdapter.notifyDataSetChanged()
            }
        })
    }
}