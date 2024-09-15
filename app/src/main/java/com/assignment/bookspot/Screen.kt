package com.assignment.bookspot

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{isbn}"){
        fun createRoute(bookIsbn: String) = "detail/$bookIsbn"
    }
}