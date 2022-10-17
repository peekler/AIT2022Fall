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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import hu.ait.todorecyclerviewdemo.adapter.TodoAdapter
import hu.ait.todorecyclerviewdemo.data.Todo
import hu.ait.todorecyclerviewdemo.databinding.ActivityScrollingBinding
import hu.ait.todorecyclerviewdemo.dialog.TodoDialog
import hu.ait.todorecyclerviewdemo.touch.TodoReyclerTouchCallback

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

        val touchCallbakList = TodoReyclerTouchCallback(adapter)
        val itemTouchHelper = ItemTouchHelper(touchCallbakList)
        itemTouchHelper.attachToRecyclerView(binding.recyclerTodo)

        /*val itemDivider = DividerItemDecoration(
            this,
            DividerItemDecoration.VERTICAL
        )
        binding.recyclerTodo.addItemDecoration(itemDivider)*/

        //binding.recyclerTodo.layoutManager = StaggeredGridLayoutManager(
        //    2, StaggeredGridLayoutManager.VERTICAL)



        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title

        binding.fabAddItem.setOnClickListener { view ->
            val todoDialog = TodoDialog()
            todoDialog.show(supportFragmentManager, "TodoDialog")
        }
    }

    override fun todoCreated(todo: Todo) {
        // add this new todo to the recyclerView
        adapter.addTodo(todo)

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

}