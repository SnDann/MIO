import { useState, useEffect } from 'react'
import Home from './components/Home'
import './App.css'

function App() {
  const [tasks, setTasks] = useState([])
  const [response, setResponse] = useState('')

  useEffect(() => {
    const saved = localStorage.getItem('mio_tasks')
    if (saved) setTasks(JSON.parse(saved))
  }, [])

  useEffect(() => {
    localStorage.setItem('mio_tasks', JSON.stringify(tasks))
  }, [tasks])

  const handleAddTask = (text) => {
    const newTask = { id: Date.now(), text, completed: false, date: new Date().toLocaleString('pt-BR') }
    setTasks([newTask, ...tasks])
    setResponse('Tarefa adicionada com sucesso! ✓')
    setTimeout(() => setResponse(''), 2000)
  }

  const handleToggleTask = (id) => {
    setTasks(tasks.map(t => t.id === id ? { ...t, completed: !t.completed } : t))
  }

  const handleDeleteTask = (id) => {
    setTasks(tasks.filter(t => t.id !== id))
  }

  return (
    <Home 
      tasks={tasks} 
      response={response} 
      onAddTask={handleAddTask}
      onToggleTask={handleToggleTask}
      onDeleteTask={handleDeleteTask}
    />
  )
}

export default App
