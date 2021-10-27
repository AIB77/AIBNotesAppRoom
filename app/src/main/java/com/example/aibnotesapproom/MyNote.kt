package com.example.aibnotesapproom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NotesTable")
data class MyNote(@PrimaryKey(autoGenerate = true) val id: Int, val noteText: String)
