package com.assignment.bookspot.API

import Book
import retrofit2.http.GET
import retrofit2.http.Path

public interface APIService {
    @GET("books/")
    suspend fun getBooks():List<Book>

    @GET("books/{id}")
    suspend fun getBookDetails(@Path("id") id:String):Book
}