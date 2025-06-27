package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DeviceDao {
    @Query("SELECT * FROM devices ORDER BY name ASC")
    fun getAllDevices(): LiveData<List<Device>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(device: Device)

    @Update
    suspend fun update(device: Device)

    @Delete
    suspend fun delete(device: Device)

    @Query("SELECT * FROM devices WHERE id = :id")
    suspend fun getDeviceById(id: Int): Device?
} 