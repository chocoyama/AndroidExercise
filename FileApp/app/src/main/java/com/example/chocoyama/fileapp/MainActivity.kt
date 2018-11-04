package com.example.chocoyama.fileapp

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import java.io.File

class MainActivity : AppCompatActivity() {

    private var currentDir: File = Environment.getExternalStorageDirectory()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.filesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        if (hasPermission()) showFiles()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (!grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showFiles()
        } else {
            finish()
        }
    }

    override fun onBackPressed() {
        if (currentDir != Environment.getExternalStorageDirectory()) {
            currentDir = currentDir.parentFile
            showFiles()
        } else {
            super.onBackPressed()
        }
    }

    private fun showFiles() {
        val adapter = FilesAdapter(this, currentDir.listFiles().toList()) {
            if (it.isDirectory) {
                currentDir = it
                showFiles()
            } else {
                Toast.makeText(this, it.absolutePath, Toast.LENGTH_SHORT).show()
            }
        }
        recyclerView.adapter = adapter
        title = currentDir.path
    }

    private fun hasPermission(): Boolean {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        val checkResult = ContextCompat.checkSelfPermission(this, permission)
        if (checkResult != PackageManager.PERMISSION_GRANTED) {
            val requestCode = 1
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            return false
        }

        return true
    }
}
