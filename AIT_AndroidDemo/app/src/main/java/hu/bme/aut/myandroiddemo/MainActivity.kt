package hu.bme.aut.myandroiddemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSave = findViewById<Button>(R.id.btnSave)
        btnSave.setOnClickListener {
            //
            val etTodo = findViewById<EditText>(R.id.etTodo)
            val tvTodo = findViewById<TextView>(R.id.tvTodo)

            tvTodo.append("\n${etTodo.text.toString()}")

        }

    }
}