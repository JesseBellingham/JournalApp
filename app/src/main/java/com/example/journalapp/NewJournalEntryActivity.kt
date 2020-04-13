package com.example.journalapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class NewJournalEntryActivity : AppCompatActivity() {
    private lateinit var editJournalEntryView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_journal_entry)
        editJournalEntryView = findViewById(R.id.edit_entry)

        val button = findViewById<Button>(R.id.button_finished)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editJournalEntryView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val entry = editJournalEntryView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, entry)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.journalapp.journalentrysql.REPLY"
    }
}
