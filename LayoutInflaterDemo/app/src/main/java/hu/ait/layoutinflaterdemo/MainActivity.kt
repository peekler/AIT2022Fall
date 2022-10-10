package hu.ait.layoutinflaterdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.ait.layoutinflaterdemo.databinding.ActivityMainBinding
import hu.ait.layoutinflaterdemo.databinding.TodoRowBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            saveItem()
        }
    }

    private fun saveItem() {
        val todoRowBinding = TodoRowBinding.inflate(
            layoutInflater)

        todoRowBinding.tvTitle.text =
            binding.etTodo.text.toString()

        if (binding.cbImportant.isChecked){
            todoRowBinding.ivIcon.setImageResource(
                R.drawable.important
            )
        } else {
            todoRowBinding.ivIcon.setImageResource(
                R.drawable.normal
            )
        }

        todoRowBinding.btnDel.setOnClickListener {
            binding.layoutContent.removeView(todoRowBinding.root)
        }


        binding.layoutContent.addView(todoRowBinding.root)
    }

}