package com.example.mytodo.viewmodal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mytodo.database.NoteDatabase
import com.example.mytodo.entity.Note
import com.example.mytodo.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModal (application: Application) : AndroidViewModel(application) {

    //private reference of repository
    private val repository:NoteRepository
    //public live data to cache the notes list
    val allNotes: LiveData<List<Note>>

    init {
        val notesDao= NoteDatabase.getDatabase(application).getDao()
        repository= NoteRepository(notesDao)
        allNotes=repository.allNotes
    }

    fun insertNotes(note: Note){

        viewModelScope.launch (Dispatchers.IO){
            repository.insertNotes(note)
        }

    }

    fun deleteNotes(note: Note){
        viewModelScope.launch (Dispatchers.IO){

            repository.deleteNotes(note)
        }
    }


}