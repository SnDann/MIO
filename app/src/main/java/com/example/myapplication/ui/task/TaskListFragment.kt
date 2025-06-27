package com.example.myapplication.ui.task

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

class TaskListFragment : Fragment() {
    private val viewModel: TaskViewModel by viewModels()
    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewTasks)
        val fab = view.findViewById<FloatingActionButton>(R.id.fabAddTask)
        val buttonGoToEvents = view.findViewById<View>(R.id.buttonGoToEvents)
        val buttonGoToRecommendations = view.findViewById<View>(R.id.buttonGoToRecommendations)
        val buttonGoToChat = view.findViewById<View>(R.id.buttonGoToChat)

        adapter = TaskAdapter(listOf(),
            onTaskClick = {
                findNavController().navigate(R.id.action_taskListFragment_to_taskFormFragment)
            },
            onTaskChecked = { task, isChecked ->
                val updatedTask = task.copy(isCompleted = isChecked)
                viewModel.update(updatedTask)
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
            adapter.updateTasks(tasks)
        }

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_taskListFragment_to_taskFormFragment)
        }

        buttonGoToEvents.setOnClickListener {
            findNavController().navigate(R.id.action_taskListFragment_to_eventListFragment)
        }

        buttonGoToRecommendations.setOnClickListener {
            findNavController().navigate(R.id.action_taskListFragment_to_recommendationFragment)
        }

        buttonGoToChat.setOnClickListener {
            findNavController().navigate(R.id.action_taskListFragment_to_chatFragment)
        }
    }
} 