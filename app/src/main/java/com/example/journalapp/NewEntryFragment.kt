package com.example.journalapp

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.journalapp.R.id.journalEntryTextField
import com.example.journalapp.entities.JournalEntry
import com.example.journalapp.utilities.JsonUtilities
import com.google.gson.Gson
import java.io.File
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.TemporalQueries.localDate
import java.util.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NewEntryFragment : Fragment() {

    private val jsonUtil: JsonUtilities = JsonUtilities()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.new_entry_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.buttonFinished).setOnClickListener {
            saveEntry(view)
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveEntry(view: View) {
        val newEntryBody = view.findViewById<EditText>(journalEntryTextField).text.toString()
        val date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
        val entries = jsonUtil.getEntriesFromFile(activity!!.applicationContext)

        entries.add(JournalEntry(date, newEntryBody))
        jsonUtil.writeToFile(entries)
    }
}
