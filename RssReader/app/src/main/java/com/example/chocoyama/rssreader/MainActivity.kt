package com.example.chocoyama.rssreader

import android.app.LoaderManager
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Loader
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
//import android.support.v4.app.LoaderManager
//import android.support.v4.content.Loader
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Rss> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loaderManager.initLoader(1, null, this)

        createChannel(this)

        val fetchJob = JobInfo.Builder(1, ComponentName(this, PollingJob::class.java))
            .setPeriodic(TimeUnit.HOURS.toMillis(6)) // 6時間ごと
            .setPersisted(true) // 再起動しても有効
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY) // オンライン
            .build()

        getSystemService(JobScheduler::class.java).schedule(fetchJob)

    }

    override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<Rss> = RssLoader(this)

    override fun onLoadFinished(loader: Loader<Rss>, data: Rss?) {
        if (data != null) {
            val recyclerView = findViewById<RecyclerView>(R.id.articlesRecyclerView)

            val adapter = ArticlesAdapter(this, data.articles) {
                val intent = CustomTabsIntent.Builder().build()
                intent.launchUrl(this, Uri.parse(it.link))
            }
            recyclerView.adapter = adapter

            val layoutManger = GridLayoutManager(this, 2)
            recyclerView.layoutManager = layoutManger
        }
    }

    override fun onLoaderReset(p0: Loader<Rss>) {
    }

}
