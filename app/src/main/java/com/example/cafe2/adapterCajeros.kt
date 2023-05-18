package com.example.cafe2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adapterCajeros : RecyclerView.Adapter<adapterCajeros.ViewHolder>() {

    var clientes: MutableList<clienteModel> = ArrayList()
    lateinit var context: Context


    fun cajerosAdapter(clientes: MutableList<clienteModel>, context: Context) {
        this.clientes = clientes
        this.context = context
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView
        val email: TextView

        init {
            nombre = view.findViewById(R.id.view_nombre)
            email = view.findViewById(R.id.view_email)
        }

    }

    override fun onBindViewHolder(holder: adapterCajeros.ViewHolder, position: Int) {
        holder.nombre.text = clientes[position].nombre
        holder.email.text = clientes[position].email
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterCajeros.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_cajeros_admin_card, parent, false)
        return adapterCajeros.ViewHolder(view)
    }

    override fun getItemCount() = clientes.size
}