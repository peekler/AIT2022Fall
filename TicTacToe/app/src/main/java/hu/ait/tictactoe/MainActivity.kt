package hu.ait.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.ait.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReset.setOnClickListener {
           binding.ticTacToe.resetGame()
        }
    }
}