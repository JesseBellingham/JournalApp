package com.example.journalapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.journalapp.entities.JournalEntry
import com.example.journalapp.entities.daos.JournalEntryDao

@Database(entities = [JournalEntry::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun journalEntryDao(): JournalEntryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "journal_app"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}