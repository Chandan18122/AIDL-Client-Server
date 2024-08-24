// IMyAidlInterface.aidl
package com.einfochips.aidlserver;

// Declare any non-default types here with import statements

import java.util.List;
import android.os.Bundle;

parcelable Student;
parcelable Marks;
parcelable Result;
parcelable StudentEnum;

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString);

    int addFromAIDL(int num1, int num2);
    int subFromAIDL(int num1, int num2);

    int randomNumberAIDL();

    String getStudentDetailsAIDL(in Student student);
    Student createStudentAIDL(int roll, String name, int age, int score, in List<Marks> marks);
    Result getResultAIDL(in Student student);
    String createStudentFromBundleAIDL(in Bundle bundle);
    String sendStudentsAIDL(in List<Student> list);
    int sendStudentEnumAIDL(in StudentEnum studentEnum);

    boolean basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString);


}