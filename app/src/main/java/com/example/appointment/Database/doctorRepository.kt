package com.example.appointment.Database

class DoctorRepository(private val dao: DoctorDao) {

    val allDoctors = dao.getAllDoctors()

    suspend fun insert(doctor: Doctor) = dao.insert(doctor)
    suspend fun update(doctor: Doctor) = dao.update(doctor)
    suspend fun delete(doctor: Doctor) = dao.delete(doctor)
}
