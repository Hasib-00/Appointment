package com.example.appointment.Adaptar

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appointment.Activity.DetailActivity
import com.example.appointment.Database.Doctor
import com.example.appointment.R
import com.example.appointment.databinding.TopdoctorlistBinding

/**
 * TopDoctorAdapter
 * ----------------
 * This adapter displays a list of top doctors in a RecyclerView.
 * Each item shows the doctor's image, name, specialization, rating, and experience year.
 * Clicking on an item opens the detailed view of that doctor.
 */
class TopDoctorAdapter(
    private var topDoctorList: List<Doctor> // List of doctors to display
) : RecyclerView.Adapter<TopDoctorAdapter.TopDoctorViewHolder>() {

    /**
     * ViewHolder class holds the view of each doctor item.
     * Uses ViewBinding to directly access views inside topdoctorlist.xml.
     */
    inner class TopDoctorViewHolder(val binding: TopdoctorlistBinding)
        : RecyclerView.ViewHolder(binding.root)

    /**
     * Called when RecyclerView needs a new ViewHolder to display an item.
     * Inflates the layout for each doctor card.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopDoctorViewHolder {
        val binding = TopdoctorlistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TopDoctorViewHolder(binding)
    }

    /**
     * Binds data from each Doctor object to its corresponding UI components.
     * This runs for each visible item on screen.
     */
    override fun onBindViewHolder(holder: TopDoctorViewHolder, position: Int) {
        val doctor = topDoctorList[position]  // Get the current doctor

        // Bind doctor data to UI elements
        holder.binding.tvDoctorName.text = doctor.name
        holder.binding.tvDoctorSpeciality.text = doctor.specialization
        holder.binding.tvDoctorRating.text = doctor.rating.toString()
        holder.binding.yeartv.text = doctor.year

        // Load the doctor image using Glide (handles URI or drawable safely)
        val imageUri = doctor.imageUri?.let { Uri.parse(it) }
        Glide.with(holder.binding.imgDoctor.context)
            .load(imageUri ?: doctor.picture)     // Prefer imageUri; fallback to drawable
            .placeholder(R.drawable.doctor)       // Shown while loading
            .error(R.drawable.doctor)             // Shown if loading fails
            .into(holder.binding.imgDoctor)

        // Handle click on the item (open DetailActivity)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("Object", doctor)  // Send the Doctor object to detail screen
            holder.itemView.context.startActivity(intent)
        }
    }

    /**
     * Returns the number of doctor items in the list.
     */
    override fun getItemCount(): Int = topDoctorList.size

    /**
     * Optional helper function to update the list dynamically.
     * Useful if you want to refresh the RecyclerView after searching or sorting.
     */
    fun updateList(newList: List<Doctor>) {
        topDoctorList = newList
        notifyDataSetChanged() // Tell RecyclerView that the data has changed
    }
}
