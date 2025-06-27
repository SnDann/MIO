package com.example.myapplication.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class ChatAdapter(private val messages: List<Pair<String, String>>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_message, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val (question, answer) = messages[position]
        holder.textViewUser.text = "Você: $question"
        holder.textViewAI.text = "Assistente: $answer"
    }

    override fun getItemCount() = messages.size

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewUser: TextView = itemView.findViewById(R.id.textViewUser)
        val textViewAI: TextView = itemView.findViewById(R.id.textViewAI)
    }
} 