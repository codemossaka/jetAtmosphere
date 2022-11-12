package com.godsonpeya.jetnavsampleapp.screens.songpage

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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.godsonpeya.jetnavsampleapp.model.SongsItem
import com.godsonpeya.jetweatherforcast.data.DataOrException

@Composable
fun SongPageScreen(navController: NavHostController, songId: Int?, viewModel: SongPageViewModel) {
    val result =
        produceState<DataOrException<SongsItem, Boolean, Exception>>(initialValue = DataOrException(
            loading = true)) {
            value = viewModel.getSong(songId!!)
            value.loading = false
        }.value

    if (result.loading == true) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
    } else if (result.data != null) {
        val song = result.data
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(modifier = Modifier.padding(10.dp)) {
                Text(text = song!!.name)

            }
            Divider()
            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, content = {
                items(song!!.verses) { verse ->
                    Text(text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize = 19.sp)) {
                            append(verse.line.replace("\\n", "\r\n"))
                        }
                    }, textAlign = TextAlign.Center, modifier = Modifier.padding(5.dp))
                }
            })
        }

    }
}