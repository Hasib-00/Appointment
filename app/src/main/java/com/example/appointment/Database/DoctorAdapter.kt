package com.example.appointment.Database

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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

        holder.binding.btnEdit.setOnClickListener { onEdit(doctor) }
        holder.binding.btnDelete.setOnClickListener { onDelete(doctor) }
    }

    override fun getItemCount() = doctorList.size

    fun updateList(newList: List<Doctor>) {
        doctorList = newList
        notifyDataSetChanged()
    }
}
