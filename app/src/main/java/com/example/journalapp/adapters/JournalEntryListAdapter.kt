package com.example.journalapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.journalapp.R
import com.example.journalapp.entities.JournalEntry

class JournalEntryListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<JournalEntryListAdapter.JournalEntryViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var entries = emptyList<JournalEntry>()

    inner class JournalEntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val entryItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalEntryViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return JournalEntryViewHolder(itemView)
    }

    override fun getItemCount() = entries.size

    override fun onBindViewHolder(holder: JournalEntryViewHolder, position: Int) {
        val current = entries[position]
        holder.entryItemView.text = current.body
    }

    internal fun setEntries(entries: List<JournalEntry>) {
        this.entries = entries
        notifyDataSetChanged()
    }
}