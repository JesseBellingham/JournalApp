package com.example.journalapp.entities.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.journalapp.entities.JournalEntry

@Dao
interface JournalEntryDao {
    @Query("SELECT * FROM journal_entry")
    fun getAll(): LiveData<List<JournalEntry>>

    @Query("SELECT * FROM journal_entry ORDER BY created_at ASC")
    fun getOrdered(): LiveData<List<JournalEntry>>

    @Query("SELECT * FROM journal_entry WHERE id IN (:entryIds)")
    fun loadAllByIds(entryIds: IntArray): LiveData<List<JournalEntry>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg entries: JournalEntry)

    @Delete
    suspend fun delete(entry: JournalEntry)
}