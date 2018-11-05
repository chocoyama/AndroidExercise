package com.example.chocoyama.memo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import java.io.File

class InputFragment : Fragment() {
    private val fileSerializeKey = "file"
    private var currentFile: File? = null

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        currentFile?.let { outState.putSerializable(fileSerializeKey, it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null && savedInstanceState.containsKey(fileSerializeKey)) {
            currentFile = savedInstanceState.getSerializable(fileSerializeKey) as File
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_input, container, false)

        val content = view.findViewById<EditText>(R.id.contentEditText)
        val saveButton = view.findViewById<Button>(R.id.saveButton)

        saveButton.setOnClickListener {
            currentFile = outputFile(currentFile, content.text.toString())
            if (context is OnFileOutputListener) {
                (context as OnFileOutputListener).onFileOutput()
            }
        }

        return view
    }

    fun show(file: File) {
        val memo = inputFile(file)

        val content = view?.findViewById<EditText>(R.id.contentEditText) ?: return
        content.setText(memo)

        currentFile = file
    }

    interface OnFileOutputListener {
        fun onFileOutput()
    }
}