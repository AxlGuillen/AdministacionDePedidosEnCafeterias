package com.example.cafe2

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class adapter_pedido_individual (private  val userList: ArrayList<model_historial>): RecyclerView.Adapter<adapter_pedido_individual.MyViewHolder>(){
    //1 Se crea la variable para el evento
    var onItemClick : ((model_historial) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapter_pedido_individual.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_pedido_individual,parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: adapter_pedido_individual.MyViewHolder, position: Int) {
        val user : model_historial = userList[position]
        holder.nombreProducto.text = user.NombreProducto
        holder.Precio.text = user.Precio
        holder.Detalles.text = user.Comentarios
        holder.Cantidad.text = user.Cantidad
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
        val nombreProducto: TextView = itemView.findViewById(R.id.view_status)
        val Precio: TextView = itemView.findViewById(R.id.view_precio)
        val Detalles: TextView = itemView.findViewById(R.id.viewDet)
        val Cantidad: TextView = itemView.findViewById(R.id.view_cantidad)
        val img: ImageView = itemView.findViewById(R.id.imgComida)
    }
}