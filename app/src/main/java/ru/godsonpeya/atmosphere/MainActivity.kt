package ru.godsonpeya.atmosphere

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.godsonpeya.atmosphere.navigation.AppNavigation
import ru.godsonpeya.atmosphere.theme.JetNavSampleAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            JetNavSampleAppTheme {
                AtmosphereApp()
            }
        }
    }


}

@Composable
fun AtmosphereApp() {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<AppViewModel>()

    Scaffold(topBar = viewModel.topBar.value,
        bottomBar = viewModel.bottomBar.value,
        scaffoldState = rememberScaffoldState(),
        drawerContent = { viewModel.drawerContent.value }
    ) { inner ->
        Surface(modifier = Modifier
            .padding(inner)
            .fillMaxSize()) {
            Text(text = "")
            AppNavigation(navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
}