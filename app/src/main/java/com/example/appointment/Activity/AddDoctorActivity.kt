package com.example.appointment.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appointment.Database.*
import com.example.appointment.Viewmodel.DoctorViewModel
import com.example.appointment.databinding.ActivityAddDoctorBinding

class AddDoctorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddDoctorBinding
    private val viewModel: DoctorViewModel by viewModels()
    private var editDoctor: Doctor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val doctorId = intent.getIntExtra("doctor_id", -1)

        viewModel.allDoctors.observe(this) { list ->
            if (doctorId != -1 && editDoctor == null) {
                editDoctor = list.find { it.id == doctorId }
                editDoctor?.let { fillForm(it) }
            }
        }

        binding.buttonSubmitDoctor.setOnClickListener {
            saveDoctor()
        }
    }

    private fun fillForm(doctor: Doctor) {
        binding.doctorNameEditText.setText(doctor.name)
        binding.doctorSpecializationEditText.setText(doctor.specialization)
        binding.doctorLocationEditText.setText(doctor.location)
        binding.doctorAddressEditText.setText(doctor.address)
        binding.doctorExperienceEditText.setText(doctor.experience?.toString())
        binding.doctorBiographyEditText.setText(doctor.biography)
        binding.doctorMobileEditText.setText(doctor.mobile)
        binding.doctorWebsiteEditText.setText(doctor.site)
        binding.doctorPatientsEditText.setText(doctor.patients?.toString())
        binding.buttonSubmitDoctor.text = "Update Doctor"
    }

    private fun saveDoctor() {
        val name = binding.doctorNameEditText.text.toString()
        val specialization = binding.doctorSpecializationEditText.text.toString()

        if (name.isBlank() || specialization.isBlank()) {
            Toast.makeText(this, "Enter required fields", Toast.LENGTH_SHORT).show()
            return
        }

        val doctor = Doctor(
            id = editDoctor?.id ?: 0,
            name = name,
            specialization = specialization,
            location = binding.doctorLocationEditText.text.toString(),
            address = binding.doctorAddressEditText.text.toString(),
            experience = binding.doctorExperienceEditText.text.toString().toIntOrNull() ?: 0,
            biography = binding.doctorBiographyEditText.text.toString(),
            mobile = binding.doctorMobileEditText.text.toString(),
            site = binding.doctorWebsiteEditText.text.toString(),
            patients = binding.doctorPatientsEditText.text.toString().toIntOrNull() ?: 0
        )

        if (editDoctor == null) {
            viewModel.insert(doctor)
            Toast.makeText(this, "Doctor Added!", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.update(doctor)
            Toast.makeText(this, "Doctor Updated!", Toast.LENGTH_SHORT).show()
        }

        finish()
    }
}
