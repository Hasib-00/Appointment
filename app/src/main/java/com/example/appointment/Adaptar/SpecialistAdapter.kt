package com.example.appointment.Adaptar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appointment.Data.SpecialityModel
import com.example.appointment.databinding.SpecialitylistBinding

class SpecialistAdapter (private val specialityList: List<SpecialityModel>) :
    RecyclerView.Adapter<SpecialistAdapter.SpecialityViewHolder>(){
    inner class SpecialityViewHolder(val binding: SpecialitylistBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SpecialistAdapter.SpecialityViewHolder {
        val binding = SpecialitylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpecialityViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SpecialistAdapter.SpecialityViewHolder,
        position: Int
    ) {
        val circleimg = specialityList[position]
        holder.binding.specialitytittle.text = circleimg.Name
        holder.binding.circleImageView.setImageResource(circleimg.Imgae)
    }

    override fun getItemCount(): Int {
        return specialityList.size

    }
}