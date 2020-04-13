package com.example.journalapp.repositories

import androidx.lifecycle.LiveData
import com.example.journalapp.entities.JournalEntry
import com.example.journalapp.entities.daos.JournalEntryDao

class JournalEntryRepository(private val journalEntryDao: JournalEntryDao) {
    val allEntries: LiveData<List<JournalEntry>> = journalEntryDao.getAll()
    val orderedEntries: LiveData<List<JournalEntry>> = journalEntryDao.getOrdered()

    suspend fun insert(entry: JournalEntry) {
        journalEntryDao.insert(entry)
    }
}