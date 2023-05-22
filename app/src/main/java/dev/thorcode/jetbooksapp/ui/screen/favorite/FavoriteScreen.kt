package dev.thorcode.jetbooksapp.ui.screen.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.thorcode.jetbooksapp.R
import dev.thorcode.jetbooksapp.di.Injection
import dev.thorcode.jetbooksapp.model.Book
import dev.thorcode.jetbooksapp.ui.ViewModelFactory
import dev.thorcode.jetbooksapp.ui.common.UiState
import dev.thorcode.jetbooksapp.ui.components.BookFavoriteItem
import dev.thorcode.jetbooksapp.ui.components.MessageText

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteBookViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->  
        when(uiState) {
            is UiState.Loading -> {
                viewModel.getFavoriteBook()
            }
            is UiState.Success -> {
                FavoriteBookContent(
                    books = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                )
            }
            is UiState.Error -> {
                MessageText()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteBookContent(
    books: List<Book>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    Column(modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.menu_favorite),
                            textAlign = TextAlign.Center
                        )
                    },
                )
            }
        ) { innerPadding ->
            if (books.isEmpty()) {
                MessageText()
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(innerPadding)
                ) {
                    items(books) { item ->
                        BookFavoriteItem(
                            bookId = item.id,
                            image = item.image,
                            title = item.title,
                            author = item.author,
                            modifier = Modifier.clickable {
                                navigateToDetail(item.id)
                            }
                        )
                    }
                }
            }
        }

    }
}

