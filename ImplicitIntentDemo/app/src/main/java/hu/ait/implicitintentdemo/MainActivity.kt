package hu.ait.implicitintentdemo

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnIntent = findViewById<Button>(R.id.btnIntent)
        btnIntent.setOnClickListener {
            //intentSearch()
            //intentCall()
            //intentSend()
            //intentWaze()
            intentStreetMaps()
        }
    }

    fun intentSearch() {
        val intent = Intent(Intent.ACTION_WEB_SEARCH)
        intent.putExtra(SearchManager.QUERY,"Balaton")
        startActivity(intent)
    }

    private fun intentCall() {
        val intentCall = Intent(Intent.ACTION_DIAL,
            Uri.parse("tel:+361234567")
        )
        startActivity(intentCall)
    }

    private fun intentSend() {
        val intentSend = Intent(Intent.ACTION_SEND)
        intentSend.type = "text/plain"
        intentSend.`package` = "com.facebook.katana"
        intentSend.putExtra(Intent.EXTRA_TEXT, "Share demo")
        startActivity(intentSend)
        //startActivity(Intent.createChooser(intentSend, "Select share app"));
    }

    private fun intentWaze() {
        //String wazeUri = "waze://?favorite=Home&navigate=yes";
        //val wazeUri = "waze://?ll=40.761043, -73.980545&navigate=yes"
        val wazeUri = "waze://?q=BME&navigate=yes"
        val intentTest = Intent(Intent.ACTION_VIEW)
        intentTest.data = Uri.parse(wazeUri)
        startActivity(intentTest)
    }

    private fun intentStreetMaps() {
        val gmmIntentUri = Uri.parse(
            "google.streetview:cbll=29.9774614,31.1329645&cbp=0,30,0,0,-15")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

}