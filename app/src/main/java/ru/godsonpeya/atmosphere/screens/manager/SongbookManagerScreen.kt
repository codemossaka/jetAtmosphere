package ru.godsonpeya.atmosphere.screens.manager

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.godsonpeya.atmosphere.data.local.entity.ApiStatus
import ru.godsonpeya.atmosphere.data.local.entity.LanguageWithSongBook
import ru.godsonpeya.atmosphere.widgets.ExpandableCard
import ru.godsonpeya.atmosphere.widgets.LoaderView
import ru.godsonpeya.atmosphere.widgets.setOpenDialog

@Composable
fun SongbookManagerScreen(navController: NavHostController, viewModel: SongbookManagerViewModel) {

    val result = viewModel.songBooks.collectAsState()
    val downloadingSongBook = viewModel.downloadingSongBook.collectAsState()
    val expandedCard = viewModel.expandedLanguageList.collectAsState()
    val status = viewModel.status


    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Manager") }, navigationIcon = {
            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = "ddd",
                modifier = Modifier.clickable { navController.popBackStack() })
        }, actions = {
            Icon(imageVector = Icons.Filled.Refresh,
                contentDescription = "ddd",
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clickable {viewModel.syncData() })
        })
    }) { inner ->
        if (status.value != ApiStatus.IDLE) {
            LoaderView(status = status,
                openDialog = setOpenDialog(status),
                songBook = downloadingSongBook){
                viewModel.cancelDownLoadSongBook()
            }
        }
        val languageWithSongBooks: MutableList<LanguageWithSongBook> = result.value
        if (languageWithSongBooks.isEmpty()) {
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(inner)
                    .fillMaxSize()) {
                Button(onClick = { viewModel.syncData() }) {
                    Text(text = "Download")
                }
            }
        } else {
            LazyColumn(modifier = Modifier.padding(inner)) {
                itemsIndexed(items = languageWithSongBooks) { _, languageWithSongBook ->
                    ExpandableCard(songBookItem = languageWithSongBook,
                        onCardArrowClick = { viewModel.languageClick(languageWithSongBook.language.id!!) },
                        expanded = expandedCard.value.contains(languageWithSongBook.language.id),
                        viewModel = viewModel)
                }
            }
        }
    }
}