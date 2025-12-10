function TaskList({ tasks, onToggle, onDelete }) {
  if (tasks.length === 0) {
    return <div className="empty-state">Nenhuma tarefa ainda. Crie sua primeira!</div>
  }

  return (
    <div className="task-list">
      {tasks.map(task => (
        <div key={task.id} className={`task-item ${task.completed ? 'completed' : ''}`}>
          <input
            type="checkbox"
            checked={task.completed}
            onChange={() => onToggle(task.id)}
            className="task-checkbox"
          />
          <div className="task-content">
            <p className="task-text">{task.text}</p>
            <small className="task-date">{task.date}</small>
          </div>
          <button
            onClick={() => onDelete(task.id)}
            className="delete-btn"
          >
            ×
          </button>
        </div>
      ))}
    </div>
  )
}

export default TaskList
