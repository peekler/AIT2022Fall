package hu.ait.threadandtimerdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.ait.threadandtimerdemo.databinding.ActivityMainBinding
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    var enabled : Boolean = false

    inner class MyThread : Thread() {
        override fun run() {
            super.run()
            while (enabled) {
                runOnUiThread {
                    binding.tvData.append("#")
                }
                sleep(1000)
            }
        }
    }

    inner class MyTimerTask : TimerTask() {
        override fun run() {
            runOnUiThread {
                binding.tvData.append("&")
            }
        }
    }

    var myTimer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            enabled = true
            //MyThread().start()
            var myTask = MyTimerTask()
            myTimer.schedule(myTask,3000,1000)

            binding.tvData.append("Hello")
        }

        binding.btnStop.setOnClickListener {
            enabled = false
            myTimer.cancel() // stops all timertasks scheduled on this timer
            myTimer = Timer()
        }
    }

    override fun onStop() {
        super.onStop()
        try {
            myTimer.cancel()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}