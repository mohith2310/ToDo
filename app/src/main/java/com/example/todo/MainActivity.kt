package com.example.todo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.new_note.*

class MainActivity : AppCompatActivity(), INotesRVAdapter {

    lateinit var mViewModel: NoteViewModel
    private lateinit var mRunnable: Runnable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvNotes.layoutManager = LinearLayoutManager(this)
        val adapter = NotesRVAdapter(this,this)

        if(adapter.itemCount == 0){
          userText.visibility = View.VISIBLE
        }
        rvNotes.adapter = adapter


        mViewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        mViewModel.allNotes.observe(this, Observer { list ->
            list?.let {
                adapter.updateNotes(it)
            }
        })
    }

    override fun onItemClicked(note: Note) {
        mViewModel.deleteNote(note)
        Toast.makeText(this,"${note.text.toString()} Deleted",LENGTH_SHORT).show()
    }

    fun submitData(view: android.view.View) {
        val noteText = newNoteText.text.toString()
        if(noteText.isNotEmpty()){
            mViewModel.insertNote(Note(noteText))
            Toast.makeText(this,"$noteText is inserted", LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }
    }

    fun newNote(view: android.view.View) {
        setContentView(R.layout.new_note)
    }
}