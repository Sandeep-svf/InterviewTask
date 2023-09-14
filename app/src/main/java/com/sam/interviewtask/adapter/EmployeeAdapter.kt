package com.sam.interviewtask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sam.interviewtask.R
import com.sam.interviewtask.R.layout.emp_list_item
import com.sam.interviewtask.model.EmployeeModel

class EmployeeAdapter (private val employeeList: ArrayList<EmployeeModel>):
    RecyclerView.Adapter<EmployeeAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeAdapter.ViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(emp_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EmployeeAdapter.ViewHolder, position: Int) {
        val currentEmployee = employeeList[position]
        holder.tvEmployeeName.text = currentEmployee.employeeName
        holder.tvEmployeephone.text = currentEmployee.employeePhoneNumber
        holder.tvEmployeeAddress.text = currentEmployee.employeeAddress
    }

    override fun getItemCount(): Int {
        return employeeList.size
    }


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val tvEmployeeName : TextView = itemView.findViewById(R.id.tv_emp_name)
        val tvEmployeephone : TextView = itemView.findViewById(R.id.tv_emp_phone)
        val tvEmployeeAddress : TextView = itemView.findViewById(R.id.tv_emp_address)
    }
}