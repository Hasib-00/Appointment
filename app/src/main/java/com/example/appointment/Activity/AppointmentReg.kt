package com.example.appointment.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.appointment.databinding.ActivityAppointmentRegBinding

/**
 * This activity allows a patient to register by entering their
 * name, email, and phone number. The data is validated and saved
 * locally using SharedPreferences so it is remembered next time.
 */
class AppointmentReg : AppCompatActivity() {

    // View binding to access layout views without findViewById()
    private lateinit var binding: ActivityAppointmentRegBinding

    // Constants for SharedPreferences keys
    private val PREFS_NAME = "my_prefs"
    private val KEY_USERNAME = "key_username"
    private val KEY_EMAIL = "key_email"
    private val KEY_NUMBER = "key_number"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // Makes the UI extend behind system bars (modern look)
        binding = ActivityAppointmentRegBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SharedPreferences to store user info locally
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // Load any saved data when the activity opens
        loadSavedData(prefs)

        // Save data automatically when user types something new
        binding.patientNameInputTXT.addTextChangedListener { saveData(prefs) }
        binding.patientEmailInputTXT.addTextChangedListener { saveData(prefs) }
        binding.patientPhoneInputTXT.addTextChangedListener { saveData(prefs) }

        // When the "Register" button is clicked
        binding.patientRegisterBTN.setOnClickListener {
            // Get user input and remove extra spaces
            val username = binding.patientNameInputTXT.text.toString().trim()
            val email = binding.patientEmailInputTXT.text.toString().trim()
            val number = binding.patientPhoneInputTXT.text.toString().trim()

            // Validate user input step by step
            when {
                username.isEmpty() -> toast("Please enter your username")

                username.length < 4 -> toast("Username must be at least 4 characters")

                username.length > 15 -> toast("Username cannot be longer than 15 characters")

                // Allow only letters, numbers, dots, and underscores
                !username.matches(Regex("^[A-Za-z0-9._]+$")) ->
                    toast("Only letters, numbers, underscores, and dots are allowed")

                // Disallow double dots or underscores like ".." or "__"
                username.contains("..") || username.contains("__") ->
                    toast("No consecutive dots or underscores allowed")

                email.isEmpty() -> toast("Please enter your email")

                // Use Android’s built-in email validation pattern
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                    toast("Please enter a valid email")

                number.isEmpty() -> toast("Please enter your phone number")

                // Phone number must have 10–15 digits only
                !number.matches(Regex("^[0-9]{10,15}$")) ->
                    toast("Please enter a valid phone number")

                // If everything is valid
                else -> {
                    saveData(prefs) // Save info in SharedPreferences
                    toast("Signup Successful")

                    // Move to the MainActivity (home screen)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish() // Close current screen
                }
            }
        }
    }

    /**
     * Simple helper function to show a short Toast message.
     */
    private fun toast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    /**
     * Save user input into SharedPreferences.
     * This makes sure data stays saved even after app is closed.
     */
    private fun saveData(prefs: SharedPreferences) {
        prefs.edit().apply {
            putString(KEY_USERNAME, binding.patientNameInputTXT.text.toString().trim())
            putString(KEY_EMAIL, binding.patientEmailInputTXT.text.toString().trim())
            putString(KEY_NUMBER, binding.patientPhoneInputTXT.text.toString().trim())
            apply() // Apply changes asynchronously
        }
    }

    /**
     * Load saved data (if any) and display it in input fields.
     */
    private fun loadSavedData(prefs: SharedPreferences) {
        binding.patientNameInputTXT.setText(prefs.getString(KEY_USERNAME, ""))
        binding.patientEmailInputTXT.setText(prefs.getString(KEY_EMAIL, ""))
        binding.patientPhoneInputTXT.setText(prefs.getString(KEY_NUMBER, ""))
    }
}
