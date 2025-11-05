package com.example.appointment.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "doctor_table")
data class Doctor(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val picture: Int = 0,
    val rating: Double = 0.0,
    val year: String = "",
    val specialization: String = "",
    val address: String = "",
    val patients: Int = 0,
    val experience: Int = 0,
    val biography: String = "",
    val mobile: String = "",
    val site: String = "",
    val location: String = ""
)
