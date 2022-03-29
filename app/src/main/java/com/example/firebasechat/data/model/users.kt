package com.example.firebasechat.data.model

import android.os.Parcel
import android.os.Parcelable

data class users(var uid:String?="",var name:String?="",var email:String?="",var imageUri:String?="",var status:String?="") :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(imageUri)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<users> {
        override fun createFromParcel(parcel: Parcel): users {
            return users(parcel)
        }

        override fun newArray(size: Int): Array<users?> {
            return arrayOfNulls(size)
        }
    }
}