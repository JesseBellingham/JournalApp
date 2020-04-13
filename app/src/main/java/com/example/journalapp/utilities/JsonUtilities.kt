package com.example.journalapp.utilities

import android.content.Context
import android.util.Log
import com.example.journalapp.entities.JournalEntry
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.IOException

class JsonUtilities {
    private val gson: Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        .create()

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName)
                .bufferedReader()
                .use {
                    it.readText()
                }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun getEntriesFromFile(context: Context): ArrayList<JournalEntry> {
        val jsonFileString =
            getJsonDataFromAsset(context,"entries.json")
        Log.i("data", jsonFileString)

        val listEntryType = object : TypeToken<List<JournalEntry>>() {}.type

        return gson.fromJson(jsonFileString, listEntryType)
    }

    fun writeToFile(entries: ArrayList<JournalEntry>) {
        val file = File("entries.json")
        file.writeText(gson.toJson(entries))
    }
}