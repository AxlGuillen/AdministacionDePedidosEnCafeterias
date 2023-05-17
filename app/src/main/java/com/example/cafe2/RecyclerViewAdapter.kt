package com.example.cafe2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    var platillos : MutableList<menuModel> = ArrayList()
    lateinit var context : Context

    fun RecyclerViewAdapter(platillos:MutableList<menuModel>, context:Context){
        this.platillos = platillos
        this.context = context
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nombre.text = platillos[position].nombre
        holder.precio.text = platillos[position].precio
        holder.desc.text = platillos[position].desc
    }
    class ViewHolder (view : View):RecyclerView.ViewHolder(view){
        val nombre: TextView
        val precio:TextView
        val desc:TextView

        init{
            nombre = view.findViewById(R.id.view_nombre)
            precio = view.findViewById(R.id.view_precio)
            desc = view.findViewById(R.id.viewDesc)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lista_admin_menu_card, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = platillos.size


}