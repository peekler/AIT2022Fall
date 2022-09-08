package hu.ait.aittimeshower

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import hu.ait.aittimeshower.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTime.setOnClickListener {
            val timeText = "Date/Time: ${Date(System.currentTimeMillis()).toString()}"

            binding.tvTime.text = timeText

            Toast.makeText(this,
                timeText,
                Toast.LENGTH_LONG).show()
        }
    }
}