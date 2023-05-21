package com.example.cafe2

import android.os.Parcel
import android.os.Parcelable

data class menuModel(val Precio:String?= null, val Nombre:String?=null, val Descripcion:String?=null) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Precio)
        parcel.writeString(Nombre)
        parcel.writeString(Descripcion)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<menuModel> {
        override fun createFromParcel(parcel: Parcel): menuModel {
            return menuModel(parcel)
        }

        override fun newArray(size: Int): Array<menuModel?> {
            return arrayOfNulls(size)
        }
    }
}
