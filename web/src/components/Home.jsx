import { useState } from 'react'
import TaskList from './TaskList'
import './Home.css'

function Home({ tasks, response, onAddTask, onToggleTask, onDeleteTask }) {
  const [input, setInput] = useState('')

  const handleSubmit = (e) => {
    e.preventDefault()
    if (input.trim()) {
      onAddTask(input)
      setInput('')
    }
  }

  return (
    <div className="app-container">
      <div className="header">
        <h1>🤖 MIO</h1>
        <p>Seu assistente pessoal inteligente</p>
      </div>

      <form onSubmit={handleSubmit} className="input-group">
        <input
          type="text"
          value={input}
          onChange={(e) => setInput(e.target.value)}
          placeholder="O que precisa ser feito?"
          className="task-input"
        />
        <button type="submit" className="submit-btn">Adicionar</button>
      </form>

      {response && <div className="response-message">{response}</div>}

      <TaskList 
        tasks={tasks}
        onToggle={onToggleTask}
        onDelete={onDeleteTask}
      />
    </div>
  )
}

export default Home
