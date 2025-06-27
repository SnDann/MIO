package com.example.myapplication.ui.device

import android.app.Application
import androidx.lifecycle.*
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.Device
import com.example.myapplication.repository.DeviceRepository
import kotlinx.coroutines.launch

class DeviceViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: DeviceRepository
    val allDevices: LiveData<List<Device>>

    init {
        val deviceDao = AppDatabase.getDatabase(application).deviceDao()
        repository = DeviceRepository(deviceDao)
        allDevices = repository.allDevices
    }

    fun insert(device: Device) = viewModelScope.launch { repository.insert(device) }
    fun update(device: Device) = viewModelScope.launch { repository.update(device) }
    fun delete(device: Device) = viewModelScope.launch { repository.delete(device) }
    fun getDeviceById(id: Int) = liveData { emit(repository.getDeviceById(id)) }
} 