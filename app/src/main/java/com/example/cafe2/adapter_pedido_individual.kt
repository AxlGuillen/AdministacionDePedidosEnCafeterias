package com.example.cafe2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adapter_pedido_individual (private  val userList: ArrayList<model_pedido_ind>): RecyclerView.Adapter<adapter_pedido_individual.MyViewHolder>(){
    //1 Se crea la variable para el evento
    var onItemClick : ((model_pedido_ind) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapter_pedido_individual.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_pedido_individual,parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: adapter_pedido_individual.MyViewHolder, position: Int) {
        val user : model_pedido_ind = userList[position]
        holder.Producto.text = user.Producto
        holder.Precio.text = user.Precio
        holder.Detalles.text = user.Detalles
        holder.Cantidad.text = user.Cantidad

        //2 Se hace el evento de picarle al boton
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(user)
        }
    }



    override fun getItemCount(): Int {
        return userList.size

    }

    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val Producto: TextView = itemView.findViewById(R.id.view_nombre)
        val Precio: TextView = itemView.findViewById(R.id.view_precio)
        val Detalles: TextView = itemView.findViewById(R.id.viewDet)
        val Cantidad: TextView = itemView.findViewById(R.id.view_cantidad)
    }
}