package ru.godsonpeya.atmosphere.navigation

import ru.godsonpeya.atmosphere.R

sealed class NavigationItem(var router: Route<Any>, var icon: Int, var title: String) {
    object MainScreen : NavigationItem(Route("home"), R.drawable.ic_download, "Home")
    object SongListScreen :
        NavigationItem(Route("SongListScreen", "songBookId"), R.drawable.ic_download, "SongList")

    object SongPageScreen :
        NavigationItem(Route("SongPageScreen", "songId"), R.drawable.ic_download, "Song")

    object ManagerScreen : NavigationItem(Route("ManagerScreen"), R.drawable.ic_download, "Manager")

    fun getFullRoute(): String {
        return this.router.getFullRoute()
    }

    fun getParam(): String {
        return this.router.param.toString()
    }

    fun <T> setParam(pathParam: T): String {
        return this.router.route + "/" + pathParam
    }
}

class Route<T>(val route: String, val param: T? = null ) {
    fun getFullRoute(): String {
        return "$route/{$param}"
    }
}