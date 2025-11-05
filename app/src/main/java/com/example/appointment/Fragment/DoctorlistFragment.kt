package com.example.appointment.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appointment.Activity.AddDoctorActivity
import com.example.appointment.Database.DoctorAdapter
import com.example.appointment.Viewmodel.DoctorViewModel
import com.example.appointment.databinding.FragmentDoctorListBinding

class DoctorListFragment : Fragment() {

    private var _binding: FragmentDoctorListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: DoctorAdapter
    private val viewModel: DoctorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoctorListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.allDoctors.observe(viewLifecycleOwner) { list ->
            adapter.updateList(list)
        }


    }

    private fun setupRecyclerView() {
        adapter = DoctorAdapter(
            emptyList(),
            onEdit = { doctor ->
                val intent = Intent(requireContext(), AddDoctorActivity::class.java)
                intent.putExtra("doctor_id", doctor.id)
                startActivity(intent)
            },
            onDelete = { doctor ->
                viewModel.delete(doctor)
            }
        )

        binding.doctorRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.doctorRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
