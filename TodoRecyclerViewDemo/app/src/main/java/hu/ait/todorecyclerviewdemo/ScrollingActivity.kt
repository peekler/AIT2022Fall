package hu.ait.todorecyclerviewdemo

import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import hu.ait.todorecyclerviewdemo.adapter.TodoAdapter
import hu.ait.todorecyclerviewdemo.data.Todo
import hu.ait.todorecyclerviewdemo.databinding.ActivityScrollingBinding
import hu.ait.todorecyclerviewdemo.dialog.TodoDialog

class ScrollingActivity : AppCompatActivity(),
    TodoDialog.TodoDialogHandler {

    private lateinit var binding: ActivityScrollingBinding
    private lateinit var adapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = TodoAdapter(this)
        binding.recyclerTodo.adapter = adapter


        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title

        binding.fabAddItem.setOnClickListener { view ->
            val todoDialog = TodoDialog()
            todoDialog.show(supportFragmentManager,"TodoDialog")
        }
    }

    override fun todoCreated(todo: Todo) {
        // add this new todo to the recyclerView
        adapter.addTodo(todo)
    }

}