package com.einfochips.aidlserver

import android.os.Parcel
import android.os.Parcelable

enum class StudentEnum(val id: Int) : Parcelable {
    JUNIOR(123),
    SENIOR(456);

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<StudentEnum> = object : Parcelable.Creator<StudentEnum> {
            override fun createFromParcel(parcel: Parcel): StudentEnum {
                return values()[parcel.readInt()]
            }

            override fun newArray(size: Int): Array<StudentEnum?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(ordinal)  // Write the enum ordinal to the Parcel
    }

    override fun describeContents(): Int {
        return 0
    }
}