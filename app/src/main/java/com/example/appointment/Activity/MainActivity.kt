package com.example.appointment.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.appointment.Fragment.DoctorListFragment
import com.example.appointment.Fragment.ExploreFragment
import com.example.appointment.Fragment.ProfileFragment
import com.example.appointment.R
import com.example.appointment.databinding.ActivityMainBinding

/**
 * MainActivity
 * -------------
 * This is the main screen of the app after login/intro.
 * It uses a Bottom Navigation Bar to switch between different sections:
 * Explore, Whitelist (favorites), Doctor List, and Profile.
 */
class MainActivity : AppCompatActivity() {

    // ViewBinding for accessing the layout views
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enables full-screen layout (edge-to-edge UI)
        enableEdgeToEdge()

        // Inflate the layout using ViewBinding (connects XML layout to Kotlin)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load the default fragment (home screen)
        replaceFragment(ExploreFragment())

        // Handle bottom navigation item clicks
        binding.navbar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.exploreicone -> replaceFragment(ExploreFragment())      // Home / Discover
                R.id.Doctorsicone -> replaceFragment(DoctorListFragment()) // Doctor directory
                R.id.acoounticone -> replaceFragment(ProfileFragment())     // User profile
            }
            true // Return true to indicate the click was handled
        }
    }

    /**
     * Helper function to switch (replace) fragments on the main screen.
     *
     * @param fragment The fragment you want to show (e.g., ExploreFragment)
     */
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main, fragment) // Replace current fragment with new one
            .commit()                     // Apply the transaction
    }

    // Navbar End
}
