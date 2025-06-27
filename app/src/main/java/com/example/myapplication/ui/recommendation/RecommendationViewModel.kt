package com.example.myapplication.ui.recommendation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.Task
import com.example.myapplication.data.Event
import java.util.*

class RecommendationViewModel(application: Application) : AndroidViewModel(application) {
    private val _recommendations = MutableLiveData<List<String>>()
    val recommendations: LiveData<List<String>> = _recommendations

    fun suggestFreeSlots(tasks: List<Task>, events: List<Event>) {
        val calendar = Calendar.getInstance()
        val today = calendar.get(Calendar.YEAR) to calendar.get(Calendar.DAY_OF_YEAR)
        val busyTimes = mutableListOf<Pair<Long, Long>>()

        // Coletar horários ocupados do dia (tarefas e eventos)
        (tasks.filter { isToday(it.dateTime, today) }.map { it.dateTime to (it.dateTime + 60 * 60 * 1000) } +
         events.filter { isToday(it.startDateTime, today) }.map { it.startDateTime to it.endDateTime })
            .forEach { busyTimes.add(it) }

        // Ordenar por início
        busyTimes.sortBy { it.first }

        // Definir limites do dia (exemplo: 08:00 às 22:00)
        calendar.set(Calendar.HOUR_OF_DAY, 8)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val dayStart = calendar.timeInMillis
        calendar.set(Calendar.HOUR_OF_DAY, 22)
        val dayEnd = calendar.timeInMillis

        val freeSlots = mutableListOf<String>()
        var lastEnd = dayStart
        for ((start, end) in busyTimes) {
            if (start > lastEnd) {
                freeSlots.add("Livre das ${formatHour(lastEnd)} às ${formatHour(start)}")
            }
            if (end > lastEnd) lastEnd = end
        }
        if (lastEnd < dayEnd) {
            freeSlots.add("Livre das ${formatHour(lastEnd)} às ${formatHour(dayEnd)}")
        }
        if (freeSlots.isEmpty()) freeSlots.add("Dia cheio, sem intervalos livres.")
        _recommendations.value = freeSlots
    }

    private fun isToday(time: Long, today: Pair<Int, Int>): Boolean {
        val cal = Calendar.getInstance()
        cal.timeInMillis = time
        return cal.get(Calendar.YEAR) == today.first && cal.get(Calendar.DAY_OF_YEAR) == today.second
    }

    private fun formatHour(time: Long): String {
        val cal = Calendar.getInstance()
        cal.timeInMillis = time
        val h = cal.get(Calendar.HOUR_OF_DAY)
        val m = cal.get(Calendar.MINUTE)
        return "%02dh%02d".format(h, m)
    }
} 