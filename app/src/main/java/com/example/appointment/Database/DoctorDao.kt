package com.example.appointment.Database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DoctorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(doctor: Doctor)

    @Update
    suspend fun update(doctor: Doctor)

    @Delete
    suspend fun delete(doctor: Doctor)

    @Query("SELECT * FROM doctor_table ORDER BY id DESC")
    fun getAllDoctors(): LiveData<List<Doctor>>
}
