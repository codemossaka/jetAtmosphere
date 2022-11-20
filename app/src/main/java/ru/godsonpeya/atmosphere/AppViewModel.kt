package ru.godsonpeya.atmosphere

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.godsonpeya.atmosphere.widgets.ScaffoldLayoutApp

class AppViewModel:ViewModel() {

    private val _topBar:MutableState<@Composable () -> Unit> = mutableStateOf({})
    val topBar = _topBar

    private val _bottomBar:MutableState<@Composable () -> Unit> = mutableStateOf({})
    val bottomBar = _bottomBar


    private val _drawerContent:MutableState<@Composable () -> Unit> = mutableStateOf({})
    val drawerContent = _drawerContent

    init {
        watchTopBar()
        watchBottomBar()
        watchDrawerContent()
    }

    private fun watchTopBar() = viewModelScope.launch {
        ScaffoldLayoutApp.watchTopBar.collectLatest { state ->
            _topBar.value = state
        }
    }

    private fun watchBottomBar() = viewModelScope.launch {
        ScaffoldLayoutApp.watchBottomBar.collectLatest { state ->
            _bottomBar.value = state
        }
    }

    private fun watchDrawerContent() = viewModelScope.launch {
        ScaffoldLayoutApp.watchDrawerContent.collectLatest { state ->
            _drawerContent.value = state
        }
    }
}