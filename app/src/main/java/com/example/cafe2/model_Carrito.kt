package com.example.cafe2

import android.os.Parcel
import android.os.Parcelable

class model_Carrito ( val Cantidad:String?= null, val Comentarios:String?= null, val NombreProducto:String?= null,val Descripcion:String?= null,val Precio:String?= null): Parcelable {
    constructor(parcel: Parcel) : this(
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
        parcel.writeString(NombreProducto)
        parcel.writeString(Descripcion)
        parcel.writeString(Precio)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<model_Carrito> {
        override fun createFromParcel(parcel: Parcel): model_Carrito {
            return model_Carrito(parcel)
        }

        override fun newArray(size: Int): Array<model_Carrito?> {
            return arrayOfNulls(size)
        }
    }
}