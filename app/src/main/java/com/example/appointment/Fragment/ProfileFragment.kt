package com.example.appointment.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appointment.Activity.AddDoctorActivity
import com.example.appointment.Activity.AppointmentReg

import com.example.appointment.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)


        binding.button.setOnClickListener {
            val intent = Intent(requireContext(), AddDoctorActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}
