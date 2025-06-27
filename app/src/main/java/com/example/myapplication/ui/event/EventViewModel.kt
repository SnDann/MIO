package com.example.myapplication.ui.event

import android.app.Application
import androidx.lifecycle.*
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.Event
import com.example.myapplication.repository.EventRepository
import kotlinx.coroutines.launch

class EventViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: EventRepository
    val allEvents: LiveData<List<Event>>

    init {
        val eventDao = AppDatabase.getDatabase(application).eventDao()
        repository = EventRepository(eventDao)
        allEvents = repository.allEvents
    }

    fun insert(event: Event) = viewModelScope.launch { repository.insert(event) }
    fun update(event: Event) = viewModelScope.launch { repository.update(event) }
    fun delete(event: Event) = viewModelScope.launch { repository.delete(event) }
    fun getEventById(id: Int) = liveData { emit(repository.getEventById(id)) }
} 