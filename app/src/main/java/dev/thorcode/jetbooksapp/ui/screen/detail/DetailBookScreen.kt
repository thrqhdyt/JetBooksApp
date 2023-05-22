package dev.thorcode.jetbooksapp.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.thorcode.jetbooksapp.R
import dev.thorcode.jetbooksapp.di.Injection
import dev.thorcode.jetbooksapp.ui.ViewModelFactory
import dev.thorcode.jetbooksapp.ui.common.UiState
import dev.thorcode.jetbooksapp.ui.components.MessageText
import dev.thorcode.jetbooksapp.ui.theme.JetBooksAppTheme

@Composable
fun DetailBookScreen(
    bookId: Long,
    viewModel: DetailBookViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState){
            is UiState.Loading -> {
                viewModel.getBookById(bookId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailBookContent(
                    image = data.image,
                    title = data.title,
                    author = data.author,
                    description = data.description,
                    isFavorite = data.isFavorite,
                    onBackClick = navigateBack,
                    onFavBtnClick = {
                        viewModel.updateIsFavorite(data.id, !data.isFavorite)
                    },
                )
            }
            is UiState.Error -> {
                MessageText()
            }
        }
    }
}

@Composable
fun DetailBookContent(
    @DrawableRes image: Int,
    title: String,
    author: String,
    description: String,
    onBackClick: () -> Unit,
    isFavorite: Boolean,
    onFavBtnClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var statusFavorite by rememberSaveable { mutableStateOf(isFavorite) }

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                Image(
                    painter = painterResource(image),
                    contentDescription = title,
                    contentScale = ContentScale.FillBounds,
                    modifier = modifier
                        .height(320.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() }
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Text(
                    text = author,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                )
            }
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
            .background(LightGray))
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (statusFavorite) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = stringResource(R.string.favorite_button),
                    tint = Color.Red,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(36.dp)
                        .clickable {
                            statusFavorite = false
                            onFavBtnClick(false)
                        }
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = stringResource(R.string.favorite_button),
                    tint = Color.DarkGray,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(36.dp)
                        .clickable {
                            statusFavorite = true
                            onFavBtnClick(true)
                        }
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    JetBooksAppTheme {
        DetailBookContent(
            image = R.drawable.cantik_itu_luka,
            title = "Cantik Itu Luka",
            author = "Eka Kurniawan" ,
            description = "Buku ini sangat bagus",
            isFavorite = true,
            onBackClick = {},
            onFavBtnClick = {}
        )
    }
}