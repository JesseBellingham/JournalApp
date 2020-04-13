package com.example.journalapp.db

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.journalapp.entities.JournalEntry
import com.example.journalapp.entities.daos.JournalEntryDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@Database(entities = [JournalEntry::class], version = 1, exportSchema = false)
@TypeConverters(com.example.journalapp.utilities.TypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun journalEntryDao(): JournalEntryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "journal_app"
                ).addCallback(AppDatabaseCallback(scope))
                .build()

                INSTANCE = instance
                return instance
            }
        }
    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch { populateDatabase(database.journalEntryDao()) }
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun populateDatabase(journalEntryDao: JournalEntryDao) {
            journalEntryDao.deleteAll()
            val date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
            var journalEntry = JournalEntry(1, date, "moo")
            journalEntryDao.insert(journalEntry)

            journalEntry = JournalEntry(2, date, "moo 2")
            journalEntryDao.insert(journalEntry)
        }
    }
}