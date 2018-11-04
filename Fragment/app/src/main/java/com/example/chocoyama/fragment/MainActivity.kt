package com.example.chocoyama.fragment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), ButtonFragment.OnButtonClickListener {
    private val labelFragmentTagName = "labelFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportFragmentManager.findFragmentByTag(labelFragmentTagName) == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, newLabelFragment(0), labelFragmentTagName)
                .commit()
        }
    }

    override fun onButtonClicked() {
        val fragment = supportFragmentManager.findFragmentByTag(labelFragmentTagName) as LabelFragment
        fragment.update()
    }
}
