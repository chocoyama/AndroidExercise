package com.example.chocoyama.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import java.lang.RuntimeException

class ButtonFragment : Fragment() {

    // フラグメントがアクティビティに配置されたときに呼ばれる
    override fun onAttach(context: Context?) {
        super.onAttach(context)

        // アクティビティがコールバックインターフェースを実装していることを確認
        if (context !is OnButtonClickListener)
            throw RuntimeException("リスナーを実装してください")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.button_fragment, container, false)
        view.findViewById<Button>(R.id.button)
            .setOnClickListener {
                val listener = context as? OnButtonClickListener
                listener?.onButtonClicked()
            }
        return view
    }

    interface OnButtonClickListener {
        fun onButtonClicked()
    }
}