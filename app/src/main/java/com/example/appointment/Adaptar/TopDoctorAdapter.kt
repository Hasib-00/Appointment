package com.example.appointment.Adaptar

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appointment.Activity.DetailActivity
import com.example.appointment.Data.TopDoctorModel
import com.example.appointment.databinding.TopdoctorlistBinding

class TopDoctorAdapter(
    private val topDoctorList: List<TopDoctorModel>
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
        val item = topDoctorList[position]

        holder.binding.tvDoctorName.text = item.Name
        holder.binding.tvDoctorSpeciality.text = item.Specialization
        holder.binding.tvDoctorRating.text = item.Rating.toString()
        holder.binding.yeartv.text = item.Year
        holder.binding.imgDoctor.setImageResource(item.Picture)

        // ✅ Click → Open Details Screen
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("Object", item)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = topDoctorList.size
}
