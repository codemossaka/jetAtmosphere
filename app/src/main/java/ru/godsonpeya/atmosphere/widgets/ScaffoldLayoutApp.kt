package ru.godsonpeya.atmosphere.widgets

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object ScaffoldLayoutApp {
    private val topBar: MutableStateFlow<@Composable () -> Unit> = MutableStateFlow {}
    private val bottomBar: MutableStateFlow<@Composable () -> Unit> = MutableStateFlow {}
    private val drawerContent: MutableStateFlow<@Composable () -> Unit> = MutableStateFlow {}

    val watchTopBar = topBar.asStateFlow()
    val watchBottomBar = bottomBar.asStateFlow()
    val watchDrawerContent = drawerContent.asStateFlow()

    fun setScaffold(
        topBar: @Composable () -> Unit = {},
        bottomBar: @Composable () -> Unit = {},
        drawerContent: @Composable () -> Unit = {},
    ) {
        this.topBar.value = topBar
        this.bottomBar.value = bottomBar
        this.drawerContent.value = drawerContent
    }
}
