package com.sam.interviewtask.dao

import androidx.room.*
import com.sam.interviewtask.model.Employee

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employees")
    fun getAllEmployees(): List<Employee>

    @Insert
    fun insertEmployee(employee: Employee)

    @Delete
    fun deleteEmployee(employee: Employee)

    @Query("DELETE FROM employees")
    fun deleteAll()


}
