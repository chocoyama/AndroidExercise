package com.example.chocoyama.fileapp

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.io.File

class FilesAdapter(
    context: Context,
    private val files: List<File>,
    private val onClick: (File) -> Unit
) : RecyclerView.Adapter<FilesAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.list_file_row, parent, false)
        val viewHolder = ViewHolder(view)

        view.setOnClickListener {
            onClick(files[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int = files.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.fileNameTextView.text = files[position].name
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fileNameTextView = view.findViewById<TextView>(R.id.fileNameTextView)!!
    }
}