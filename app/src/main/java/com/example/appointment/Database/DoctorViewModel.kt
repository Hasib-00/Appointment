package com.example.appointment.Viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appointment.Database.*
import kotlinx.coroutines.launch

/**
 * DoctorViewModel
 * ----------------
 * The ViewModel acts as a bridge between the UI (Activities / Fragments)
 * and the Repository (which handles database operations).
 *
 * It stores and manages UI-related data in a lifecycle-conscious way,
 * ensuring that data survives configuration changes (like screen rotation).
 */
class DoctorViewModel(application: Application) : AndroidViewModel(application) {

    // Create repository instance (data manager)
    private val repository: DoctorRepository

    // Expose LiveData list of all doctors for the UI to observe
    val allDoctors = DoctorDatabase.getDatabase(application).doctorDao().getAllDoctors()

    init {
        // Get DAO (Data Access Object) from the Room database
        val dao = DoctorDatabase.getDatabase(application).doctorDao()

        // Initialize the repository with the DAO
        repository = DoctorRepository(dao)
    }

    /**
     * Insert a new doctor into the database.
     * This runs inside viewModelScope (a built-in coroutine scope),
     * meaning it runs in the background thread safely.
     */
    fun insert(doctor: Doctor) = viewModelScope.launch {
        repository.insert(doctor)
    }

    /**
     * Update an existing doctor's information.
     */
    fun update(doctor: Doctor) = viewModelScope.launch {
        repository.update(doctor)
    }

    /**
     * Delete a doctor from the database.
     */
    fun delete(doctor: Doctor) = viewModelScope.launch {
        repository.delete(doctor)
    }
}
