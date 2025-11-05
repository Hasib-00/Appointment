package com.example.appointment.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.example.appointment.R
import com.example.appointment.databinding.ActivityAppointmentRegBinding

class AppointmentReg : AppCompatActivity() {
    private lateinit var binding: ActivityAppointmentRegBinding
    private val PREFS_NAME = "my_prefs"
    private val KEY_USERNAME = "key_username"
    private val KEY_EMAIL = "key_email"
    private val KEY_NUMBER = "key_number"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAppointmentRegBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // Load saved data
        loadSavedData(prefs)

        // Automatically save data when user types
        binding.patientNameInputTXT.addTextChangedListener { saveData(prefs) }
        binding.patientEmailInputTXT.addTextChangedListener { saveData(prefs) }
        binding.patientPhoneInputTXT.addTextChangedListener { saveData(prefs) }

        // Button click
        binding.patientRegisterBTN.setOnClickListener {
            val username = binding.patientNameInputTXT.text.toString().trim()
            val email = binding.patientEmailInputTXT.text.toString().trim()
            val number = binding.patientPhoneInputTXT.text.toString().trim()

            when {
                username.isEmpty() -> {
                    Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show()
                }
                username.length < 4 -> {
                    Toast.makeText(this, "Username must be at least 4 characters", Toast.LENGTH_SHORT).show()
                }
                username.length > 15 -> {
                    Toast.makeText(this, "Username cannot be longer than 15 characters", Toast.LENGTH_SHORT).show()
                }
                !username.matches(Regex("^[A-Za-z0-9._]+$")) -> {
                    Toast.makeText(this, "Only letters, numbers, underscores, and dots are allowed", Toast.LENGTH_SHORT).show()
                }
                username.contains("..") || username.contains("__") -> {
                    Toast.makeText(this, "No consecutive dots or underscores allowed", Toast.LENGTH_SHORT).show()
                }
                email.isEmpty() -> {
                    Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
                }
                number.isEmpty() -> {
                    Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show()
                }
                !number.matches(Regex("^[0-9]{10,15}$")) -> {
                    Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        // Sign-in click
        binding.patientRegisterBTN.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveData(prefs: SharedPreferences) {
        val username = binding.patientNameInputTXT.text.toString().trim()
        val email = binding.patientEmailInputTXT.text.toString().trim()
        val number = binding.patientPhoneInputTXT.text.toString().trim()

        prefs.edit().apply {
            putString(KEY_USERNAME, username)
            putString(KEY_EMAIL, email)
            putString(KEY_NUMBER, number)
            apply()
        }
    }

    private fun loadSavedData(prefs: SharedPreferences) {
        binding.patientNameInputTXT.setText(prefs.getString(KEY_USERNAME, ""))
        binding.patientEmailInputTXT.setText(prefs.getString(KEY_EMAIL, ""))
        binding.patientPhoneInputTXT.setText(prefs.getString(KEY_NUMBER, ""))
    }
}
