package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.data.Event
import com.example.myapplication.data.EventDao

class EventRepository(private val eventDao: EventDao) {
    val allEvents: LiveData<List<Event>> = eventDao.getAllEvents()

    suspend fun insert(event: Event) = eventDao.insert(event)
    suspend fun update(event: Event) = eventDao.update(event)
    suspend fun delete(event: Event) = eventDao.delete(event)
    suspend fun getEventById(id: Int) = eventDao.getEventById(id)
} 