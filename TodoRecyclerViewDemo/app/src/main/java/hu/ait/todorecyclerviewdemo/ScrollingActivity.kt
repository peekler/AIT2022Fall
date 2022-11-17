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
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
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
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt
import kotlin.concurrent.thread

class ScrollingActivity : AppCompatActivity(),
    TodoDialog.TodoDialogHandler {

    companion object {
        const val KEY_TODO_EDIT = "KEY_TODO_EDIT"
        const val PREF_SETTINGS = "SETTINGS"
        const val KEY_FIRST_START = "KEY_FIRST_START"
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

        if (isItFirstStart()) {
            MaterialTapTargetPrompt.Builder(this)
                .setPrimaryText("Create item")
                .setSecondaryText("Click here to create new todo")
                .setTarget(binding.fabAddItem)
                .show()

            saveAppWasStarted()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_scrolling, menu)

        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchView: SearchView? =
            searchItem?.actionView as SearchView

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                AppDatabase.getInstance(this@ScrollingActivity).todoDao().findTodos(newText!!)
                    .observe(
                        this@ScrollingActivity, Observer { items ->
                            adapter.submitList(items)
                        })

                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }


    fun saveAppWasStarted() {
        val sp = getSharedPreferences(PREF_SETTINGS, MODE_PRIVATE)
        val editor = sp.edit()
        editor.putBoolean(KEY_FIRST_START, false)
        editor.commit()
    }

    fun isItFirstStart(): Boolean {
        val sp = getSharedPreferences(PREF_SETTINGS, MODE_PRIVATE)
        return sp.getBoolean(KEY_FIRST_START, true)
    }


    fun initRecyclerView() {
        adapter = TodoAdapter(this, todosViewModel)
        binding.recyclerTodo.adapter = adapter

        val callback: ItemTouchHelper.Callback = TodoReyclerTouchCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.recyclerTodo)

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