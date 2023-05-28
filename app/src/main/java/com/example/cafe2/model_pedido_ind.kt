package com.example.cafe2

import android.os.Parcel
import android.os.Parcelable

data class model_pedido_ind(val email:String?= null, val estado:String?=null , val numero:String?=null, val status:String?=null) :
    Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(estado)
        parcel.writeString(numero)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<model_pedido_ind> {
        override fun createFromParcel(parcel: Parcel): model_pedido_ind {
            return model_pedido_ind(parcel)
        }

        override fun newArray(size: Int): Array<model_pedido_ind?> {
            return arrayOfNulls(size)
        }
    }

}
