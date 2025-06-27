package com.example.myapplication.ui.task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.Task

class TaskAdapter(
    private var tasks: List<Task>,
    private val onTaskClick: (Task) -> Unit,
    private val onTaskChecked: (Task, Boolean) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBoxCompleted: CheckBox = itemView.findViewById(R.id.checkBoxCompleted)
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewDateTime: TextView = itemView.findViewById(R.id.textViewDateTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.textViewTitle.text = task.title
        holder.textViewDateTime.text = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(java.util.Date(task.dateTime))
        holder.checkBoxCompleted.isChecked = task.isCompleted

        holder.itemView.setOnClickListener { onTaskClick(task) }
        holder.checkBoxCompleted.setOnCheckedChangeListener(null)
        holder.checkBoxCompleted.setOnCheckedChangeListener { _, isChecked ->
            onTaskChecked(task, isChecked)
        }
    }

    override fun getItemCount() = tasks.size

    fun updateTasks(newTasks: List<Task>) {
        this.tasks = newTasks
        notifyDataSetChanged()
    }
} 