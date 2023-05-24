package com.example.cafe2

import android.os.Parcel
import android.os.Parcelable

data class model_pedidos_activos_cliente(val Producto:String?= null, val Fecha:String?=null) :
    Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Producto)
        parcel.writeString(Fecha)
    }

    companion object CREATOR : Parcelable.Creator<model_pedidos_activos_cliente> {
        override fun createFromParcel(parcel: Parcel): model_pedidos_activos_cliente {
            return model_pedidos_activos_cliente(parcel)
        }

        override fun newArray(size: Int): Array<model_pedidos_activos_cliente?> {
            return arrayOfNulls(size)
        }
    }

    }
