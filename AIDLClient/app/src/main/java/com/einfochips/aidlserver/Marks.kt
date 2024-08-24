package com.einfochips.aidlserver

import android.os.Parcel
import android.os.Parcelable

data class Marks(var subjectName: String?,
                 var subjectMark: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(subjectName)
        parcel.writeInt(subjectMark)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Marks> {
        override fun createFromParcel(parcel: Parcel): Marks {
            return Marks(parcel)
        }

        override fun newArray(size: Int): Array<Marks?> {
            return arrayOfNulls(size)
        }
    }
}
