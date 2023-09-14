package com.sam.interviewtask.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sam.interviewtask.dao.EmployeeDao
import com.sam.interviewtask.model.Employee

@Database(entities = [Employee::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
}
