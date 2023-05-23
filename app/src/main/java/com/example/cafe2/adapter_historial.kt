package com.example.cafe2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adapter_historial(private  val userList: ArrayList<model_historial>): RecyclerView.Adapter<adapter_historial.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapter_historial.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.historial_card,parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: adapter_historial.MyViewHolder, position: Int) {

        val user : model_historial = userList[position]
        holder.Producto.text = user.Producto
        holder.id_Pedido.text = user.id_Pedido
        holder.view_Precio2.text = user.Precio
        holder.view_Fecha2.text = user.Fecha

    }


    override fun getItemCount(): Int {

        return userList.size

    }

    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val Producto: TextView = itemView.findViewById(R.id.nombre_Productos)
        val id_Pedido: TextView = itemView.findViewById(R.id.view_id_pedido)
        val view_Precio2: TextView = itemView.findViewById(R.id.view_Precio2)
        val view_Fecha2: TextView = itemView.findViewById(R.id.view_fecha2)
    }
}