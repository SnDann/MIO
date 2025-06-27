package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "devices")
data class Device(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val type: String, // "http" ou "mqtt"
    val address: String, // URL ou tópico
    val onCommand: String,
    val offCommand: String
) 