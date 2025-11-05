package com.example.appointment.Activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appointment.R
import com.example.appointment.databinding.ActivityAddDoctorBinding

class AddDoctorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddDoctorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}