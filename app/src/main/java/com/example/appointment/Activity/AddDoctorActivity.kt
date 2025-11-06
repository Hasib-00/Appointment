package com.example.appointment.Activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.appointment.Database.Doctor
import com.example.appointment.R
import com.example.appointment.Viewmodel.DoctorViewModel
import com.example.appointment.databinding.ActivityAddDoctorBinding

/**
 * Activity to Add or Edit a Doctor profile.
 * Uses ViewModel to interact with the database.
 */
class AddDoctorActivity : AppCompatActivity() {

    // View binding for easy access to layout views
    private lateinit var binding: ActivityAddDoctorBinding

    // ViewModel instance to access database operations
    private val viewModel: DoctorViewModel by viewModels()

    // Will hold the doctor being edited (if editing)
    private var editDoctor: Doctor? = null

    // Stores the selected image URI (from gallery)
    private var selectedImageUri: Uri? = null

    /**
     * Registers an image picker activity to open the gallery
     * and receive the selected image URI.
     */
    private val imagePicker =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data ?: return@registerForActivityResult
                try {
                    // Persist read permission so the app can access the image later
                    contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                } catch (_: SecurityException) {
                    // Ignore if permission already granted
                }

                // Save and display the selected image
                selectedImageUri = uri
                binding.doctorImageView.setImageURI(uri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using ViewBinding
        binding = ActivityAddDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check if we are editing an existing doctor
        val doctorId = intent.getIntExtra("doctor_id", -1)

        // Observe the list of doctors from the ViewModel
        viewModel.allDoctors.observe(this) { list ->
            // If editing, find the doctor by ID and fill the form
            if (doctorId != -1 && editDoctor == null) {
                editDoctor = list.find { it.id == doctorId }
                editDoctor?.let { fillForm(it) }
            }
        }

        // Set click listener to open image picker
        binding.buttonUploadImage.setOnClickListener { pickImageFromGallery() }

        // Set click listener to save or update doctor information
        binding.buttonSubmitDoctor.setOnClickListener { saveDoctor() }
    }

    /**
     * Opens the gallery to select an image for the doctor.
     */
    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE) // Only show openable files
            type = "image/*"                      // Only show images
        }
        imagePicker.launch(intent)
    }

    /**
     * Fills the form fields with data when editing an existing doctor.
     */
    private fun fillForm(doctor: Doctor) {
        binding.doctorNameEditText.setText(doctor.name)
        binding.doctorSpecializationEditText.setText(doctor.specialization)
        binding.doctorLocationEditText.setText(doctor.location)
        binding.doctorAddressEditText.setText(doctor.address)
        binding.doctorExperienceEditText.setText(doctor.experience.toString())
        binding.doctorBiographyEditText.setText(doctor.biography)
        binding.doctorMobileEditText.setText(doctor.mobile)
        binding.doctorWebsiteEditText.setText(doctor.site)
        binding.doctorPatientsEditText.setText(doctor.patients.toString())

        // Change button text to indicate "Update"
        binding.buttonSubmitDoctor.text = "Update Doctor"

        // If the doctor already has an image, display it
        doctor.imageUri?.let {
            val uri = Uri.parse(it)
            binding.doctorImageView.setImageURI(uri)
            selectedImageUri = uri
        }
    }

    /**
     * Validates input fields and saves (inserts/updates) doctor information in database.
     */
    private fun saveDoctor() {
        val name = binding.doctorNameEditText.text.toString().trim()
        val specialization = binding.doctorSpecializationEditText.text.toString().trim()

        // Basic validation
        if (name.isBlank() || specialization.isBlank()) {
            Toast.makeText(this, "Enter required fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Create a Doctor object with user inputs
        val doctor = Doctor(
            id = editDoctor?.id ?: 0,  // Keep ID if updating, else 0 for new doctor
            name = name,
            // Default picture if user did not choose one
            picture = if (selectedImageUri == null) R.drawable.doctor else 0,
            imageUri = selectedImageUri?.toString(),
            specialization = specialization,
            location = binding.doctorLocationEditText.text.toString(),
            address = binding.doctorAddressEditText.text.toString(),
            experience = binding.doctorExperienceEditText.text.toString().toIntOrNull() ?: 0,
            biography = binding.doctorBiographyEditText.text.toString(),
            mobile = binding.doctorMobileEditText.text.toString(),
            site = binding.doctorWebsiteEditText.text.toString(),
            patients = binding.doctorPatientsEditText.text.toString().toIntOrNull() ?: 0
        )

        // Insert or update doctor in database
        if (editDoctor == null) {
            viewModel.insert(doctor)
            Toast.makeText(this, "Doctor Added!", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.update(doctor)
            Toast.makeText(this, "Doctor Updated!", Toast.LENGTH_SHORT).show()
        }

        // Close the activity and go back
        finish()
    }
}
