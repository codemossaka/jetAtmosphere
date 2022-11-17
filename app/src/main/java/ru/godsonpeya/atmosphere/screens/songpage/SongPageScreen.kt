package ru.godsonpeya.atmosphere.screens.songpage

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.godsonpeya.atmosphere.widgets.TopBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SongPageScreen(navController: NavHostController, songId: Int?, viewModel: SongPageViewModel) {

    LaunchedEffect(Unit) {
        viewModel.getSong(songId!!)
    }

    val result = viewModel.song
    Scaffold(topBar = {
        TopBar(title = "", navController = navController)
    }) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(modifier = Modifier.padding(vertical = 13.dp)) {
                Text(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 19.sp)) {
                        append(result.song.name + "\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 13.sp)) {
                        result.songBook.name?.let { append(it) }
                    }
                }, textAlign = TextAlign.Center)
            }
            Divider()
            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, content = {
                items(result.verses) { verse ->
                    Text(text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            fontSize = 19.sp,
                            fontStyle = if (verse.isRefrain) FontStyle.Italic else FontStyle.Normal,
                            fontWeight = if (verse.isRefrain) FontWeight.Bold else FontWeight.Normal,
                        )) {
                            verse.line?.replace("\\n", "\r\n")?.let { append(it) }
                        }
                    },
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 89.dp))
                }
            })
        }
    }
}