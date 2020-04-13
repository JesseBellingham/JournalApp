package com.example.journalapp

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.journalapp.adapters.JournalEntryListAdapter
import com.example.journalapp.entities.JournalEntry
import com.example.journalapp.viewmodels.JournalEntryViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var journalEntryViewModel: JournalEntryViewModel
    private val newJournalEntryActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = JournalEntryListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        journalEntryViewModel = ViewModelProvider(this).get(JournalEntryViewModel::class.java)
        journalEntryViewModel.orderedEntries.observe(this, Observer { entries ->
            entries?.let { adapter.setEntries(it) }
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewJournalEntryActivity::class.java)
            startActivityForResult(intent, newJournalEntryActivityRequestCode)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newJournalEntryActivityRequestCode &&
            resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewJournalEntryActivity.EXTRA_REPLY)?.let {
                val date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
                val entry = JournalEntry(3, date, it)
                journalEntryViewModel.insert(entry)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG)
            .show()
        }
    }
}
