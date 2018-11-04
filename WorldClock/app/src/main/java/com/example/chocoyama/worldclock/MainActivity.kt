package com.example.chocoyama.worldclock

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timeZone = TimeZone.getDefault()

        val timeZoneTextView = findViewById<TextView>(R.id.timeZoneTextView)
        timeZoneTextView.text = timeZone.displayName

        val addButton = findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener {
            val intent = Intent(this, TimeZoneSelectActivity::class.java)
            val requestCode = 1
            startActivityForResult(intent, requestCode)
        }

        showWorldClocks()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            val timeZone = data.getStringExtra("timeZone")

            val pref = getSharedPreferences("prefs", Context.MODE_PRIVATE)

            val timeZones = pref.getStringSet("time_zone", mutableSetOf())
            timeZones.add(timeZone)

            pref.edit().putStringSet("time_zone", timeZones).apply()

            showWorldClocks()
        }
    }

    private fun showWorldClocks() {
        val timeZones = getSharedPreferences("prefs", Context.MODE_PRIVATE)
            .getStringSet("time_zone", setOf())
            .toTypedArray()

        val list = findViewById<ListView>(R.id.clockListView)
        list.adapter = TimeZoneAdapter(this, timeZones)
    }
}
