package com.example.appointment.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appointment.databinding.FragmentWhitelistBinding


class WhitelistFragment : Fragment() {
    private lateinit var binding: FragmentWhitelistBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWhitelistBinding.inflate(inflater, container, false)
        return binding.root
    }
}