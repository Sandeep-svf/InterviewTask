package com.sam.interviewtask.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.sam.interviewtask.R
import com.sam.interviewtask.adapter.EmployeeAdapter
import com.sam.interviewtask.model.EmployeeModel

class FetchingActivity : AppCompatActivity() {

    private lateinit var rcvEmployeeList : RecyclerView
    private lateinit var backArrow : AppCompatImageView
    private lateinit var tvLoadingData : AppCompatTextView
    private lateinit var employeeList : ArrayList<EmployeeModel>
    private lateinit var dbRef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        rcvEmployeeList = findViewById(R.id.rcv_employee_list)
        backArrow = findViewById(R.id.back_arrow)
        tvLoadingData = findViewById(R.id.tv_loading_data)

        rcvEmployeeList.layoutManager = LinearLayoutManager(this)

        // Initializing Arraylist
        employeeList = arrayListOf<EmployeeModel>()

        getEmployeeData()

        backArrow.setOnClickListener(){
            finish()
        }

    }

    private fun getEmployeeData() {
        rcvEmployeeList.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Employee")
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                employeeList.clear()

                if(snapshot.exists()){
                    for(employeeSnap in snapshot.children){
                        val employeeData = employeeSnap.getValue(EmployeeModel::class.java)
                        employeeList.add(employeeData!!)
                    }
                    val employeeAdapter = EmployeeAdapter(employeeList)
                    rcvEmployeeList.adapter = employeeAdapter

                    rcvEmployeeList.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}