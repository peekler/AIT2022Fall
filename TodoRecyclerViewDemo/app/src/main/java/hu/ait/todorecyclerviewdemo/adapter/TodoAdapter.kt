package hu.ait.todorecyclerviewdemo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.ait.todorecyclerviewdemo.ScrollingActivity
import hu.ait.todorecyclerviewdemo.data.AppDatabase
import hu.ait.todorecyclerviewdemo.data.Todo
import hu.ait.todorecyclerviewdemo.databinding.TodoRowBinding
import hu.ait.todorecyclerviewdemo.touch.TodoTouchHelperCallback
import hu.ait.todorecyclerviewdemo.viewmodel.TodoViewModel
import java.util.*

class TodoAdapter(
    private val context: Context,
    private val todosViewModel: TodoViewModel
) : ListAdapter<Todo, TodoAdapter.ViewHolder>(TodoDiffCallback()),
    TodoTouchHelperCallback{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val todoRowBinding = TodoRowBinding.inflate(
            LayoutInflater.from(context),
            parent, false
        )
        return ViewHolder(todoRowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTodo = getItem(holder.adapterPosition)
        holder.bind(currentTodo)
    }


    fun deleteLast() {
        val lastTodo = getItem(currentList.lastIndex)
        todosViewModel.deleteTodo(lastTodo)
    }


    override fun onDismissed(position: Int) {
        todosViewModel.deleteTodo(getItem(position))
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        val tmpList = mutableListOf<Todo>()
        tmpList.addAll(currentList)
        Collections.swap(tmpList, fromPosition, toPosition)

        submitList(tmpList)
    }

    inner class ViewHolder(private val todoRowBinding: TodoRowBinding) :
        RecyclerView.ViewHolder(todoRowBinding.root) {

        fun bind(todo: Todo) {
            todoRowBinding.cbTodoDone.text = todo.todoTitle
            todoRowBinding.cbTodoDone.isChecked = todo.isDone
            todoRowBinding.tvTodoDate.text = todo.createDate

            todoRowBinding.btnDelete.setOnClickListener {
                todosViewModel.deleteTodo(todo)
            }

            todoRowBinding.btnEdit.setOnClickListener {
                (context as ScrollingActivity).
                    showEditDialog(todo)
            }

            todoRowBinding.cbTodoDone.setOnClickListener {
                todo.isDone = todoRowBinding.cbTodoDone.isChecked
                todosViewModel.updateTodo(todo)
            }
        }
    }
}

class TodoDiffCallback : DiffUtil.ItemCallback<Todo>() {
    override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem._todoId == newItem._todoId
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem == newItem
    }
}