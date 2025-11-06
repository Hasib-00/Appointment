package com.example.appointment.Data

/**
 * SpecialityModel
 * ----------------
 * This data class represents a single medical speciality item.
 * Each speciality has:
 *  - A name (e.g., "Cardiology")
 *  - An image (icon or drawable resource ID)
 *
 * It is used by SpecialistAdapter to display a list of specialities
 * in a RecyclerView.
 */
data class SpecialityModel(
    val Name: String = "",  // Name of the speciality (e.g., "Dermatology")
    val Image: Int = 0      // Resource ID for the image (e.g., R.drawable.dermatology_icon)
)
