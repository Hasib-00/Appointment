package com.example.appointment.Database

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appointment.R
import com.example.appointment.databinding.ListdoctorBinding

class DoctorAdapter(
    private var doctorList: List<Doctor>,
    val onEdit: (Doctor) -> Unit,
    val onDelete: (Doctor) -> Unit
) : RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    inner class DoctorViewHolder(val binding: ListdoctorBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val binding = ListdoctorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DoctorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = doctorList[position]
        holder.binding.tvDoctorName.text = doctor.name
        holder.binding.tvSpeciality.text = doctor.specialization

        // ✅ Load image from URI or use default
        val imageUri = doctor.imageUri?.let { Uri.parse(it) }
        Glide.with(holder.binding.imgDoctor.context)
            .load(imageUri ?: doctor.picture)
            .placeholder(R.drawable.doctor)
            .error(R.drawable.doctor)
            .into(holder.binding.imgDoctor)

        holder.binding.btnEdit.setOnClickListener { onEdit(doctor) }
        holder.binding.btnDelete.setOnClickListener { onDelete(doctor) }
    }

    override fun getItemCount() = doctorList.size

    fun updateList(newList: List<Doctor>) {
        doctorList = newList
        notifyDataSetChanged()
    }
}
