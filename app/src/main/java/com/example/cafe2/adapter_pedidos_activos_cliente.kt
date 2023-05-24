package com.example.cafe2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adapter_pedidos_activos_cliente (private  val userList: ArrayList<model_pedidos_activos_cliente>): RecyclerView.Adapter<adapter_pedidos_activos_cliente.MyViewHolder>(){

    var onItemClick : ((model_pedidos_activos_cliente) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapter_pedidos_activos_cliente.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_pedidos_activos_cliente,parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: adapter_pedidos_activos_cliente.MyViewHolder, position: Int) {
        val user : model_pedidos_activos_cliente = userList[position]
        holder.Producto.text = user.Producto
        holder.Fecha.text = user.Fecha

        //2 Se hace el evento de picarle al boton
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(user)
        }
    }

    override fun getItemCount(): Int {
        return userList.size

    }

    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val Producto: TextView = itemView.findViewById(R.id.nombre_Productos)
        val Fecha: TextView = itemView.findViewById(R.id.view_fecha)
    }

}