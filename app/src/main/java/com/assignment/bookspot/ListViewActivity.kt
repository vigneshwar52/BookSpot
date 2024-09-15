package com.assignment.bookspot

import Book
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.assignment.bookspot.ViewModel.BookListViewModel
import com.assignment.bookspot.ui.theme.BookSpotTheme

class ListViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookSpotTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting3(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun HomePageListView(
    viewModel: BookListViewModel,
    navController: NavController
) {
    val bookList by viewModel.fetchBookListFromModel.observeAsState(emptyList())
    val isLoading by viewModel.isLoadingFetch.observeAsState(initial = false)
    val errorMessage by viewModel.errorMessageFetch.observeAsState(initial = null)

    Scaffold(
        topBar = {
                SearchBar(viewModel)
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                errorMessage != null -> {
                    Text(
                        text = errorMessage ?: "",
                        color = Color.Red,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(50.dp)
                    )
                }
                else ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(bookList) { bookItem ->
                            BookListItem(book = bookItem) {
                                navController.navigate(Screen.Detail.createRoute(bookItem.isbn))
                            }
                        }
                    }
            }
        }

    }
}

@Composable
fun BookListItem(book: Book, onClick: (() -> Unit)?) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable {
                onClick?.invoke()
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF7F2F9),
        )
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = book.thumbnailUrl,
                contentDescription = "Thumbnail of ${book.title}",
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp)
            )
            Column(modifier = Modifier.padding(8.dp)) {
                book.title?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.DarkGray
                    )
                }
                /*                val authors =
                                    book.authors?.takeIf { it.isNotBlank() } ?: "No description available"*/
                val authors =
                    book.authors
                Text(
                    text = authors.toString() ?: "No authors available",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.DarkGray
                )
                val categories = book.categories
                Text(
                    text = categories.toString() ?: "No categories available",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.DarkGray
                )
                val isbn =
                    book.isbn
                Text(
                    text = "ISBN NO : $isbn",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Composable
fun Greeting3(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    BookSpotTheme {
        Greeting3("Android")
    }
}