package hu.bme.aut.audioplaydemo

import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import hu.bme.aut.audioplaydemo.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), MediaPlayer.OnPreparedListener {

    lateinit var binding: ActivityMainBinding
    private lateinit var mediaPlayer: MediaPlayer

    private var timer: Timer? = null
    private var mySeekTimerTask: MySeekTimerTask? = null

    inner class MySeekTimerTask: TimerTask() {
        override fun run() {
            binding.seekBar.progress = mediaPlayer.currentPosition
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.seekBarVolume.min = 0
        binding.seekBarVolume.max = 100
        binding.seekBarVolume.progress = 100


        binding.seekBarVolume.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener
        {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (mediaPlayer != null) {
                    mediaPlayer.setVolume(progress.toFloat()/100,
                        progress.toFloat()/100)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding.btnStart.setOnClickListener {
            mediaPlayer = MediaPlayer.create(this@MainActivity,
                    R.raw.demo)
            mediaPlayer.setOnPreparedListener(this@MainActivity)
        }

        binding.btnStop.setOnClickListener {
            mediaPlayer?.stop()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPrepared(mp: MediaPlayer) {
        mediaPlayer.start()

        if (timer != null) {
            timer?.cancel()
        }
        timer = Timer()
        mySeekTimerTask = MySeekTimerTask()
        timer?.schedule(mySeekTimerTask,0, 1000)

        binding.seekBar.min = 0
        binding.seekBar.max = mediaPlayer.duration

        binding.seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener
        {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    override fun onStop() {
        mediaPlayer.stop()
        try {
            timer?.cancel()
        }catch (e: Exception) {
            e.printStackTrace()
        }
        super.onStop()
    }
}