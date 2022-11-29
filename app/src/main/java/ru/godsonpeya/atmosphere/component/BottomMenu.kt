package ru.godsonpeya.atmosphere.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import ru.godsonpeya.atmosphere.navigation.NavigationItem
import ru.godsonpeya.atmosphere.widgets.BottomNavItem

@Composable
fun BottomMenu(navController: NavHostController) {
    BottomAppBar {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            BottomNavItem(imageVector = Icons.Default.Home) {
                navController.navigate(NavigationItem.MainScreen.getFullRoute())
            }
            BottomNavItem(imageVector = Icons.Default.FavoriteBorder) {
                navController.navigate(NavigationItem.FavoriteScreen.getFullRoute())
            }
        }
    }
}