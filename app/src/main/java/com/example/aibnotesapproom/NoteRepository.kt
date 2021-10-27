package com.example.aibnotesapproom

import android.provider.ContactsContract

class NoteRepository (private val noteDao: NoteDao) {

    val getNotes: List<MyNote> = noteDao.getNotes()

    suspend fun addNote(note: MyNote){
        noteDao.addNote(note)
    }

    suspend fun updateNote(note: MyNote){
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: MyNote){
        noteDao.deleteNote(note)
    }
}