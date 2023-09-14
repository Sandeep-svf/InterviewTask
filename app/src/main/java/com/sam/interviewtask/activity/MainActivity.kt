package com.sam.interviewtask.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.room.Room
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.sam.interviewtask.model.EmployeeModel
import com.sam.interviewtask.R
import com.sam.interviewtask.model.Employee
import com.sam.interviewtask.roomdb.AppDatabase
import com.sam.interviewtask.utility.CheckNetworkConnection

class MainActivity : AppCompatActivity() {

    private lateinit var btnInsertData : AppCompatButton
    private lateinit var btnFetchData : AppCompatButton
    private lateinit var employeeName : AppCompatEditText
    private lateinit var employeePhone : AppCompatEditText
    private lateinit var employeeAddress : AppCompatEditText
    private lateinit var checkNetworkConnection: CheckNetworkConnection
    private var flag : Boolean = true
    private var status : Boolean = false
    private lateinit var arrayListRoomEmpData : ArrayList<Employee>


    private lateinit var dbRef : DatabaseReference

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnInsertData = findViewById(R.id.e_save_button)
        btnFetchData = findViewById(R.id.e_view_button)
        employeeName = findViewById(R.id.e_name)
        employeePhone = findViewById(R.id.e_number)
        employeeAddress = findViewById(R.id.e_address)

        dbRef = FirebaseDatabase.getInstance().getReference("Employee")


        btnFetchData.setOnClickListener(){
            startActivity(Intent(this@MainActivity,FetchingActivity::class.java))

        }

            btnInsertData.setOnClickListener {


                // validation
                val employeeNameData = employeeName.text.toString()
                val employeePhoneData = employeePhone.text.toString()
                val employeeAddressData = employeeAddress.text.toString()

                if(employeeNameData.isEmpty()){
                    employeeName.error = "This field can't be empty"
                    return@setOnClickListener
                }

                if(employeePhoneData.isEmpty()){
                    employeePhone.error = "This field can't be empty"
                    return@setOnClickListener
                }

                if(employeeAddressData.isEmpty()){
                    employeePhone.error = "This field can't be empty"
                    return@setOnClickListener
                }


                /* flag = true*/
                callNetworkConnection()
                // Initializing room db
                val database = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java,
                    "employee_database"
                ).build()
                if(status){


                    saveEmployeeData()
                    Toast.makeText(this,"Network is available", Toast.LENGTH_LONG).show()
                    Log.i("Sam_test","Calling A")

                    Thread{
                        val databaseFile = getDatabasePath("employee_database")
                        if (databaseFile.exists()) {
                            database.employeeDao().getAllEmployees().forEach {
                                Log.i("Sam_test", "Id is: ${it.id}")
                                Log.i("Sam_test", "Id is: ${it.name}")
                                Log.i("Sam_test", "Id is: ${it.phoneNumber}")
                                Log.i("Sam_test", "Id is: ${it.address}")

                                val employeeId = dbRef.push().key!!

                                val employee = EmployeeModel(it.id.toString(),it.name.toString(),it.phoneNumber.toString(),it.address.toString())

                                dbRef.child(employeeId).setValue(employee)
                                    .addOnCompleteListener{
                                        Toast.makeText(this,"Data inserted successfully", Toast.LENGTH_LONG).show()
                                        flag = false
                                    }   .addOnFailureListener{err ->
                                        Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_LONG).show()

                                    }
                            }
                        } else {
                            // Database file doesn't exist
                        }

                        // delete data from room db
                        // delete data for avoid
                        database.employeeDao().deleteAll()




                    }.start()


                }else{
                    Log.i("Sam_test", "Calling B")

                    Toast.makeText(this, "Network is not available", Toast.LENGTH_LONG).show()



                    // getting values form user
                    val employeeNameData = employeeName.text.toString()
                    val employeePhoneData = employeePhone.text.toString()
                    val employeeAddress = employeeAddress.text.toString()

                    // Insert an employee
                    Thread {
                        val employee = Employee(
                            name = employeeNameData,
                            phoneNumber = employeePhoneData,
                            address = employeeAddress
                        )
                        database.employeeDao().insertEmployee(employee)


                    }.start()
                }



        }


    }

    private fun callNetworkConnection(){
        checkNetworkConnection = CheckNetworkConnection(application)
        checkNetworkConnection.observe(this) { isConnected ->
            if (isConnected) {
                status = true

            } else {
                /*// Fetch all employees
                val employees = database.employeeDao().getAllEmployees()*/
                status = false

            }
        }

    }
    private fun saveEmployeeData() {

        // getting values form user
        val employeeNameData = employeeName.text.toString()
        val employeePhoneData = employeePhone.text.toString()
        val employeeAddress = employeeAddress.text.toString()


        val employeeId = dbRef.push().key!!

        val employee = EmployeeModel(employeeId,employeeNameData,employeePhoneData,employeeAddress)

        dbRef.child(employeeId).setValue(employee)
            .addOnCompleteListener{
            Toast.makeText(this,"Data inserted successfully", Toast.LENGTH_LONG).show()
                flag = false
        }   .addOnFailureListener{err ->
                Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_LONG).show()

            }



    }


    private fun validation(): Boolean {

        // getting values form user
        val employeeNameData = employeeName.text.toString()
        val employeePhoneData = employeePhone.text.toString()
        val employeeAddress = employeeAddress.text.toString()

        if(employeeName.text.toString().trim().isEmpty()){
            Toast.makeText(this, "Please enter employee name", Toast.LENGTH_SHORT)
            return false
        }else if(employeePhone.text.toString().trim().isEmpty()){
            Toast.makeText(this, "Please enter employee phone number", Toast.LENGTH_SHORT)
            return false
        }else if(employeeAddress.toString().trim().isEmpty()){
            Toast.makeText(this, "Please enter employee address", Toast.LENGTH_SHORT)
            return false
        }

        return true;
    }
}