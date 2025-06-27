package com.example.myapplication.ui.event

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.Event

class EventAdapter(
    private var events: List<Event>,
    private val onEventClick: (Event) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewEventTitle)
        val textViewTime: TextView = itemView.findViewById(R.id.textViewEventTime)
        val textViewLocation: TextView = itemView.findViewById(R.id.textViewEventLocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.textViewTitle.text = event.title
        holder.textViewTime.text =
            "${java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(java.util.Date(event.startDateTime))} - ${java.text.SimpleDateFormat("HH:mm").format(java.util.Date(event.endDateTime))}"
        holder.textViewLocation.text = event.location ?: ""
        holder.itemView.setOnClickListener { onEventClick(event) }
    }

    override fun getItemCount() = events.size

    fun updateEvents(newEvents: List<Event>) {
        this.events = newEvents
        notifyDataSetChanged()
    }
} 