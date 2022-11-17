package ru.godsonpeya.atmosphere.screens.songlist

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.godsonpeya.atmosphere.navigation.NavigationItem
import ru.godsonpeya.atmosphere.widgets.TopBar
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SongListScreen(navController: NavController, songBookId: Int?, viewModel: SongListViewModel) {
    val songs = produceState(initialValue = mutableListOf()) {
        viewModel.getSongs(songBookId!!).collectLatest {
            value = it.toList()
        }
    }.value
    if (songs.isEmpty()) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
    } else {
        Scaffold(topBar = {
            TopBar(title = songs[0].songBook.name!!,
                navController = navController,
                isMainScreen = false)
        }) { inner->
            Column(modifier = Modifier.padding(inner).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                LazyColumn(content = {
                    items(songs) { data ->
                        Column {
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(NavigationItem.SongPageScreen.setParam(data.song.id!!))
                                }) {
                                Text(text = buildAnnotatedString {
                                    withStyle(style = SpanStyle()) {
                                        append(data.song.number + ". ")
                                    }
                                    withStyle(style = SpanStyle()) {
                                        append(data.song.name!!)
                                    }
                                }, modifier = Modifier.padding(20.dp))
                            }
                            Divider()
                        }
                    }
                })
            }
        }
    }
}