package com.example.todo

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note:Note)

    @Delete
    suspend fun delete(note:Note)

    @Query("Select * from notes_table order by id")
    fun getAllNotes():LiveData<List<Note>>
}