package com.example.appointment.Database

/**
 * DoctorRepository
 * -----------------
 * The Repository acts as a middle layer between:
 *    - The database (DoctorDao)
 *    - The ViewModel / UI
 *
 * It abstracts access to multiple data sources and keeps
 * your ViewModel simple and clean.
 *
 * This class handles all data operations (insert, update, delete, getAll).
 */
class DoctorRepository(private val dao: DoctorDao) {

    // ✅ LiveData list of all doctors
    // This is automatically updated whenever the database changes
    val allDoctors = dao.getAllDoctors()

    // ✅ Insert a new doctor (runs in background using coroutines)
    suspend fun insert(doctor: Doctor) = dao.insert(doctor)

    // ✅ Update an existing doctor
    suspend fun update(doctor: Doctor) = dao.update(doctor)

    // ✅ Delete a doctor from the database
    suspend fun delete(doctor: Doctor) = dao.delete(doctor)
}
