package hu.ait.todorecyclerviewdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.ait.todorecyclerviewdemo.data.Todo
import hu.ait.todorecyclerviewdemo.databinding.TodoRowBinding

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private var todoItems = mutableListOf<Todo>(
        Todo("Demo1", "13. 10. 2022", false),
        Todo("Demo2", "14. 10. 2022", false),
        Todo("Demo3", "15. 10. 2022", false)
    )

    val context: Context
    constructor(context: Context) {
        this.context = context
    }

    override fun getItemCount(): Int {
        return todoItems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val todoRowBinding = TodoRowBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(todoRowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTodo = todoItems[holder.adapterPosition]
        holder.bind(currentTodo)
    }

    fun addTodo(todo: Todo) {
        todoItems.add(todo)
        //notifyDataSetChanged()
        notifyItemInserted(
            todoItems.lastIndex
        )
    }


    inner class ViewHolder(private val todoRowBinding: TodoRowBinding) :
        RecyclerView.ViewHolder(todoRowBinding.root)
    {
        fun bind(todo: Todo) {
            todoRowBinding.cbTodoDone.text = todo.todoTitle
            todoRowBinding.cbTodoDone.isChecked = todo.isDone
            todoRowBinding.tvTodoDate.text = todo.createDate
        }
    }
}