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

/**
 * ExploreFragment
 * ----------------
 * This fragment is the “Explore” section of your app.
 * It shows two horizontal lists:
 *   1️⃣ A list of medical specialities (static data)
 *   2️⃣ A list of top doctors (fetched dynamically from Room DB via ViewModel)
 *
 * It uses two RecyclerViews: one for specialities and one for doctors.
 */
class ExploreFragment : Fragment() {

    // ViewBinding gives access to layout elements safely
    private lateinit var binding: FragmentExploreBinding

    // RecyclerView adapters
    private lateinit var specialityAdapter: SpecialistAdapter
    private var topDoctorAdapter: TopDoctorAdapter? = null

    // Shared ViewModel that provides access to doctor data
    private val doctorViewModel: DoctorViewModel by activityViewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using ViewBinding
        binding = FragmentExploreBinding.inflate(inflater, container, false)

        // Set up the two RecyclerViews
        setupSpecialityRecycler()
        setupDoctorRecycler()

        return binding.root
    }

    /**
     * Sets up the speciality RecyclerView with static data.
     * These are pre-defined medical categories like Cardiology, Neurology, etc.
     */
    private fun setupSpecialityRecycler() {
        val specialityList = listOf(
            SpecialityModel("Cardiology", R.drawable.cardiology),
            SpecialityModel("Dentist", R.drawable.cardiology),
            SpecialityModel("Neurology", R.drawable.cardiology)
        )

        // Initialize adapter
        specialityAdapter = SpecialistAdapter(specialityList)

        // Configure RecyclerView (horizontal scrolling)
        binding.rvSpeciality.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvSpeciality.adapter = specialityAdapter
    }

    /**
     * Sets up the top doctors RecyclerView.
     * Observes the LiveData list from the DoctorViewModel and updates the UI automatically.
     */
    private fun setupDoctorRecycler() {
        doctorViewModel.allDoctors.observe(viewLifecycleOwner) { doctors ->
            // Initialize adapter only once
            if (topDoctorAdapter == null) {
                topDoctorAdapter = TopDoctorAdapter(doctors)

                binding.rvDoctors.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

                binding.rvDoctors.adapter = topDoctorAdapter
            } else {
                // If adapter already exists, just update the list
                topDoctorAdapter?.updateList(doctors)
            }

            // Show or hide the list based on data availability
            binding.rvDoctors.visibility = if (doctors.isNotEmpty()) View.VISIBLE else View.GONE
        }
    }
}
