package com.example.appointment.Data

import java.io.Serializable

data class TopDoctorModel(
    val Name: String = "",
    val Picture: Int = 0,
    val Rating: Double = 0.0,
    val Year: String = "",
    val Specialization: String = "",
    val Address: String = "",
    val Patients: Int = 0,
    val Experience: Int = 0,
    val Biography: String = "",
    val Mobile: String = "",
    val Site: String = "",
    val Location: String = ""
) : Serializable
