package com.example.appointment.Database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "doctor_table")
data class Doctor(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Basic details
    val name: String = "",
    val specialization: String = "",
    val address: String = "",
    val location: String = "",

    // Doctor info
    val rating: Double = 0.0,
    val year: String = "",
    val patients: Int = 0,
    val experience: Int = 0,
    val biography: String = "",

    // Contact
    val mobile: String = "",
    val site: String = "",

    // Images
    val picture: Int = 0,             // drawable resource fallback
    val imageUri: String? = null      // ✅ added: stores user-selected image URI (from gallery)
) : Serializable
