package com.example.appointment.Adaptar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appointment.Data.SpecialityModel
import com.example.appointment.databinding.SpecialitylistBinding

/**
 * SpecialistAdapter
 * -----------------
 * A RecyclerView adapter that displays a list of doctor specialities (e.g., "Cardiology", "Dermatology").
 * Each item shows a name and an image in a circle (using SpecialitylistBinding layout).
 *
 * RecyclerView = scrollable list
 * Adapter = connects your data to the list view
 */
class SpecialistAdapter(private val specialityList: List<SpecialityModel>) :
    RecyclerView.Adapter<SpecialistAdapter.SpecialityViewHolder>() {

    /**
     * ViewHolder class
     * ----------------
     * Holds the view for each list item (one card or row).
     * We use ViewBinding here to easily access the layout elements.
     */
    inner class SpecialityViewHolder(val binding: SpecialitylistBinding) :
        RecyclerView.ViewHolder(binding.root)

    /**
     * Called when RecyclerView needs to create a new ViewHolder.
     * We inflate (load) the XML layout for each list item here.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialityViewHolder {
        // Inflate layout using the binding class generated from specialitylist.xml
        val binding = SpecialitylistBinding.inflate(
            LayoutInflater.from(parent.context),  // get context from parent
            parent,
            false
        )
        return SpecialityViewHolder(binding)
    }

    /**
     * Called to display data at the given position in the list.
     * Here we set the name and image for each speciality.
     */
    override fun onBindViewHolder(holder: SpecialityViewHolder, position: Int) {
        val item = specialityList[position]  // Get the current speciality

        // Bind the data to UI
        holder.binding.specialitytittle.text = item.Name
        holder.binding.circleImageView.setImageResource(item.Image)
    }

    /**
     * Returns the total number of items in the list.
     */
    override fun getItemCount() = specialityList.size
}
