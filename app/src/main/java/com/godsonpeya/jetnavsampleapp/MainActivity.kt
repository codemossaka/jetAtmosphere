package com.godsonpeya.jetnavsampleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.godsonpeya.jetnavsampleapp.navigation.AppScreens
import com.godsonpeya.jetnavsampleapp.screens.songpage.SongPageScreen
import com.godsonpeya.jetnavsampleapp.screens.songlist.SongListScreen
import com.godsonpeya.jetnavsampleapp.screens.songlist.SongListViewModel
import com.godsonpeya.jetnavsampleapp.screens.songbook.MainScreen
import com.godsonpeya.jetnavsampleapp.screens.songbook.MainViewModel
import com.godsonpeya.jetnavsampleapp.screens.songpage.SongPageViewModel
import com.godsonpeya.jetnavsampleapp.ui.theme.JetNavSampleAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetNavSampleAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {

                    val navController = rememberNavController()
                    MainContent(navController)
                }
            }
        }
    }
}

@Composable
fun MainContent(navController: NavHostController) {
    NavHost(navController = navController, startDestination = AppScreens.MainScreen.name) {
        composable(AppScreens.MainScreen.name) {
            val viewModel = hiltViewModel<MainViewModel>()
            MainScreen(navController = navController, viewModel = viewModel)
        }
        composable(AppScreens.SongListScreen.name + "/{songBookId}") { entry ->
            val viewModel = hiltViewModel<SongListViewModel>()
            SongListScreen(navController = navController,
                songBookId = entry.arguments?.getString("songBookId")?.toInt(),
                viewModel = viewModel)
        }
        composable(AppScreens.SongPageScreen.name + "/{songId}") { entry ->
            val viewModel = hiltViewModel<SongPageViewModel>()
            SongPageScreen(navController = navController,
                songId = entry.arguments?.getString("songId")?.toInt(),
                viewModel = viewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetNavSampleAppTheme {}
}