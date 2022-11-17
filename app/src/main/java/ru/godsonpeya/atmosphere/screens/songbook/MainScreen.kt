package ru.godsonpeya.atmosphere.screens.songbook

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import ru.godsonpeya.atmosphere.component.Drawer
import ru.godsonpeya.atmosphere.data.local.entity.ApiStatus
import ru.godsonpeya.atmosphere.data.local.entity.SongBook
import ru.godsonpeya.atmosphere.navigation.NavigationItem
import ru.godsonpeya.atmosphere.widgets.BottomNavItem
import ru.godsonpeya.atmosphere.widgets.LoaderView
import ru.godsonpeya.atmosphere.widgets.setOpenDialog


@Composable
fun MainScreen(viewModel: MainViewModel, navController: NavHostController) {
    val result = viewModel.songBooks.collectAsState().value
    val status = viewModel.status

    val scope = rememberCoroutineScope()
    val scaffoldState =
        rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Closed))


    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Songbooks") }, navigationIcon = {
            Icon(imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier.clickable {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                })
        }, actions = {
            Icon(imageVector = Icons.Default.Add,
                contentDescription = "ddd",
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clickable {
                        navController.navigate(NavigationItem.ManagerScreen.getFullRoute())
                    })
            Icon(imageVector = Icons.Filled.Refresh,
                contentDescription = "ddd",
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clickable {
                        viewModel.syncData()
                    })
        })
    }, bottomBar = {
        BottomAppBar {
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                BottomNavItem(imageVector = Icons.Default.Home) {

                }
                BottomNavItem(imageVector = Icons.Default.Home) {

                }
            }
        }
    }, scaffoldState = scaffoldState, drawerContent = {
        Drawer(songBooks = viewModel.songBooks.collectAsState()) { title, route ->
            scope.launch {
                scaffoldState.drawerState.close()
            }
//                    Constant.title = title
//                    Toast.makeText(context, title, Toast.LENGTH_SHORT).show()
            navController.navigate(route = route)
        }
    }) { innerPadding ->

        if (status.value!=ApiStatus.IDLE) {
            LoaderView(status = status, openDialog = setOpenDialog(status))
        }
        if (result.isEmpty()) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Text(text = "No songbook download")
            }
        } else {
            Surface(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)) {
                LazyColumn(content = {
                    items(result) { item ->
                        Column {
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(NavigationItem.SongListScreen.setParam(
                                        item.songbook.id))
                                }) {
                                Text(text = buildAnnotatedString {
                                    withStyle(style = SpanStyle()) {
                                        append(item.songbook.id.toString() + ". ")
                                    }
                                    withStyle(style = SpanStyle()) {
                                        append(item.songbook.name!!)
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