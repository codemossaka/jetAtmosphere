package com.godsonpeya.jetnavsampleapp.screens.songbook

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.navigation.NavHostController
import com.godsonpeya.jetnavsampleapp.model.SongBooks
import com.godsonpeya.jetnavsampleapp.navigation.AppScreens
import com.godsonpeya.jetweatherforcast.data.DataOrException


@Composable
fun MainScreen(viewModel: MainViewModel, navController: NavHostController) {
    val songbooks =
        produceState<DataOrException<SongBooks, Boolean, Exception>>(initialValue = DataOrException(
            loading = true)) {
            value = viewModel.getSongBooks()
            value.loading = false
        }.value

    if (songbooks.loading == true) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    } else if (songbooks.data != null) {
        Surface(modifier = Modifier.fillMaxSize()) {
            LazyColumn(content = {
                items(songbooks.data!!) { songbook ->
                    Surface(modifier = Modifier
                        .fillMaxWidth()
                        .border(width = 1.dp, color = Color.Gray)
                        .clickable { navController.navigate(AppScreens.SongListScreen.name + "/${songbook.id}") }) {
                        Text(text = songbook.name, modifier = Modifier.padding(10.dp))
                    }
                }
            })
        }
    }
}