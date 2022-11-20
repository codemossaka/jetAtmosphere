package ru.godsonpeya.atmosphere.screens.songpage

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Translate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import ru.godsonpeya.atmosphere.widgets.ScaffoldLayoutApp
import ru.godsonpeya.atmosphere.widgets.ScaffoldLayoutApp.setScaffold
import ru.godsonpeya.atmosphere.widgets.TopBar


@Composable
fun SongPageScreen(navController: NavHostController, songId: Int?, viewModel: SongPageViewModel) {
    val menuExpanded = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.getSong(songId!!)
    }

    val result = viewModel.song.value
    if (result.song.code != null) {
        viewModel.getLanguages(result)
    }
    setScaffold(
        topBar = {
            TopBar(title = "", actions = {
                if (viewModel.languages.value.isNotEmpty()) {
                    IconButton(onClick = {
                        menuExpanded.value = true

                    }) {
                        Icon(imageVector = Icons.Default.Translate,
                            contentDescription = "Translate")
                    }
                }
                Column(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                    DropdownMenu(expanded = menuExpanded.value, onDismissRequest = {
                        menuExpanded.value = false
                    }, modifier = Modifier
                        .width(200.dp)
                        .wrapContentSize(Alignment.TopStart)) {
                        viewModel.languages.value.forEach { language ->
                            DropdownMenuItem(onClick = {
                                menuExpanded.value = false
                                viewModel.getAnalog(language.id!!, result.song.code!!)
                            }) {
                                language.name?.let { Text(text = it) }
                            }
                        }
                    }
                }
            }) {
                navController.popBackStack()
            }
        },
    )

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
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
                }, textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 89.dp))
            }
        })
    }

}