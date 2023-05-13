package com.example.cafe2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class menu_admin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_admin)

        val recycler : RecyclerView = findViewById(R.id.menuRecycler)
        val adapter : RecyclerViewAdapter = RecyclerViewAdapter()

        //Configuracion del Adapter

        adapter.RecyclerViewAdapter(plati(), this)


        //Configuracion del RecyclerView
        recycler.hasFixedSize()
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

    }

    private fun plati(): MutableList<menuModel> {
        var plaModels : MutableList<menuModel> = ArrayList()
        plaModels.add(menuModel("Chilaquiles", "500","ChilaquilesChingones"))
        plaModels.add(menuModel("Chilaquiles", "500","ChilaquilesChingones"))
        plaModels.add(menuModel("Chilaquiles", "500","ChilaquilesChingones"))
        plaModels.add(menuModel("Chilaquiles", "500","ChilaquilesChingones"))
        plaModels.add(menuModel("Chilaquiles", "500","ChilaquilesChingones"))
        plaModels.add(menuModel("Chilaquiles", "500","ChilaquilesChingones"))
        return plaModels

    }
}
