package com.example.chocoyama.memo

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.io.File

class FilesAdapter(
    private val context: Context,
    private val files: List<File>,
    private val onFileCliecked: (File) -> Unit
) : RecyclerView.Adapter<FilesAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.list_item_row, parent, false)
        val viewHolder = ViewHolder(view)

        view.setOnClickListener {
            val file = files[viewHolder.adapterPosition]
            onFileCliecked(file)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val file = files[position]
        holder.titleTextView.text = file.name
        holder.updateTimeTextView.text = context.getString(R.string.last_modified, file.lastModified())
    }

    override fun getItemCount(): Int = files.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView = view.findViewById<TextView>(R.id.titleTextView)!!
        val updateTimeTextView = view.findViewById<TextView>(R.id.lastModifiedTextView)!!
    }
}