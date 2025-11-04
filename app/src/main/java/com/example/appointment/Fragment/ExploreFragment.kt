package com.example.appointment.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appointment.Adaptar.SpecialistAdapter
import com.example.appointment.Data.SpecialityModel
import com.example.appointment.R
import com.example.appointment.databinding.FragmentExploreBinding


class ExploreFragment : Fragment() {

    private lateinit var binding: FragmentExploreBinding
    private lateinit var specialityAdapter: SpecialistAdapter
    private lateinit var mylist: List<SpecialityModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExploreBinding.inflate(inflater, container, false)

        // Load data
        loaddata()

        // Initialize adapter
        specialityAdapter = SpecialistAdapter(mylist)

        // Setup RecyclerView
        binding.rvSpeciality.apply {
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false
            )
            adapter = specialityAdapter
        }

        return binding.root
    }

    private fun loaddata() {
        mylist = listOf(
            SpecialityModel("Md Hasib", R.drawable.cardiology),
            SpecialityModel("Md Hasib", R.drawable.cardiology),
            SpecialityModel("Md Hasib", R.drawable.cardiology),
            SpecialityModel("Md Hasib", R.drawable.cardiology),
            SpecialityModel("Md Hasib", R.drawable.cardiology),
            SpecialityModel("Md Hasib", R.drawable.cardiology),
            SpecialityModel("Md Hasib", R.drawable.cardiology),
            SpecialityModel("Md Hasib", R.drawable.cardiology),

        )
    }
}
