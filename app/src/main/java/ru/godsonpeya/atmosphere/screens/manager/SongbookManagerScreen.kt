package ru.godsonpeya.atmosphere.screens.manager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import ru.godsonpeya.atmosphere.component.ExpandableLanguageRow
import ru.godsonpeya.atmosphere.component.ExpandableSongBookRow
import ru.godsonpeya.atmosphere.data.local.entity.ApiStatus
import ru.godsonpeya.atmosphere.data.local.entity.LanguageWithSongBook
import ru.godsonpeya.atmosphere.utils.checkForInternet
import ru.godsonpeya.atmosphere.widgets.LoaderView
import ru.godsonpeya.atmosphere.widgets.ScaffoldLayoutApp
import ru.godsonpeya.atmosphere.widgets.setOpenDialog

@Composable
fun SongbookManagerScreen(navController: NavHostController, viewModel: SongbookManagerViewModel) {

    val result = viewModel.songBooks.collectAsState()
    val downloadingSongBook = viewModel.downloadingSongBook.collectAsState()
    val expandedCard = viewModel.expandedLanguageList.collectAsState()
    val status = viewModel.status
    val context = LocalContext.current

    LaunchedEffect(Unit) {
//        if (checkForInternet(context)) {
//            viewModel.syncData()
//        }
    }
    ScaffoldLayoutApp.setScaffold(topBar = {
        TopAppBar(title = { Text(text = "Manager") }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = "ddd",
                    modifier = Modifier)
            }
        }, actions = {
            IconButton(onClick = { viewModel.syncData() }) {
                Icon(imageVector = Icons.Filled.Refresh,
                    contentDescription = "ddd",
                    modifier = Modifier)
            }
        })
    })


    if (status.value != ApiStatus.IDLE) {
        LoaderView(status = status,
            openDialog = setOpenDialog(status),
            songBook = downloadingSongBook) {
            viewModel.cancelDownLoadSongBook()
        }
    }
    val languageWithSongBooks: MutableList<LanguageWithSongBook> = result.value
    if (languageWithSongBooks.isEmpty()) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Button(onClick = { viewModel.syncData() }) {
                Text(text = "Download")
            }
        }
    } else {
        LazyColumn(modifier = Modifier) {
            itemsIndexed(items = languageWithSongBooks) { _, languageWithSongBook ->
                val expanded = expandedCard.value.contains(languageWithSongBook.language.id)
                ExpandableLanguageRow(songBookItem = languageWithSongBook,
                    onCardArrowClick = { viewModel.languageClick(languageWithSongBook.language.id!!) },
                    expanded = expanded) {
                    ExpandableSongBookRow(expanded = expanded,
                        songBooks = languageWithSongBook.songbooks) { songBook ->
                        if (!songBook.isDownLoaded) viewModel.downLoadSongBook(songBook) else viewModel.deleteSongBook(
                            songBook.id!!)

                    }
                }
            }
        }
    }

}