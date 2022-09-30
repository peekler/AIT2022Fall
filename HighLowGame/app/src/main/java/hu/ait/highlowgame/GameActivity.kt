package hu.ait.highlowgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.ait.highlowgame.databinding.ActivityGameBinding
import java.lang.Exception
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    companion object {
        const val KEY_NUM = "KEY_NUM"
    }

    lateinit var binding: ActivityGameBinding

    var generatedNum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null && savedInstanceState!!.containsKey(KEY_NUM)) {
            generatedNum = savedInstanceState.getInt(KEY_NUM)
        } else {
            generateNewNumber()
        }

        binding.btnGuess.setOnClickListener {
            try {

                if (binding.etGuess.text!!.isNotEmpty()) {
                    val myNum = binding.etGuess.text.toString().toInt()

                    if (myNum == generatedNum) {
                        binding.tvResult.text = "You have won!"
                        startActivity(Intent(this,
                            ResultActivity::class.java))
                    } else if (myNum < generatedNum) {
                        binding.tvResult.text = "The number is higher"
                    } else if (myNum > generatedNum) {
                        binding.tvResult.text = "The number is lower"
                    }
                } else {
                    binding.etGuess.error = "This value is not valid"

                }

            } catch (e: Exception) {
                binding.etGuess.error = e.message
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_NUM, generatedNum)

        super.onSaveInstanceState(outState)
    }


    fun generateNewNumber() {
        val rand = Random(System.currentTimeMillis())
        generatedNum = rand.nextInt(3) // 0..2
    }
}