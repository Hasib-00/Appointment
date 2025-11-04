package com.example.appointment.Adaptar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appointment.Data.SpecialityModel
import com.example.appointment.databinding.SpecialitylistBinding


class SpecialistAdapter(private val specialityList: List<SpecialityModel>) :
    RecyclerView.Adapter<SpecialistAdapter.SpecialityViewHolder>() {

    inner class SpecialityViewHolder(val binding: SpecialitylistBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialityViewHolder {
        return SpecialityViewHolder(
            SpecialitylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SpecialityViewHolder, position: Int) {
        val item = specialityList[position]
        holder.binding.specialitytittle.text = item.Name
        holder.binding.circleImageView.setImageResource(item.Image)
    }

    override fun getItemCount() = specialityList.size
}
