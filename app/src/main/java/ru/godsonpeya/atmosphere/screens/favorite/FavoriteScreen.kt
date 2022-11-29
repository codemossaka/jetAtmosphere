package ru.godsonpeya.atmosphere.screens.favorite

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import ru.godsonpeya.atmosphere.component.BottomMenu
import ru.godsonpeya.atmosphere.component.RowSongItem
import ru.godsonpeya.atmosphere.navigation.NavigationItem
import ru.godsonpeya.atmosphere.widgets.ScaffoldLayoutApp
import ru.godsonpeya.atmosphere.widgets.TopBar

@Composable
fun FavoriteScreen(navController: NavHostController, viewModel: FavoriteViewModel) {

    ScaffoldLayoutApp.setScaffold(
        topBar = {
            TopBar(
                title = "Favorite",
            ) {
                navController.popBackStack()
            }
        },
        bottomBar = {
            BottomMenu(navController = navController)
        })

    LazyColumn(content = {

        items(viewModel.songs.value) { data ->
            RowSongItem(data, onFavoriteClicked = {
                viewModel.setFavorite(data.song.id!!, !data.song.isFavorite!!)
            }) {
                navController.navigate(NavigationItem.SongPageScreen.setParam(data.song.id!!))
            }
        }
    })
}