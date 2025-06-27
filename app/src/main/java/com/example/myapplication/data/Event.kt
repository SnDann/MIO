package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String? = null,
    val startDateTime: Long,
    val endDateTime: Long,
    val location: String? = null,
    val hasReminder: Boolean = false
) 