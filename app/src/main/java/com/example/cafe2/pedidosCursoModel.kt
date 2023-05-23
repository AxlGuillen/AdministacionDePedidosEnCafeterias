package com.example.cafe2

import android.os.Parcel
import android.os.Parcelable

data class pedidosCursoModel(val Nombre:String?= null, val id_Pedido:String?=null) :
    Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Nombre)
        parcel.writeString(id_Pedido)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<pedidosCursoModel> {
        override fun createFromParcel(parcel: Parcel): pedidosCursoModel {
            return pedidosCursoModel(parcel)
        }

        override fun newArray(size: Int): Array<pedidosCursoModel?> {
            return arrayOfNulls(size)
        }
    }

}
