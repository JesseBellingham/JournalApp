package com.example.journalapp.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.journalapp.R
import com.example.journalapp.entities.JournalEntry
import java.time.format.DateTimeFormatter

class JournalEntryAdapter(
    private val context: Context,
    private val dataSource: ArrayList<JournalEntry>) : BaseAdapter() {

    @RequiresApi(Build.VERSION_CODES.O)
    private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?): View {
        val view = inflater.inflate(R.layout.row, parent, false)

        // Get title element
        val body = view.findViewById(R.id.rowBody) as TextView
        val date = view.findViewById(R.id.rowDate) as TextView

        val entry = getItem(position) as JournalEntry

        body.text = entry.body
        date.text = entry.createdAt.toLocaleString()//.format(dateFormatter)

        return view
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }
}