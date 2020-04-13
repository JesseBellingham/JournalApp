package com.example.journalapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "journal_entry")
data class JournalEntry(
    @PrimaryKey val id: UUID,
    @ColumnInfo(name = "created_at") val createdAt: Date,
    @ColumnInfo(name = "entry_body") val body: String
) {
    constructor(createdAt: Date, entryBody: String)
            : this(UUID.randomUUID(), createdAt, entryBody)
}