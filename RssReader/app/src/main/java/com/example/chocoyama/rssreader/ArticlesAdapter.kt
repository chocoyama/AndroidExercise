package com.example.chocoyama.rssreader

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ArticlesAdapter(
    private val context: Context,
    private val articles: List<Article>,
    private val onArticleClicked: (Article) -> Unit
) : RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.grid_article_cell, parent, false)
        val viewHolder = ViewHolder(view)

        view.setOnClickListener {
            val position = viewHolder.adapterPosition
            val article = articles[position]
            onArticleClicked(article)
        }

        return viewHolder
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.titleTextView.text = article.title
        holder.pubDateTextView.text = context.getString(R.string.pubDate, article.pubDate)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView = view.findViewById<TextView>(R.id.titleTextView)!!
        val pubDateTextView = view.findViewById<TextView>(R.id.pubDateTextView)!!
    }

}