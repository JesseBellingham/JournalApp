package com.example.journalapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.journalapp.adapters.JournalEntryAdapter
import com.example.journalapp.utilities.JsonUtilities

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class EntryListFragment : Fragment() {
    private lateinit var listView: ListView
    private val jsonUtil = JsonUtilities()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.entry_list_fragment, container, false)

        listView = view.findViewById(R.id.entriesList)
        var entries = jsonUtil.getEntriesFromFile(activity!!.applicationContext)
        val adapter = JournalEntryAdapter(activity!!.applicationContext, entries)
        listView.adapter = adapter

        return view
    }
}
