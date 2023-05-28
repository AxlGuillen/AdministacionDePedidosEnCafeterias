package com.example.cafe2

import android.os.Parcel
import android.os.Parcelable

data class model_historial(val Cantidad:String?= null, val Comentarios :String?=null, val Descripcion:String?=null, val NombreProducto: String?=null, val Precio: String?=null,
                           val email: String?=null, val estado: String?=null, val fecha: String?=null, val hora: String?=null, val idPedido: String?=null): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Cantidad)
        parcel.writeString(Comentarios)
        parcel.writeString(Descripcion)
        parcel.writeString(NombreProducto)
        parcel.writeString(Precio)
        parcel.writeString(email)
        parcel.writeString(estado)
        parcel.writeString(fecha)
        parcel.writeString(hora)
        parcel.writeString(idPedido)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<model_historial> {
        override fun createFromParcel(parcel: Parcel): model_historial {
            return model_historial(parcel)
        }

        override fun newArray(size: Int): Array<model_historial?> {
            return arrayOfNulls(size)
        }
    }
}
