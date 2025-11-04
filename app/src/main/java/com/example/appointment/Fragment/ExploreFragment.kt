package com.example.appointment.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appointment.Adaptar.SpecialistAdapter
import com.example.appointment.Adaptar.TopDoctorAdapter
import com.example.appointment.Data.SpecialityModel
import com.example.appointment.Data.TopDoctorModel
import com.example.appointment.R
import com.example.appointment.databinding.FragmentExploreBinding

class ExploreFragment : Fragment() {

    private lateinit var binding: FragmentExploreBinding
    private lateinit var specialityAdapter: SpecialistAdapter
    private lateinit var topDoctorAdapter: TopDoctorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExploreBinding.inflate(inflater, container, false)

        val specialityList = listOf(
            SpecialityModel("Cardiology", R.drawable.cardiology),
            SpecialityModel("Dentist", R.drawable.cardiology),
            SpecialityModel("Neurology", R.drawable.cardiology)
        )

        val doctorList = listOf(
            TopDoctorModel(
                Name = "Dr. John Smith",
                Picture = R.drawable.doctor,
                Rating = 4.9,
                Year = "5 yrs",
                Specialization = "Cardiologist",
                Address = "123 Medical Street, Paris",
                Patients = 1500,
                Experience = 5,
                Biography = "Expert heart specialist with 5+ years experience.",
                Mobile = "+18001234567",
                Site = "https://doctorjohn.com",
                Location = "geo:48.8566,2.3522"
            ),
            TopDoctorModel(
                Name = "Dr. Emily Rose",
                Picture = R.drawable.doctor,
                Rating = 4.7,
                Year = "7 yrs",
                Specialization = "Dermatologist",
                Address = "456 Skin Care Ave, Paris",
                Patients = 1200,
                Experience = 7,
                Biography = "Specialist in advanced skin treatments.",
                Mobile = "+18009876543",
                Site = "https://dremily.com",
                Location = "geo:48.8666,2.3522"
            ),
            TopDoctorModel(
                Name = "Dr. Alex Carter",
                Picture = R.drawable.doctor,
                Rating = 4.6,
                Year = "4 yrs",
                Specialization = "Neurologist",
                Address = "789 Brain Health Rd, Paris",
                Patients = 900,
                Experience = 4,
                Biography = "Neurology specialist focused on patient care.",
                Mobile = "+18001112233",
                Site = "https://dralex.com",
                Location = "geo:48.8466,2.3522"
            )
        )

        specialityAdapter = SpecialistAdapter(specialityList)
        topDoctorAdapter = TopDoctorAdapter(doctorList)

        binding.rvSpeciality.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvSpeciality.adapter = specialityAdapter

        binding.rvDoctors.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvDoctors.adapter = topDoctorAdapter

        return binding.root
    }
}
