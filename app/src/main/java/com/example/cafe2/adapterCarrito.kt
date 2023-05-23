package com.example.cafe2

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class adapterCarrito (private  val userList: ArrayList<model_Carrito>): RecyclerView.Adapter<adapterCarrito.MyViewHolder>(){

    //1 Se crea la variable para el evento
    var onItemClick : ((model_Carrito) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterCarrito.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.lista_promos_card,parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: adapterCarrito.MyViewHolder, position: Int) {
        val user : model_Carrito = userList[position]
        holder.NombreProducto.text = user.NombreProducto
        holder.Precio.text = user.Precio
        holder.Descripcion.text = user.Descripcion
        val storageRef = FirebaseStorage.getInstance().reference.child("images/${user.NombreProducto}.jpg")
        val localfile = File.createTempFile("tempImage","jpg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            holder.img.setImageBitmap(bitmap)
        }.addOnFailureListener{

        }

        //2 Se hace el evento de picarle al boton
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(user)
        }
    }

    override fun getItemCount(): Int {

        return userList.size

    }

    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val NombreProducto: TextView = itemView.findViewById(R.id.view_nombre)
        val Precio: TextView = itemView.findViewById(R.id.view_precio)
        val Descripcion: TextView = itemView.findViewById(R.id.viewDesc)
        val img: ImageView = itemView.findViewById(R.id.imgComida)
    }
}