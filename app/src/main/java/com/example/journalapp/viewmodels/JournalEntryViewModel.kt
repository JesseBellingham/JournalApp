package com.example.journalapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.journalapp.db.AppDatabase
import com.example.journalapp.entities.JournalEntry
import com.example.journalapp.repositories.JournalEntryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JournalEntryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: JournalEntryRepository
    val orderedEntries: LiveData<List<JournalEntry>>

    init {
        val journalEntryDao = AppDatabase.getDatabase(application, viewModelScope).journalEntryDao()
        repository = JournalEntryRepository(journalEntryDao)
        orderedEntries = repository.orderedEntries
    }

    fun insert(entry: JournalEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(entry)
    }
}