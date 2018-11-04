package com.example.chocoyama.recyclerview

import android.content.Context
import android.icu.util.TimeZone
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.timeZoneRecyclerView)

        val adapter = SampleAdapter(this) {
            Toast.makeText(this, it.displayName, Toast.LENGTH_SHORT).show()
        }

        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

}

class SampleAdapter(
    context: Context,
    private val onItemClicked: (TimeZone) -> Unit
) : RecyclerView.Adapter<SampleAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private val timeZones = TimeZone.getAvailableIDs().map {
        TimeZone.getTimeZone(it)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.list_time_zone_row, parent, false)

        val viewHolder = ViewHolder(view)

        // リストビューではsetOnItemClickListener()があったが、
        // リサイクラービューでは自前で実装する必要がある
        view.setOnClickListener {
            val position = viewHolder.adapterPosition // アダプター内のインデックスが取得できる
            val timeZone = timeZones[position]
            onItemClicked(timeZone)
        }

        return viewHolder
    }

    override fun getItemCount(): Int = timeZones.size

    override fun onBindViewHolder(holder: SampleAdapter.ViewHolder, position: Int) {
        holder.timeZoneTextView.text = timeZones[position].id
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val timeZoneTextView = view.findViewById<TextView>(R.id.timeZoneTextView)!!
    }
}