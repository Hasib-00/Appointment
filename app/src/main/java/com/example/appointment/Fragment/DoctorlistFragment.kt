package com.example.appointment.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appointment.Activity.AddDoctorActivity
import com.example.appointment.Database.DoctorAdapter
import com.example.appointment.Viewmodel.DoctorViewModel
import com.example.appointment.databinding.FragmentDoctorListBinding

/**
 * DoctorListFragment
 * ------------------
 * This fragment displays the list of all doctors stored in the Room database.
 * It observes data from the DoctorViewModel and automatically updates
 * the RecyclerView whenever the database changes.
 *
 * It also allows users to edit or delete doctors using buttons in each list item.
 */
class DoctorListFragment : Fragment() {

    // ViewBinding for this fragment layout
    private var _binding: FragmentDoctorListBinding? = null
    private val binding get() = _binding!!

    // Adapter for displaying doctor list in RecyclerView
    private lateinit var adapter: DoctorAdapter

    // Shared ViewModel (shared with activity, so data persists across fragments)
    private val viewModel: DoctorViewModel by activityViewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using ViewBinding
        _binding = FragmentDoctorListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        // Observe LiveData from ViewModel
        // Whenever doctor data changes, RecyclerView updates automatically
        viewModel.allDoctors.observe(viewLifecycleOwner) { list ->
            adapter.updateList(list)
        }

        // Optional: Add a button for adding new doctors (if your layout includes it)
        // binding.fabAddDoctor.setOnClickListener {
        //     startActivity(Intent(requireContext(), AddDoctorActivity::class.java))
        // }
    }

    /**
     * Initializes the RecyclerView and adapter.
     * Sets up click listeners for Edit and Delete actions.
     */
    private fun setupRecyclerView() {
        adapter = DoctorAdapter(
            emptyList(),
            onEdit = { doctor ->
                // Navigate to AddDoctorActivity with selected doctor for editing
                val intent = Intent(requireContext(), AddDoctorActivity::class.java)
                intent.putExtra("doctor_id", doctor.id)
                startActivity(intent)
            },
            onDelete = { doctor ->
                // Delete doctor using ViewModel
                viewModel.delete(doctor)
            }
        )

        // Configure RecyclerView
        binding.doctorRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.doctorRecyclerView.adapter = adapter
    }

    /**
     * Clears binding reference to avoid memory leaks
     * when the fragment view is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
