package com.example.cafe2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class menu_cajeros_admin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_cajeros_admin)

        val recycler : RecyclerView = findViewById(R.id.clientesRecycler)
        val adapter : adapterCajeros = adapterCajeros()

        //Configuracion del Adapter

        adapter.cajerosAdapter(clientes(), this)


        //Configuracion del RecyclerView
        recycler.hasFixedSize()
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

    }
    private fun clientes(): MutableList<clienteModel> {
        var clieModels : MutableList<clienteModel> = ArrayList()
        clieModels.add(clienteModel("Cajero 1", "email1@hotmail.com"))
        clieModels.add(clienteModel("Cajero 2", "email1@hotmail.com"))
        clieModels.add(clienteModel("Cajero 3", "email1@hotmail.com"))
        clieModels.add(clienteModel("Cajero 4", "email1@hotmail.com"))
        clieModels.add(clienteModel("Cajero 5", "email1@hotmail.com"))
        clieModels.add(clienteModel("Cajero 6", "email1@hotmail.com"))
        return clieModels

    }
}
