package ru.godsonpeya.atmosphere.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.godsonpeya.atmosphere.screens.manager.SongbookManagerScreen
import ru.godsonpeya.atmosphere.screens.manager.SongbookManagerViewModel
import ru.godsonpeya.atmosphere.screens.songbook.MainScreen
import ru.godsonpeya.atmosphere.screens.songbook.MainViewModel
import ru.godsonpeya.atmosphere.screens.songlist.SongListScreen
import ru.godsonpeya.atmosphere.screens.songlist.SongListViewModel
import ru.godsonpeya.atmosphere.screens.songpage.SongPageScreen
import ru.godsonpeya.atmosphere.screens.songpage.SongPageViewModel


@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController,
        startDestination = NavigationItem.MainScreen.getFullRoute()) {
        composable(NavigationItem.MainScreen.getFullRoute()) {
            val viewModel = hiltViewModel<MainViewModel>()
            MainScreen(navController = navController, viewModel = viewModel)
        }
        composable(route = NavigationItem.SongListScreen.getFullRoute() + "/{songBookId}",
            arguments = listOf(navArgument("songBookId") {
                type = NavType.IntType
            })) { entry ->
            val viewModel = hiltViewModel<SongListViewModel>()
            SongListScreen(
                navController = navController,
                songBookId = entry.arguments?.getInt("songBookId"),
                viewModel = viewModel,
            )
        }
        composable(route = NavigationItem.SongPageScreen.getFullRoute() + "/{songId}",
            arguments = listOf(navArgument("songId") {
                type = NavType.IntType
            })) { entry ->
            val viewModel = hiltViewModel<SongPageViewModel>()
            SongPageScreen(navController = navController,
                songId = entry.arguments?.getInt("songId"),
                viewModel = viewModel)
        }
        composable(NavigationItem.ManagerScreen.getFullRoute()) {
            val viewModel = hiltViewModel<SongbookManagerViewModel>()
            SongbookManagerScreen(navController = navController, viewModel = viewModel)
        }
    }
}