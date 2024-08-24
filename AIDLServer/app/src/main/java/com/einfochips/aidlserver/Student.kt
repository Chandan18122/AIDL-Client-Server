package com.einfochips.aidlserver

import android.os.Parcel
import android.os.Parcelable


data class Student(var roll: Int,
                   var name: String?,
                   var age: Int,
                   var score: Int,
                   var marks: List<Marks>? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.createTypedArrayList(Marks.CREATOR)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(roll)
        parcel.writeString(name)
        parcel.writeInt(age)
        parcel.writeInt(score)
        parcel.writeTypedList(marks)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Student> {
        override fun createFromParcel(parcel: Parcel): Student {
            return Student(parcel)
        }

        override fun newArray(size: Int): Array<Student?> {
            return arrayOfNulls(size)
        }
    }
}
