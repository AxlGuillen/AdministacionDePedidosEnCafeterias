package com.example.cafe2

import android.os.Parcel
import android.os.Parcelable

data class model_historial(val Producto:String?= null, val id_Pedido:String?=null, val Fecha:String?=null, val Precio: String?=null) : Parcelable{

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Precio)
        parcel.writeString(Producto)
        parcel.writeString(id_Pedido)
        parcel.writeString(Fecha)
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
