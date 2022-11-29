package ru.godsonpeya.atmosphere.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import ru.godsonpeya.atmosphere.component.BottomMenu
import ru.godsonpeya.atmosphere.component.Drawer
import ru.godsonpeya.atmosphere.component.RowSongBookItem
import ru.godsonpeya.atmosphere.data.local.entity.ApiStatus
import ru.godsonpeya.atmosphere.navigation.NavigationItem
import ru.godsonpeya.atmosphere.widgets.LoaderView
import ru.godsonpeya.atmosphere.widgets.ScaffoldLayoutApp.setScaffold
import ru.godsonpeya.atmosphere.widgets.setOpenDialog

@Composable
fun MainScreen(viewModel: MainViewModel, navController: NavHostController) {
    val result = viewModel.songBooks.collectAsState().value
    val status = viewModel.status

    val scope = rememberCoroutineScope()
    val scaffoldState =
        rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Closed))

    setScaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Songbooks") }, navigationIcon = {
                IconButton(onClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            modifier = Modifier)
                    }
                }
            }, actions = {
                IconButton(onClick = { navController.navigate(NavigationItem.ManagerScreen.getFullRoute()) }) {
                    Icon(imageVector = Icons.Filled.AddCircle,
                        contentDescription = "ddd",
                        modifier = Modifier)
                }
            })
        },
        bottomBar = {
            BottomMenu(navController=navController)
        },
        {
            Drawer(songBooks = viewModel.songBooks.collectAsState()) { title, route ->
                scope.launch {
                    scaffoldState.drawerState.close()
                }
                navController.navigate(route = route)
            }
        },
    )

    if (status.value != ApiStatus.IDLE) {
        LoaderView(status = status, openDialog = setOpenDialog(status)) {
            viewModel.cancelSync()
        }
    }
    if (result.isEmpty()) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(text = "No songbook download")
        }
    } else {
        Surface(modifier = Modifier.fillMaxSize()) {
            LazyColumn(content = {
                items(result) { item ->
                    RowSongBookItem(item,
                        onUpdateSongBook = { viewModel.updateSongBook(item.songbook.id!!) },
                        onDeleteSongBook = { viewModel.deleteSongBook(item.songbook.id!!) }) {
                        navController.navigate(NavigationItem.SongListScreen.getFullRoute() + "/${item.songbook.id}")
                    }
//                    Column {
//                        Row(modifier = Modifier
//                            .fillMaxWidth()
//                            .clickable {
//                                navController.navigate(NavigationItem.SongListScreen.getFullRoute() + "/${item.songbook.id}")
//                            }) {
//                            Text(text = buildAnnotatedString {
//                                withStyle(style = SpanStyle()) {
//                                    append(item.songbook.id.toString() + ". ")
//                                }
//                                withStyle(style = SpanStyle()) {
//                                    append(item.songbook.name!!)
//                                }
//                            }, modifier = Modifier.padding(20.dp))
//                        }
//                        Divider()
//                    }
                }
            })
        }

//        }
    }
}