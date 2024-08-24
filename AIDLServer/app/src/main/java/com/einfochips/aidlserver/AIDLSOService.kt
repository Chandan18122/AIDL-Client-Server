package com.einfochips.aidlserver

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import kotlin.random.Random

class AIDLSOService : Service() {

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    private val binder = object : IMyAidlInterface.Stub() {
        @Throws(RemoteException::class)
        override fun addFromAIDL(num1: Int, num2: Int) : Int  {
            return num1 + num2
        }

        override fun subFromAIDL(num1: Int, num2: Int) : Int  {
            return num1 - num2
        }

        override fun randomNumberAIDL(): Int {
            return Random.nextInt(100000, 1000000)
        }

        override fun getStudentDetailsAIDL(student: Student?): String {
            var marks = student?.marks?.joinToString(separator = ", ") { getMarksDetails(it) }
            return "${getStudentDetails(student)}, [${marks}]"
        }

        private fun getMarksDetails(marks: Marks?): String{
            return "${marks?.subjectName}: ${marks?.subjectMark}"
        }

        private fun getStudentDetails(student: Student?): String{
            return "Name: ${student?.name}, Roll: ${student?.roll}, Age: ${student?.age}, Score: ${student?.score}"
        }

        override fun createStudentAIDL(
            roll: Int,
            name: String?,
            age: Int,
            score: Int,
            marks: MutableList<Marks>?
        ): Student {
            val totalScore = marks?.sumOf { it.subjectMark }?: score
            return Student(roll, name, age, totalScore, marks)
        }

        override fun getResultAIDL(student: Student?): Result? {
            // generate Result based on the student data

            val roll = student?.roll?: 0
            val averageScore = student?.score?.div(student.marks!!.size)
            if (averageScore != null) {
                return when {
                    averageScore >= 90 -> Result(roll ,"Excellent", "$averageScore")
                    averageScore >= 75 -> Result(roll,"Good", "$averageScore")
                    averageScore >= 50 -> Result(roll,"Average", "$averageScore")
                    else -> Result(roll,"Poor", "$averageScore")
                }
            }
            return null
        }

        override fun createStudentFromBundleAIDL(bundle: Bundle?): String {
            val student = bundle?.getParcelable<Student>("student")
            return student?.let {
                getStudentDetailsAIDL(it)
            } ?: "Invalid student data"
        }

        override fun sendStudentsAIDL(list: MutableList<Student>?): String {
            return list?.joinToString(separator = "\n") { getStudentDetails(it) } ?: "Received ${list?.size} students"
        }

        override fun sendStudentEnumAIDL(studentEnum: StudentEnum?): Int {
            return studentEnum?.id ?: 0
        }

        override fun basicTypes(
            anInt: Int,
            aLong: Long,
            aBoolean: Boolean,
            aFloat: Float,
            aDouble: Double,
            aString: String?
        ): Boolean {
            return true
        }
    }
}