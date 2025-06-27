package com.example.myapplication.ui.device

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.data.Device
import kotlinx.coroutines.launch

class DeviceFormFragment : Fragment() {
    private val viewModel: DeviceViewModel by viewModels()
    private val args: DeviceFormFragmentArgs by navArgs()
    private var editingDevice: Device? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_device_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editTextName = view.findViewById<EditText>(R.id.editTextDeviceName)
        val editTextType = view.findViewById<EditText>(R.id.editTextDeviceType)
        val editTextAddress = view.findViewById<EditText>(R.id.editTextDeviceAddress)
        val editTextOnCommand = view.findViewById<EditText>(R.id.editTextDeviceOnCommand)
        val editTextOffCommand = view.findViewById<EditText>(R.id.editTextDeviceOffCommand)
        val buttonSave = view.findViewById<Button>(R.id.buttonSaveDevice)

        if (args.deviceId != 0) {
            lifecycleScope.launch {
                val device = viewModel.getDeviceById(args.deviceId).value
                device?.let {
                    editingDevice = it
                    editTextName.setText(it.name)
                    editTextType.setText(it.type)
                    editTextAddress.setText(it.address)
                    editTextOnCommand.setText(it.onCommand)
                    editTextOffCommand.setText(it.offCommand)
                }
            }
        }

        buttonSave.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val type = editTextType.text.toString().trim()
            val address = editTextAddress.text.toString().trim()
            val onCommand = editTextOnCommand.text.toString().trim()
            val offCommand = editTextOffCommand.text.toString().trim()
            if (name.isEmpty() || type.isEmpty() || address.isEmpty()) {
                Toast.makeText(requireContext(), "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val device = Device(
                id = editingDevice?.id ?: 0,
                name = name,
                type = type,
                address = address,
                onCommand = onCommand,
                offCommand = offCommand
            )
            lifecycleScope.launch {
                if (editingDevice == null) {
                    viewModel.insert(device)
                } else {
                    viewModel.update(device)
                }
                findNavController().popBackStack()
            }
        }
    }
} 