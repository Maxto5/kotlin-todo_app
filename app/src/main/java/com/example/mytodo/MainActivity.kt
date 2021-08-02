package com.example.mytodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.mytodo.adapters.NoteAdapter
import com.example.mytodo.entity.Note
import com.example.mytodo.viewmodal.NotesViewModal
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() , NoteAdapter.INotesRVAdapter{

    private lateinit var notesViewModal: NotesViewModal
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = NoteAdapter(this,this)
        rv_notes.adapter = adapter
        notesViewModal= ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(
            NotesViewModal::class.java)
        notesViewModal.allNotes.observe(this, { list->
           if(list.isEmpty()){
             rv_notes.visibility= View.INVISIBLE
              lt_dummy.visibility = View.VISIBLE


           }else{
               rv_notes.visibility = View.VISIBLE
               lt_dummy.visibility = View.INVISIBLE
               adapter.updateTodo(list)
           }


            list?.let {
                rv_notes.visibility = View.VISIBLE
                adapter.updateTodo(it)
            }
        })


        btn_add.setOnClickListener {
            val itext=et_notes.text.toString()
            if(itext.isNotEmpty()){
                notesViewModal.insertNotes(Note(itext))
                et_notes.setText("")
            }else{
                Toast.makeText(this,"field is empty", Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun onItemClick(notes: Note) {
        notesViewModal.deleteNotes(notes)
    }
}