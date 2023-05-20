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

class adapterPromos(private  val userList: ArrayList<menuModel>): RecyclerView.Adapter<adapterPromos.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterPromos.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.lista_promos_card,parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: adapterPromos.MyViewHolder, position: Int) {
        val user : menuModel = userList[position]
        holder.Nombre.text = user.Nombre
        holder.Precio.text = user.Precio
        holder.Descripcion.text = user.Descripcion
        val storageRef = FirebaseStorage.getInstance().reference.child("images/${user.Nombre}.jpg")
        val localfile = File.createTempFile("tempImage","jpg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            holder.img.setImageBitmap(bitmap)
        }.addOnFailureListener{

        }
    }

    override fun getItemCount(): Int {

        return userList.size

    }

    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val Nombre: TextView = itemView.findViewById(R.id.view_nombre)
        val Precio: TextView = itemView.findViewById(R.id.view_precio)
        val Descripcion: TextView = itemView.findViewById(R.id.viewDesc)
        val img: ImageView = itemView.findViewById(R.id.imgComida)
    }
}