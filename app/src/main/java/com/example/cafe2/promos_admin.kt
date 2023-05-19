package com.example.cafe2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class promos_admin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_menu_admin)

        val recycler : RecyclerView = findViewById(R.id.clientesRecycler)
        val adapter : adapterPromos = adapterPromos()

        //Configuracion del Adapter
        adapter.adapterPromos(plati(), this)


        //Configuracion del RecyclerView
        recycler.hasFixedSize()
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

    }

    private fun plati(): MutableList<menuModel> {
        var plaModels : MutableList<menuModel> = ArrayList()
   //     plaModels.add(menuModel("Promo 1", "500","Descripcion 1"))
     //   plaModels.add(menuModel("Promo 2", "500","Descripcion 2"))
   //     plaModels.add(menuModel("Promo 3", "500","Descripcion 3"))
    //    plaModels.add(menuModel("Promo 4", "500","Descripcion 4"))
  //      plaModels.add(menuModel("Promo 5", "500","Descripcion 5"))
       // plaModels.add(menuModel("Promo 6", "500","Descripcion 6"))
        return plaModels

    }
}