package com.example.appointment.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.appointment.Adaptar.SpecialistAdapter
import com.example.appointment.Fragment.ExploreFragment
import com.example.appointment.Fragment.ProfileFragment
import com.example.appointment.Fragment.SettingsFragment
import com.example.appointment.Fragment.WhitelistFragment
import com.example.appointment.R
import com.example.appointment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inflate the layout via binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Navbar Start

        replaceFragment(ExploreFragment())

        binding.navbar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.exploreicone -> replaceFragment(ExploreFragment())
                R.id.loveicone -> replaceFragment(WhitelistFragment())
                R.id.Settingsicone -> replaceFragment(SettingsFragment())
                R.id.acoounticone -> replaceFragment(ProfileFragment())
            }
            true
        }

    }



    private fun replaceFragment (fragment: Fragment){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main,fragment)
            .commit()
    }




    // Navbar End
}