package hu.ait.todorecyclerviewdemo

import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import hu.ait.todorecyclerviewdemo.adapter.TodoAdapter
import hu.ait.todorecyclerviewdemo.data.AppDatabase
import hu.ait.todorecyclerviewdemo.data.Todo
import hu.ait.todorecyclerviewdemo.databinding.ActivityScrollingBinding
import hu.ait.todorecyclerviewdemo.dialog.TodoDialog
import hu.ait.todorecyclerviewdemo.touch.TodoReyclerTouchCallback
import hu.ait.todorecyclerviewdemo.viewmodel.TodoViewModel
import kotlin.concurrent.thread

class ScrollingActivity : AppCompatActivity(),
    TodoDialog.TodoDialogHandler {

    companion object {
        const val KEY_TODO_EDIT = "KEY_TODO_EDIT"
    }


    private lateinit var binding: ActivityScrollingBinding
    private lateinit var adapter: TodoAdapter

    private lateinit var todosViewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todosViewModel = ViewModelProvider(this)[TodoViewModel::class.java]

        initRecyclerView()

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title

        binding.fabAddItem.setOnClickListener { view ->
            val todoDialog = TodoDialog()
            todoDialog.show(supportFragmentManager, "TodoDialog")
        }

        binding.fabDeleteAll.setOnClickListener {
            todosViewModel.deleteAllTodos()
        }
    }

    fun initRecyclerView() {
        adapter = TodoAdapter(this, todosViewModel)
        binding.recyclerTodo.adapter = adapter
        todosViewModel.allTodos.observe(this) { todos ->
            adapter.submitList(todos)
        }
    }

    fun showEditDialog(todoToEdit: Todo) {
        val dialog = TodoDialog()

        val bundle = Bundle()
        bundle.putSerializable(KEY_TODO_EDIT, todoToEdit)
        dialog.arguments = bundle

        dialog.show(supportFragmentManager, "TAG_ITEM_EDIT")
    }

    override fun todoCreated(todo: Todo) {
        todosViewModel.insertTodo(todo)

        Snackbar.make(
            binding.root,
            "Item added",
            Snackbar.LENGTH_LONG
        ).setAction(
            "Undo"
        ) {
            // remove last todo from RecyclerView..
            adapter.deleteLast()
        }.show()
    }

    override fun todoUpdated(todo: Todo) {
        todosViewModel.updateTodo(todo)
    }

}