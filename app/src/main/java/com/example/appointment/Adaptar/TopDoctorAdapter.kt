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

class TopDoctorAdapter(
    private var topDoctorList: List<Doctor>
) : RecyclerView.Adapter<TopDoctorAdapter.TopDoctorViewHolder>() {

    inner class TopDoctorViewHolder(val binding: TopdoctorlistBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopDoctorViewHolder {
        val binding = TopdoctorlistBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TopDoctorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopDoctorViewHolder, position: Int) {
        val doctor = topDoctorList[position]
        holder.binding.tvDoctorName.text = doctor.name
        holder.binding.tvDoctorSpeciality.text = doctor.specialization
        holder.binding.tvDoctorRating.text = doctor.rating.toString()
        holder.binding.yeartv.text = doctor.year

        val imageUri = doctor.imageUri?.let { Uri.parse(it) }
        Glide.with(holder.binding.imgDoctor.context)
            .load(imageUri ?: doctor.picture)
            .placeholder(R.drawable.doctor)
            .error(R.drawable.doctor)
            .into(holder.binding.imgDoctor)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("Object", doctor)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = topDoctorList.size

    fun updateList(newList: List<Doctor>) {
        topDoctorList = newList
        notifyDataSetChanged()
    }
}
