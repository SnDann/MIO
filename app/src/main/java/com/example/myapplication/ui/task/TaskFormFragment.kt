package com.example.myapplication.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.data.Task
import com.example.myapplication.ai.OpenAIService
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.launch

class TaskFormFragment : Fragment() {
    private val viewModel: TaskViewModel by viewModels()
    private var editingTask: Task? = null
    private val openAIService = OpenAIService("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")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editTextTitle = view.findViewById<EditText>(R.id.editTextTitle)
        val editTextDescription = view.findViewById<EditText>(R.id.editTextDescription)
        val buttonDateTime = view.findViewById<Button>(R.id.buttonDateTime)
        val switchReminder = view.findViewById<Switch>(R.id.switchReminder)
        val buttonSave = view.findViewById<Button>(R.id.buttonSave)
        val buttonAskAISuggestion = view.findViewById<Button>(R.id.buttonAskAISuggestion)

        var selectedDateTime: Long = System.currentTimeMillis()
        buttonDateTime.text = SimpleDateFormat("dd/MM/yyyy HH:mm").format(Date(selectedDateTime))

        buttonDateTime.setOnClickListener {
            // Exemplo simples: apenas incrementa 1h para simular seleção
            selectedDateTime += 60 * 60 * 1000
            buttonDateTime.text = SimpleDateFormat("dd/MM/yyyy HH:mm").format(Date(selectedDateTime))
        }

        // Verifica se está editando (pode ser melhorado com argumentos de navegação)
        // Aqui, sempre cria nova tarefa
        buttonSave.setOnClickListener {
            val title = editTextTitle.text.toString().trim()
            val description = editTextDescription.text.toString().trim()
            val hasReminder = switchReminder.isChecked
            if (title.isEmpty()) {
                Toast.makeText(requireContext(), "Título obrigatório", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val task = Task(
                id = editingTask?.id ?: 0,
                title = title,
                description = description,
                dateTime = selectedDateTime,
                isCompleted = editingTask?.isCompleted ?: false,
                hasReminder = hasReminder
            )
            lifecycleScope.launch {
                if (editingTask == null) {
                    viewModel.insert(task)
                } else {
                    viewModel.update(task)
                }
                findNavController().popBackStack()
            }
        }

        buttonAskAISuggestion.setOnClickListener {
            buttonAskAISuggestion.isEnabled = false
            buttonAskAISuggestion.text = "Consultando IA..."
            openAIService.getCompletion("Sugira um título de tarefa para hoje.") { resposta ->
                requireActivity().runOnUiThread {
                    editTextTitle.setText(resposta ?: "Sugestão da IA")
                    buttonAskAISuggestion.isEnabled = true
                    buttonAskAISuggestion.text = "Sugestão da IA"
                }
            }
        }
    }
} 