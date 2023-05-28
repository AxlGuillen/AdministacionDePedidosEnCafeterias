package com.example.cafe2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class notiAdapter (private  val userList: ArrayList<notiModel>): RecyclerView.Adapter<notiAdapter.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): notiAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.lista_noti_card,parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: notiAdapter.MyViewHolder, position: Int) {

        //Me imagino que aqui irian los condicionantes para desplegar las notificaciones de cada usuario o algo así
            val user : notiModel = userList[position]
                holder.Descripcion.text = user.Descripcion
                holder.Fecha.text = user.Fecha
                holder.Titulo.text = user.Titulo

    }

    override fun getItemCount(): Int {return userList.size}



    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val Titulo: TextView = itemView.findViewById(R.id.view_titulo)
        val Fecha: TextView = itemView.findViewById(R.id.view_correo)
        val Descripcion: TextView = itemView.findViewById(R.id.viewDesc)
    }
}
