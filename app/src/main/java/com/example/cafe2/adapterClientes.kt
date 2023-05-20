package com.example.cafe2

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class adapterClientes (private  val userList: ArrayList<clienteModel>): RecyclerView.Adapter<adapterClientes.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterClientes.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.menu_clientes_admin_card,parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: adapterClientes.MyViewHolder, position: Int) {
        if(userList[position].Rol.equals("Cliente")){
        val user : clienteModel = userList[position]
        holder.Nombre.text = user.Nombre
        holder.Email.text = user.Email}
    }


    override fun getItemCount(): Int {
        return userList.size

    }

    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val Nombre: TextView = itemView.findViewById(R.id.nombre_cliente)
        val Email: TextView = itemView.findViewById(R.id.view_email)
    }
}