package com.godsonpeya.jetnavsampleapp.screens.songlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.godsonpeya.jetnavsampleapp.model.Songs
import com.godsonpeya.jetnavsampleapp.navigation.AppScreens
import com.godsonpeya.jetweatherforcast.data.DataOrException

@Composable
fun SongListScreen(navController: NavController, songBookId: Int?, viewModel: SongListViewModel) {
    val result =
        produceState<DataOrException<Songs, Boolean, Exception>>(initialValue = DataOrException(
            loading = true)) {
            value = viewModel.getSongs(songBookId!!)
        }.value


    if (result.loading == true) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
    } else if (result.data != null) {
        val songs: Songs = result.data!!
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(modifier = Modifier.padding(vertical = 17.dp)) {
                Text(text =songs[0].songBook.name, textAlign = TextAlign.Center)
            }
            Divider()
            LazyColumn(content = {
                items(songs) { song ->
                    Column {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navController.navigate(AppScreens.SongPageScreen.name + "/${song.id}") }) {
                            Text(text = buildAnnotatedString {
                                withStyle(style = SpanStyle()) {
                                    append(song.number + ". ")
                                }
                                withStyle(style = SpanStyle()) {
                                    append(song.name)
                                }
                            }, modifier = Modifier.padding(20.dp))
                        }
                        Divider()
                    }
//                    Surface(modifier = Modifier
//                        .fillMaxWidth()
//                        .border(width = 1.dp, color = Color.Gray)
//                    .clickable {  navController.navigate(AppScreens.SongPageScreen.name + "/${song.id}") }
//                    ) {
//                        Text(text = song.name, modifier = Modifier.padding(10.dp))
//                    }
                }
            })
        }
    }
}