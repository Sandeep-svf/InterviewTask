package com.sam.interviewtask.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employees")
data class Employee(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "employee_name")
    val name: String,
    @ColumnInfo(name = "employee_number")
    val phoneNumber: String,
    @ColumnInfo(name = "employee_address")
    val address: String
)
