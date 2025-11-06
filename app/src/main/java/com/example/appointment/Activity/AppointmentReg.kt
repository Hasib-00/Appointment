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
        loadSavedData(prefs)

        binding.patientNameInputTXT.addTextChangedListener { saveData(prefs) }
        binding.patientEmailInputTXT.addTextChangedListener { saveData(prefs) }
        binding.patientPhoneInputTXT.addTextChangedListener { saveData(prefs) }

        binding.patientRegisterBTN.setOnClickListener {
            val username = binding.patientNameInputTXT.text.toString().trim()
            val email = binding.patientEmailInputTXT.text.toString().trim()
            val number = binding.patientPhoneInputTXT.text.toString().trim()

            when {
                username.isEmpty() -> toast("Please enter your username")
                username.length < 4 -> toast("Username must be at least 4 characters")
                username.length > 15 -> toast("Username cannot be longer than 15 characters")
                !username.matches(Regex("^[A-Za-z0-9._]+$")) -> toast("Only letters, numbers, underscores, and dots are allowed")
                username.contains("..") || username.contains("__") -> toast("No consecutive dots or underscores allowed")
                email.isEmpty() -> toast("Please enter your email")
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> toast("Please enter a valid email")
                number.isEmpty() -> toast("Please enter your phone number")
                !number.matches(Regex("^[0-9]{10,15}$")) -> toast("Please enter a valid phone number")
                else -> {
                    saveData(prefs)
                    toast("Signup Successful")
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
    }

    private fun toast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    private fun saveData(prefs: SharedPreferences) {
        prefs.edit().apply {
            putString(KEY_USERNAME, binding.patientNameInputTXT.text.toString().trim())
            putString(KEY_EMAIL, binding.patientEmailInputTXT.text.toString().trim())
            putString(KEY_NUMBER, binding.patientPhoneInputTXT.text.toString().trim())
            apply()
        }
    }

    private fun loadSavedData(prefs: SharedPreferences) {
        binding.patientNameInputTXT.setText(prefs.getString(KEY_USERNAME, ""))
        binding.patientEmailInputTXT.setText(prefs.getString(KEY_EMAIL, ""))
        binding.patientPhoneInputTXT.setText(prefs.getString(KEY_NUMBER, ""))
    }
}
