package com.assignment.bookspot.Database

import Book
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface BookDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(book:List<Book>)

    @Query("SELECT * FROM books")
    suspend fun getBooks() : List<Book>

    @Delete
    suspend fun deleteAllBooks(book: Book)
}