package com.example.chocoyama.memo

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import java.io.File

class MainActivity : AppCompatActivity(),
    FilesListFragment.OnFileSelectListener,
    InputFragment.OnFileOutputListener {

    private var drawerToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (hasPermission()) setViews()
    }

    // アクティビティの生成が終わった後に呼ばれる
    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        drawerToggle?.syncState()
    }

    // 画面が回転するなど、状態が変化したときに呼ばれる
    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        drawerToggle?.onConfigurationChanged(newConfig)
    }

    // オプションメニューがタップされたときに呼ばれる
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // ドロワーに伝える
        if (drawerToggle?.onOptionsItemSelected(item) == true) {
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (!grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setViews()
            drawerToggle?.syncState()
        } else {
            finish()
        }
    }

    override fun onFileSelected(file: File) {
        val fragment = supportFragmentManager.findFragmentById(R.id.input) as InputFragment
        fragment.show(file)
    }

    override fun onFileOutput() {
        val fragment = supportFragmentManager.findFragmentById(R.id.list) as FilesListFragment
        fragment.show()
    }

    private fun setupDrawer(drawer: DrawerLayout) {
        val toggle = ActionBarDrawerToggle(this, drawer, R.string.app_name, R.string.app_name)
        toggle.isDrawerIndicatorEnabled = true // ドロワーのトグルを有効にする
        drawer.addDrawerListener(toggle) // 開いたり閉じたりのコールバックを設定する

        drawerToggle = toggle

        // アクションバーの設定を行う
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true) // ドロワー用のアイコンを表示
            setHomeButtonEnabled(true)
        }
    }

    private fun setViews() {
        setContentView(R.layout.activity_main)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)

        if (drawerLayout != null) {
            setupDrawer(drawerLayout)
        }
    }

    private fun hasPermission(): Boolean {
        val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val checkResult = ContextCompat.checkSelfPermission(this, permission)
        if (checkResult != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), 1)
            return false
        }

        return true
    }
}
