package hu.ait.todorecyclerviewdemo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import hu.ait.todorecyclerviewdemo.data.AppDatabase
import hu.ait.todorecyclerviewdemo.data.Todo
import hu.ait.todorecyclerviewdemo.data.TodoDAO


class TodoViewModel(application: Application) :
    AndroidViewModel(application) {

    val allTodos: LiveData<List<Todo>>

    private var todoDAO: TodoDAO

    init {
        todoDAO = AppDatabase.getInstance(application).
          todoDao()
        allTodos = todoDAO.getAllTodo()
    }

    fun insertTodo(todo: Todo)  {
        Thread {
            todoDAO.addTodo(todo)
        }.start()
    }

    fun deleteTodo(todo: Todo)  {
        Thread {
            todoDAO.deleteTodo(todo)
        }.start()
    }

    fun deleteAllTodos()  {
        Thread {
            todoDAO.deleteAllTodos()
        }.start()
    }

    fun updateTodo(todo: Todo) {
        Thread {
            todoDAO.updateTodo(todo)
        }.start()
    }
}