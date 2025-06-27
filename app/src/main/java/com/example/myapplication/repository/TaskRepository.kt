package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.data.Task
import com.example.myapplication.data.TaskDao

class TaskRepository(private val taskDao: TaskDao) {
    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks()

    suspend fun insert(task: Task) = taskDao.insert(task)
    suspend fun update(task: Task) = taskDao.update(task)
    suspend fun delete(task: Task) = taskDao.delete(task)
    suspend fun getTaskById(id: Int) = taskDao.getTaskById(id)
} 