package com.example.cafe2

import android.os.Parcel
import android.os.Parcelable

data class model_pedidos_activos_cliente(val email:String?= null, val estado:String?=null , val numero:String?=null, val status:String?=null) :
    Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(estado)
        parcel.writeString(numero)
        parcel.writeString(status)
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
