package com.example.appointment.Database

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * DoctorDao
 * ----------
 * DAO (Data Access Object) defines all the database operations
 * for the "doctor_table" in Room.
 *
 * It provides methods to insert, update, delete, and fetch doctor records.
 * Room automatically generates the underlying SQL code for these operations.
 */
@Dao
interface DoctorDao {

    /**
     * Insert a new doctor into the database.
     *
     * If there’s a conflict (e.g., same ID), the existing record will be replaced.
     * The `suspend` keyword means it runs asynchronously (in a coroutine),
     * so it won’t block the main UI thread.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(doctor: Doctor)

    /**
     * Update an existing doctor’s information in the database.
     */
    @Update
    suspend fun update(doctor: Doctor)

    /**
     * Delete a specific doctor record from the database.
     */
    @Delete
    suspend fun delete(doctor: Doctor)

    /**
     * Fetch all doctors from the database.
     *
     * The results are sorted by descending ID (newest first).
     * Using LiveData ensures that any UI observing this data
     * automatically updates when the table changes.
     */
    @Query("SELECT * FROM doctor_table ORDER BY id DESC")
    fun getAllDoctors(): LiveData<List<Doctor>>
}
