package com.example.journalapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.journalapp.adapters.EntryAdapter
import com.example.journalapp.entities.Entry
import com.example.journalapp.utilities.JsonUtilities
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.text.DateFormat

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private lateinit var listView: ListView
    private val jsonUtil = JsonUtilities()
    private val gson: Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        .create()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        listView = view.findViewById(R.id.entriesList)
        var entries = getEntriesFromFile()
        val adapter = EntryAdapter(activity!!.applicationContext, entries)
        listView.adapter = adapter

        return view
    }

    private fun getEntriesFromFile(): ArrayList<Entry> {
        val jsonFileString =
            jsonUtil.getJsonDataFromAsset(activity!!.applicationContext, "entries.json")
        Log.i("data", jsonFileString)

        val listEntryType = object : TypeToken<List<Entry>>() {}.type

        return gson.fromJson(jsonFileString, listEntryType)
    }
}
