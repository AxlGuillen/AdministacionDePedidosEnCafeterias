package com.example.cafe2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class menu_clientes_admin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_clientes_admin)

        val recycler : RecyclerView = findViewById(R.id.clientesRecycler)
        val adapter : adapterClientes = adapterClientes()

        //Configuracion del Adapter

        adapter.clientesAdapter(clientes(), this)


        //Configuracion del RecyclerView
        recycler.hasFixedSize()
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

    }
    private fun clientes(): MutableList<clienteModel> {
        var clieModels : MutableList<clienteModel> = ArrayList()
        clieModels.add(clienteModel("Cliente 1", "email1@hotmail.com"))
        clieModels.add(clienteModel("Cliente 2", "email1@hotmail.com"))
        clieModels.add(clienteModel("Cliente 3", "email1@hotmail.com"))
        clieModels.add(clienteModel("Cliente 4", "email1@hotmail.com"))
        clieModels.add(clienteModel("Cliente 5", "email1@hotmail.com"))
        clieModels.add(clienteModel("Cliente 6", "email1@hotmail.com"))
        return clieModels

    }

}