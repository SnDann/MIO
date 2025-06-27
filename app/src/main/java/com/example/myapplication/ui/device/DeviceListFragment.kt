package com.example.myapplication.ui.device

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.navigation.fragment.findNavController

class DeviceListFragment : Fragment() {
    private val viewModel: DeviceViewModel by viewModels()
    private lateinit var adapter: DeviceListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_device_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewDevices)
        val fab = view.findViewById<FloatingActionButton>(R.id.fabAddDevice)

        adapter = DeviceListAdapter(listOf()) { device ->
            // Navegar para edição
            val action = DeviceListFragmentDirections.actionDeviceListFragmentToDeviceFormFragment(device.id)
            findNavController().navigate(action)
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.allDevices.observe(viewLifecycleOwner) { devices ->
            adapter.updateDevices(devices)
        }

        fab.setOnClickListener {
            // Navegar para cadastro
            val action = DeviceListFragmentDirections.actionDeviceListFragmentToDeviceFormFragment(0)
            findNavController().navigate(action)
        }
    }
} 