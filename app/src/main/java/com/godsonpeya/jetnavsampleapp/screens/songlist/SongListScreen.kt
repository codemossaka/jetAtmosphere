package com.godsonpeya.jetnavsampleapp.screens.songlist

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.godsonpeya.jetnavsampleapp.model.Songs
import com.godsonpeya.jetnavsampleapp.navigation.AppScreens
import com.godsonpeya.jetweatherforcast.data.DataOrException

@Composable
fun SongListScreen(navController: NavController, songBookId: Int?, viewModel: SongListViewModel) {
    val songs =
        produceState<DataOrException<Songs, Boolean, Exception>>(initialValue = DataOrException(
            loading = true)) {
            value = viewModel.getSongs(songBookId!!)
        }.value


    if (songs.loading == true) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    } else if (songs.data != null) {
        Surface(modifier = Modifier.fillMaxSize()) {
            LazyColumn(content = {
                items(songs.data!!) { song ->
                    Surface(modifier = Modifier
                        .fillMaxWidth()
                        .border(width = 1.dp, color = Color.Gray)
                    .clickable {  navController.navigate(AppScreens.SongPageScreen.name + "/${song.id}") }
                    ) {
                        Text(text = song.name, modifier = Modifier.padding(10.dp))
                    }
                }
            })
        }
    }
}