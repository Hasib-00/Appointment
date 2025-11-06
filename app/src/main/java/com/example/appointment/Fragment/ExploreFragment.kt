package com.example.appointment.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appointment.Adaptar.SpecialistAdapter
import com.example.appointment.Adaptar.TopDoctorAdapter
import com.example.appointment.Data.SpecialityModel
import com.example.appointment.R
import com.example.appointment.Viewmodel.DoctorViewModel
import com.example.appointment.databinding.FragmentExploreBinding

class ExploreFragment : Fragment() {

    private lateinit var binding: FragmentExploreBinding
    private lateinit var specialityAdapter: SpecialistAdapter
    private var topDoctorAdapter: TopDoctorAdapter? = null
    private val doctorViewModel: DoctorViewModel by activityViewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExploreBinding.inflate(inflater, container, false)
        setupSpecialityRecycler()
        setupDoctorRecycler()
        return binding.root
    }

    private fun setupSpecialityRecycler() {
        val specialityList = listOf(
            SpecialityModel("Cardiology", R.drawable.cardiology),
            SpecialityModel("Dentist", R.drawable.cardiology),
            SpecialityModel("Neurology", R.drawable.cardiology)
        )
        specialityAdapter = SpecialistAdapter(specialityList)
        binding.rvSpeciality.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvSpeciality.adapter = specialityAdapter
    }

    private fun setupDoctorRecycler() {
        doctorViewModel.allDoctors.observe(viewLifecycleOwner) { doctors ->
            if (topDoctorAdapter == null) {
                topDoctorAdapter = TopDoctorAdapter(doctors)
                binding.rvDoctors.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.rvDoctors.adapter = topDoctorAdapter
            } else {
                topDoctorAdapter?.updateList(doctors)
            }
            binding.rvDoctors.visibility = if (doctors.isNotEmpty()) View.VISIBLE else View.GONE
        }
    }
}
