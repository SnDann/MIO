package com.example.myapplication.ui.recommendation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.Task
import com.example.myapplication.data.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecommendationFragment : Fragment() {
    private val viewModel: RecommendationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recommendation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textViewRecommendations = view.findViewById<TextView>(R.id.textViewRecommendations)

        // Carregar tarefas e eventos do banco e sugerir horários livres
        CoroutineScope(Dispatchers.IO).launch {
            val context = requireContext().applicationContext
            val db = AppDatabase.getDatabase(context)
            val tasks = db.taskDao().getAllTasks().value ?: emptyList<Task>()
            val events = db.eventDao().getAllEvents().value ?: emptyList<Event>()
            withContext(Dispatchers.Main) {
                viewModel.suggestFreeSlots(tasks, events)
            }
        }

        viewModel.recommendations.observe(viewLifecycleOwner) { recs ->
            textViewRecommendations.text = if (recs.isNullOrEmpty()) {
                "Nenhuma recomendação disponível."
            } else {
                recs.joinToString("\n")
            }
        }
    }
} 