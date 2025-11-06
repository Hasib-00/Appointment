package com.example.appointment.Database

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appointment.R
import com.example.appointment.databinding.ListdoctorBinding

/**
 * DoctorAdapter
 * --------------
 * A RecyclerView adapter that displays a list of doctors.
 * Each doctor item shows their name, specialization, and photo.
 * It also provides edit and delete button actions via callbacks.
 */
class DoctorAdapter(
    private var doctorList: List<Doctor>,        // List of doctor objects to display
    val onEdit: (Doctor) -> Unit,                // Lambda function for Edit action
    val onDelete: (Doctor) -> Unit               // Lambda function for Delete action
) : RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    /**
     * ViewHolder
     * ----------
     * Holds the layout for a single list item (doctor card).
     * Uses ViewBinding for direct access to the XML views.
     */
    inner class DoctorViewHolder(val binding: ListdoctorBinding) :
        RecyclerView.ViewHolder(binding.root)

    /**
     * onCreateViewHolder()
     * --------------------
     * Called when RecyclerView needs to create a new ViewHolder.
     * Here we inflate the XML layout for a single doctor card.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val binding = ListdoctorBinding.inflate(
            LayoutInflater.from(parent.context), // Convert XML into View
            parent,
            false
        )
        return DoctorViewHolder(binding)
    }

    /**
     * onBindViewHolder()
     * ------------------
     * Binds the data from a Doctor object to its corresponding item view.
     * This is called automatically for every visible item on screen.
     */
    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = doctorList[position]

        // Set text fields
        holder.binding.tvDoctorName.text = doctor.name
        holder.binding.tvSpeciality.text = doctor.specialization

        // ✅ Load image from URI (if user selected) or fallback to drawable resource
        val imageUri = doctor.imageUri?.let { Uri.parse(it) }
        Glide.with(holder.binding.imgDoctor.context)
            .load(imageUri ?: doctor.picture)
            .placeholder(R.drawable.doctor) // Shown while image loads
            .error(R.drawable.doctor)       // Shown if image fails
            .into(holder.binding.imgDoctor)

        // Handle Edit button click — pass doctor object to onEdit callback
        holder.binding.btnEdit.setOnClickListener { onEdit(doctor) }

        // Handle Delete button click — pass doctor object to onDelete callback
        holder.binding.btnDelete.setOnClickListener { onDelete(doctor) }
    }

    /**
     * getItemCount()
     * ---------------
     * Returns the total number of items in the list.
     */
    override fun getItemCount() = doctorList.size

    /**
     * updateList()
     * -------------
     * Updates the list when data changes (e.g., after adding or deleting a doctor)
     * and refreshes the RecyclerView.
     */
    fun updateList(newList: List<Doctor>) {
        doctorList = newList
        notifyDataSetChanged() // Tells RecyclerView to redraw the list
    }
}
