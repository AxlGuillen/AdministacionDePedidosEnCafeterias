package com.example.cafe2

import android.os.Parcel
import android.os.Parcelable

data class clienteModel(val Nombre:String?= null, val Email:String?= null,val Rol:String?= null): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Nombre)
        parcel.writeString(Email)
        parcel.writeString(Rol)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<clienteModel> {
        override fun createFromParcel(parcel: Parcel): clienteModel {
            return clienteModel(parcel)
        }

        override fun newArray(size: Int): Array<clienteModel?> {
            return arrayOfNulls(size)
        }
    }
}

