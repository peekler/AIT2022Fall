package hu.ait.sensordemo

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.ait.sensordemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SensorEventListener {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetSensor.setOnClickListener {
            listSensors()
        }

        binding.btnStartSensor.setOnClickListener {
            val sensorManager =
                getSystemService(SENSOR_SERVICE) as SensorManager

            val sensor = sensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER)
            sensorManager.registerListener(this, sensor,
                SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun listSensors() {
        val sensorManager =
            getSystemService(SENSOR_SERVICE) as SensorManager

        sensorManager.getSensorList(Sensor.TYPE_ALL).forEach {
            binding.tvData.append("${it.name}\n")
        }

    }


    override fun onSensorChanged(event: SensorEvent) {
        val accX = event.values[0].toDouble()
        val accY = event.values[1].toDouble()
        val accZ = event.values[2].toDouble()


        binding.tvData.text =
            """
                X: $accX
                Y: $accY
                Z: $accZ
            """.trimIndent()
    }


    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}