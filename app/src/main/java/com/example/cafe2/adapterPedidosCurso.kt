package com.example.cafe2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adapterPedidosCurso (private  val userList: ArrayList<pedidosCursoModel>): RecyclerView.Adapter<adapterPedidosCurso.MyViewHolder>(){
    //1 Se crea la variable para el evento
    var onItemClick : ((pedidosCursoModel) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterPedidosCurso.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_pedidos_curso,parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: adapterPedidosCurso.MyViewHolder, position: Int) {
        val user : pedidosCursoModel = userList[position]
        holder.Nombre.text = user.Nombre
        holder.id_Pedido.text = user.id_Pedido

        //2 Se hace el evento de picarle al boton
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(user)
        }
    }



    override fun getItemCount(): Int {
        return userList.size

    }

    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val Nombre: TextView = itemView.findViewById(R.id.view_nombre)
        val id_Pedido: TextView = itemView.findViewById(R.id.view_id_pedido)
    }
}