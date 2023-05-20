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

        //Aqui como rayos relacionaremos la imagen a la notificación? Supuse que si le damos el id de la fecha se podria diferenciar o algo asi
        val storageRef = FirebaseStorage.getInstance().reference.child("images/${user.Fecha}.jpg")
        val localfile = File.createTempFile("tempImage","jpg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            holder.img.setImageBitmap(bitmap)
        }.addOnFailureListener{

        }


    }

    override fun getItemCount(): Int {return userList.size}



    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val Titulo: TextView = itemView.findViewById(R.id.view_titulo)
        val Fecha: TextView = itemView.findViewById(R.id.view_fecha)
        val Descripcion: TextView = itemView.findViewById(R.id.viewDesc)
        val img: ImageView = itemView.findViewById(R.id.imgComida)
    }
}
