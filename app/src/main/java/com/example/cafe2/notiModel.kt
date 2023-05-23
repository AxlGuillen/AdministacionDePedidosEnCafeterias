package com.example.cafe2

import android.os.Parcel
import android.os.Parcelable

data class notiModel(var Descripcion : String?= null, var Fecha : String?= null, var Titulo : String?= null, var Destinatario : String?= null):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Descripcion)
        parcel.writeString(Fecha)
        parcel.writeString(Titulo)
        parcel.writeString(Destinatario)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<notiModel> {
        override fun createFromParcel(parcel: Parcel): notiModel {
            return notiModel(parcel)
        }

        override fun newArray(size: Int): Array<notiModel?> {
            return arrayOfNulls(size)
        }
    }
}
