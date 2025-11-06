package com.example.appointment.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appointment.Activity.AddDoctorActivity
import com.example.appointment.databinding.FragmentProfileBinding

/**
 * ProfileFragment
 * ----------------
 * This fragment represents the “Profile” section of your app.
 * It can later display the user’s personal info (e.g., name, email, appointments).
 *
 * For now, it simply contains a button that opens the AddDoctorActivity
 * so the user can add a new doctor.
 */
class ProfileFragment : Fragment() {

    // ViewBinding instance to safely access layout views
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using ViewBinding
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        // Handle button click: go to AddDoctorActivity
        binding.button.setOnClickListener {
            val intent = Intent(requireContext(), AddDoctorActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}
