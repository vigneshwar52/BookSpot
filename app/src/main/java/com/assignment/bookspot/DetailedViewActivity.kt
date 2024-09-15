package com.assignment.bookspot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.assignment.bookspot.ViewModel.BookListViewModel
import com.assignment.bookspot.ui.theme.BookSpotTheme
import com.assignment.bookspot.ui.theme.typography

class DetailedViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookSpotTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun DetailedView(viewModel: BookListViewModel, bookISBN: String, innerPadding: PaddingValues) {
    val bookInfo by viewModel.fetchBookDetailFromModel.observeAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(bookISBN) {
        viewModel.fetchBookDetail(bookISBN)
    }

    bookInfo?.let {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                it.title?.let { it1 ->
                    Text(
                        text = it1,
                        style = typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                }
                AsyncImage(
                    model = it.thumbnailUrl,
                    contentDescription = "Thumbnail of ${it.title}",
                    modifier = Modifier
                        .height(400.dp)
                        .width(200.dp),
                    error = painterResource(R.drawable.image_not_found)
                )
                Text(
                    text = it.authors.joinToString(", "),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(10.dp))
                it.status?.let { it1 ->
                    Text(
                        text = it1,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                it.longDescription?.let { it1 ->
                    Text(
                        text = it1,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                it.pageCount.toString().let { it1 ->
                    Text(
                        text = it1,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    BookSpotTheme {
        Greeting("Android")
    }
}