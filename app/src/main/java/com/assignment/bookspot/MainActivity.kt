package com.assignment.bookspot

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.assignment.bookspot.API.RetrofitInstance
import com.assignment.bookspot.ViewModel.BookListViewModel
import com.assignment.bookspot.ui.theme.BookSpotTheme

private val TAG:String = MainActivity::class.java.simpleName
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            BookSpotTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()
    val viewModel: BookListViewModel = viewModel()

    Scaffold { innerPadding ->
        NavHost(navController = navController, startDestination = Screen.Home.route) {
            composable(Screen.Home.route) {
                HomePageListView(viewModel, navController)
            }
            composable(Screen.Detail.route) { backStackEntry ->
                val bookISBN = backStackEntry.arguments?.getString("isbn")
                bookISBN?.let {
                    DetailedView(viewModel = viewModel, bookISBN = it, innerPadding)
                }
            }
        }
    }
}

@Composable
fun SearchBar(viewModel: BookListViewModel) {
    Box {
        Card(
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF7F2F9),
            )
        ) {
            var ipAddress by rememberSaveable { mutableStateOf("") }
            var isEditing by rememberSaveable { mutableStateOf(true) }

            if(isEditing) {
                TextField(
                    value = ipAddress,
                    singleLine = true,
                    onValueChange = { ipAddress = it },
                    placeholder = { Text("Enter an IP to fetch Book Items...") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.LightGray,  // Background color when focused
                        unfocusedContainerColor = Color(0xFFF7F2F9),  // Background color when not focused
                        focusedIndicatorColor = Color.DarkGray,  // Line color when focused
                        unfocusedIndicatorColor = Color.Gray  // Line color when not focused
                    ),
                    textStyle = TextStyle(Color.DarkGray),
                    trailingIcon = {
                        IconButton(onClick = {
                            if (ipAddress.isEmpty()) {
                                ipAddress = "192.168.1.1"
                            }
                            Log.d(TAG, "IP Address set to: $ipAddress")
                            RetrofitInstance.updateIP(ipAddress)
                            try {
                                viewModel.fetchBookList()
                                isEditing = false
                            } catch (e: Exception) {
                                Log.e("NetworkRequest", "Error fetching book list", e)
                            }
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Search",
                                tint = Color.Gray
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
            }
            else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("IP Address: ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(ipAddress)
                            }
                        },
                        color = Color.DarkGray,
                        modifier = Modifier
                            .padding(20.dp)
                    )
                    IconButton(onClick = { isEditing = true }) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Edit",
                            tint = Color.Gray
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BookSpotTheme {
        /*val previewBookItem = Book(
            title = "Book Title",
            authors = listOf("Author Name 1", "Author Name 2"),
            categories = listOf("Category 1", "Category 2"),
            isbn = "1234567890",
            thumbnailUrl = "https://s3.amazonaws.com/AKIAJC5RLADLUMVRPFDQ.book-thumb-images/ableson2.jpg"
        )

        // Preview one list item
        BookListItem(book = previewBookItem, onClick = null)*/
//        SearchBar()

        SearchBar(viewModel = BookListViewModel())

    }
}