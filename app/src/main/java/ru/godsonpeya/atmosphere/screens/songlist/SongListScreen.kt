package ru.godsonpeya.atmosphere.screens.songlist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.godsonpeya.atmosphere.component.RowSongItem
import ru.godsonpeya.atmosphere.data.dto.TabView
import ru.godsonpeya.atmosphere.navigation.NavigationItem
import ru.godsonpeya.atmosphere.widgets.ScaffoldLayoutApp
import ru.godsonpeya.atmosphere.widgets.ScaffoldLayoutApp.setScaffold
import ru.godsonpeya.atmosphere.widgets.TopBar

@Composable
fun SongListScreen(navController: NavController, songBookId: Int?, viewModel: SongListViewModel) {
    var searchState by remember {
        mutableStateOf(false)
    }
    var searchText by remember {
        mutableStateOf("")
    }
    LaunchedEffect(Unit) {
        viewModel.getSongs(songBookId!!)
    }
    val songs = viewModel.songs.value

    if (songs.isEmpty()) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
    } else {

        setScaffold(topBar = {
            TopBar(title = songs[0].songBook.name!!, isMainScreen = false, actions = {
                IconButton(onClick = { searchState = !searchState }) {
                    Icon(imageVector = Icons.Default.Sort,
                        contentDescription = "Search",
                        modifier = Modifier)
                }
                IconButton(onClick = { searchState = !searchState }) {
                    Icon(imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        modifier = Modifier)
                }
            }) {
                navController.popBackStack()
            }
        })
        if (searchState) {
            Column(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(value = searchText,
                    onValueChange = { text ->
                        if (text.isEmpty()) {
                            viewModel.getSongs(songBookId!!)
                        } else {
                            searchText = text
                            val splited = text.split(" ")
                            val search =
                                if (splited.size > 1) splited.joinToString(separator = "%") else text
                            viewModel.searchSongs(search)
                        }
                    },
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(5.dp))
                        .border(border = BorderStroke(width = 1.dp,
                            color = MaterialTheme.colors.onPrimary)),
                    trailingIcon = {
                        Icon(imageVector = Icons.Default.Close,
                            contentDescription = "",
                            modifier = Modifier.clickable {
                                searchText = ""
                            })
                    })

                if (songs.isNotEmpty()) {
                    LazyColumn(content = {
                        items(songs) { data ->
                            RowSongItem(data, onFavoriteClicked = {
                                viewModel.setFavorite(data.song.id!!, !data.song.isFavorite!!)
                            }) {
                                navController.navigate(NavigationItem.SongPageScreen.setParam(data.song.id!!))
                            }
                        }
                    })
                }
            }
        } else {
            LazyColumn(content = {
                items(songs) { data ->
                    RowSongItem(data, onFavoriteClicked = {
                        viewModel.setFavorite(data.song.id!!, !data.song.isFavorite!!)
                    }) {
                        navController.navigate(NavigationItem.SongPageScreen.setParam(data.song.id!!))
                    }
                }
            })
        }
    }
}
