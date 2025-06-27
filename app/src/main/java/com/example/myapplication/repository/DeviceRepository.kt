package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.data.Device
import com.example.myapplication.data.DeviceDao

class DeviceRepository(private val deviceDao: DeviceDao) {
    val allDevices: LiveData<List<Device>> = deviceDao.getAllDevices()

    suspend fun insert(device: Device) = deviceDao.insert(device)
    suspend fun update(device: Device) = deviceDao.update(device)
    suspend fun delete(device: Device) = deviceDao.delete(device)
    suspend fun getDeviceById(id: Int) = deviceDao.getDeviceById(id)
} 