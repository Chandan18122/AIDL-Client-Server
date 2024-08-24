package com.einfochips.aidlserver

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import android.view.View
import android.widget.Toast
import com.einfochips.aidlclient.R
import com.einfochips.aidlclient.databinding.ActivityMainBinding
import com.einfochips.aidlserver.IMyAidlInterface

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var iMyAidlInterface: IMyAidlInterface
    private var TAG:String = "MainActivity"
    private var student: Student? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service)
            Log.d(TAG, "onServiceConnected: ")
            Toast.makeText(this@MainActivity, "Service Connected", Toast.LENGTH_LONG).show()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected: ")
            Toast.makeText(this@MainActivity, "Service Disconnected", Toast.LENGTH_LONG).show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent("android.intent.action.AIDLSOService")
        intent.setPackage("com.einfochips.aidlserver")
        bindService(intent, serviceConnection, BIND_AUTO_CREATE)

        // Set click listeners
        binding.addButton.setOnClickListener(this)
        binding.randomNumButton.setOnClickListener(this)
        binding.subButton.setOnClickListener(this)
        binding.createStudentButton.setOnClickListener(this)
        binding.getStudentDetailsButton.setOnClickListener(this)
        binding.getResultButton.setOnClickListener(this)
        binding.studentFromBundleButton.setOnClickListener(this)
        binding.arrayListOfStudentsResultButton.setOnClickListener(this)
        binding.studentEnumButton.setOnClickListener(this)
        binding.basicTypeButton.setOnClickListener(this)
    }

    private fun createSampleStudent(): Student {
        val marksList = arrayListOf(
            Marks("Math", 96),
            Marks("English", 65),
            Marks("Hindi", 80)
        )
        return iMyAidlInterface.createStudentAIDL(1, "Chandan Kumar", 24, 85, marksList)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addButton -> handleAddButtonClick()
            R.id.randomNumButton -> handleRandomNumButtonClick()
            R.id.subButton -> handleSubButtonClick()
            R.id.createStudentButton -> handleCreateStudentButtonClick()
            R.id.getStudentDetailsButton -> handleGetStudentDetailsButtonClick()
            R.id.getResultButton -> handleGetResultButtonClick()
            R.id.studentFromBundleButton -> handleStudentFromBundleButtonClick()
            R.id.arrayListOfStudentsResultButton -> handleArrayListOfStudentsResultButtonClick()
            R.id.studentEnumButton -> handleStudentEnumButtonClick()
            R.id.basicTypeButton -> handleBasicTypeButtonClick()
        }
    }

    private fun handleAddButtonClick() {
        val num1 = binding.editTextNumber1.text.toString().toIntOrNull() ?: 0
        val num2 = binding.editTextNumber2.text.toString().toIntOrNull() ?: 0
        var answer = 0
        try {
            answer = iMyAidlInterface.addFromAIDL(num1, num2)
        } catch (e: RemoteException) {
            throw RuntimeException(e)
        }
        binding.sampleText.text = "$num1 + $num2 = $answer"
    }

    private fun handleRandomNumButtonClick() {
        binding.sampleText.text = "Random number: ${iMyAidlInterface.randomNumberAIDL()}"
    }

    private fun handleSubButtonClick() {
        val num1 = binding.editTextNumber1.text.toString().toIntOrNull() ?: 0
        val num2 = binding.editTextNumber2.text.toString().toIntOrNull() ?: 0
        var answer = 0
        try {
            answer = iMyAidlInterface.subFromAIDL(num1, num2)
        } catch (e: RemoteException) {
            throw RuntimeException(e)
        }
        binding.sampleText.text = "$num1 - $num2 = $answer"
    }

    private fun handleCreateStudentButtonClick() {
        student = createSampleStudent()
        binding.sampleText.text = "Student Data Created"
    }

    private fun handleGetStudentDetailsButtonClick() {
        if (student == null){
            handleCreateStudentButtonClick()
        }
        binding.sampleText.text = iMyAidlInterface.getStudentDetailsAIDL(student)
    }

    private fun handleGetResultButtonClick() {
        if (student == null){
            handleCreateStudentButtonClick()
        }
        val result = iMyAidlInterface.getResultAIDL(student)
        binding.sampleText.text = "Roll: ${result.roll}, Result: ${result.status}, Score: ${result.grade}"
    }

    private fun handleStudentFromBundleButtonClick() {
        if (student == null){
            handleCreateStudentButtonClick()
        }
        val bundle = Bundle().apply {
            putParcelable("student", student)
        }
        binding.sampleText.text = "Student from Bundle: ${iMyAidlInterface.createStudentFromBundleAIDL(bundle)}"
    }

    private fun handleArrayListOfStudentsResultButtonClick() {
        val studentList = arrayListOf(
            Student(1, "Chandan Kumar", 24, 95),
            Student(2, "Ranjan Kumar", 21, 90)
        )
        binding.sampleText.text = "Student List: ${iMyAidlInterface.sendStudentsAIDL(studentList)}"
    }

    private fun handleStudentEnumButtonClick() {
        binding.sampleText.text = "Student ENUM: ${iMyAidlInterface.sendStudentEnumAIDL(StudentEnum.SENIOR)}, ${iMyAidlInterface.sendStudentEnumAIDL(StudentEnum.JUNIOR)}"
    }

    private fun handleBasicTypeButtonClick() {
        binding.sampleText.text = "Basic Type: ${iMyAidlInterface.basicTypes(12, 232232L, true, 42.2F, 232.22, "Chandan")}"
    }

}