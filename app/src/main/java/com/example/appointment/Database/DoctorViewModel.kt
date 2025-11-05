package com.example.appointment.Viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appointment.Database.*
import kotlinx.coroutines.launch

class DoctorViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DoctorRepository
    val allDoctors = DoctorDatabase.getDatabase(application).doctorDao().getAllDoctors()

    init {
        val dao = DoctorDatabase.getDatabase(application).doctorDao()
        repository = DoctorRepository(dao)
    }

    fun insert(doctor: Doctor) = viewModelScope.launch { repository.insert(doctor) }
    fun update(doctor: Doctor) = viewModelScope.launch { repository.update(doctor) }
    fun delete(doctor: Doctor) = viewModelScope.launch { repository.delete(doctor) }
}
