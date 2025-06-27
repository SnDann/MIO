package com.example.myapplication.ui.event

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

class EventListFragment : Fragment() {
    private val viewModel: EventViewModel by viewModels()
    private lateinit var adapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewTasks)
        val fab = view.findViewById<FloatingActionButton>(R.id.fabAddTask)

        adapter = EventAdapter(listOf(),
            onEventClick = {
                findNavController().navigate(R.id.action_eventListFragment_to_eventFormFragment)
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.allEvents.observe(viewLifecycleOwner) { events ->
            adapter.updateEvents(events)
        }

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_eventListFragment_to_eventFormFragment)
        }
    }
} 