package com.joshhn.codepathmail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var emails: MutableList<Email>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvEmails = findViewById<RecyclerView>(R.id.rvEmails)
        val btnLoadMore = findViewById<Button>(R.id.btnLoadMore)

        emails = EmailFetcher.getEmails()

        val adapter = EmailAdapter(emails)
        rvEmails.adapter = adapter
        rvEmails.layoutManager = LinearLayoutManager(this)

        btnLoadMore.setOnClickListener {
            val newEmails = EmailFetcher.getNext5Emails()
            emails.addAll(newEmails)
            adapter.notifyDataSetChanged()
        }
    }
}