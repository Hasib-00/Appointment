package com.example.appointment.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appointment.databinding.FragmentWhitelistBinding

/**
 * WhitelistFragment
 * ------------------
 * This fragment represents the “Favorites” or “Whitelist” section of your app.
 * You can later use it to display a list of favorite or saved doctors.
 *
 * For now, it simply inflates its layout and prepares the ViewBinding.
 */
class WhitelistFragment : Fragment() {

    // ViewBinding gives type-safe access to the layout’s views
    private lateinit var binding: FragmentWhitelistBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using ViewBinding
        binding = FragmentWhitelistBinding.inflate(inflater, container, false)

        // Return the root view (the base of this fragment’s UI)
        return binding.root
    }
}
