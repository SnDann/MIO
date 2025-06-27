package com.example.myapplication.ui.device

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.Device

class DeviceListAdapter(
    private var devices: List<Device>,
    private val onDeviceClick: (Device) -> Unit
) : RecyclerView.Adapter<DeviceListAdapter.DeviceViewHolder>() {

    inner class DeviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return DeviceViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val device = devices[position]
        holder.textViewName.text = device.name
        holder.itemView.setOnClickListener { onDeviceClick(device) }
    }

    override fun getItemCount() = devices.size

    fun updateDevices(newDevices: List<Device>) {
        this.devices = newDevices
        notifyDataSetChanged()
    }
} 