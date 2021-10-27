package com.example.aibnotesapproom

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val noteDao by lazy { NoteDatabase.getDatabase(this).noteDao() }
    private val repository by lazy { NoteRepository(noteDao) }

    private lateinit var RV: RecyclerView
    private lateinit var EDTnote: EditText
    private lateinit var submitBtn: Button
    private lateinit var notes: List<MyNote>

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notes = listOf()
        EDTnote = findViewById(R.id.tvNewNote)
        submitBtn = findViewById(R.id.btSubmit)
        RV = findViewById(R.id.rvNotes)

        submitBtn.setOnClickListener {
            if(EDTnote.text.isNotBlank())
            {
                addNote(EDTnote.text.toString())
                EDTnote.text.clear()
                updateRV()
            }
            else
            {
                Toast.makeText(applicationContext, "Pls Enter Text Note! ", Toast.LENGTH_SHORT).show()
            }
        }

        getItemsList()
        updateRV()
    }

    private fun updateRV()
    {
        RV.adapter = RecyclerViewAdapter(this, notes)
        RV.layoutManager = LinearLayoutManager(this)

    }

    private fun getItemsList()
    {
        CoroutineScope(Dispatchers.IO).launch {
            val data = async {
                repository.getNotes
            }.await()
            if(data.isNotEmpty()){
                notes = data
                updateRV()
            }else{
                Log.e("MainActivity", "Unable to get data", )
            }
        }
    }

    private fun addNote(noteText: String)
    {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addNote(MyNote(0, noteText))

        }
        updateRV()
    }

    private fun editNote(noteID: Int, noteText: String)
    {
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateNote(MyNote(noteID, noteText))

        }
        updateRV()
    }

    fun deleteNote(noteID: Int)
    {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteNote(MyNote(noteID, ""))

        }
        updateRV()
    }

    fun raiseDialog(id: Int)
    {
        val dialogBuilder = AlertDialog.Builder(this)
        val updatedNote = EditText(this)
        updatedNote.hint = "Enter new text"
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Save", DialogInterface.OnClickListener {
                    _, _ -> run {
                    editNote(id, updatedNote.text.toString())
                    updateRV()
                }
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(updatedNote)
        alert.show()
    }
}