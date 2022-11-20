package ru.godsonpeya.atmosphere.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.godsonpeya.atmosphere.R
import ru.godsonpeya.atmosphere.data.local.entity.ApiStatus
import ru.godsonpeya.atmosphere.data.local.entity.SongBook
import ru.godsonpeya.atmosphere.utils.SongBookEventBroadcast


@Composable
fun TopBar(
    title: String,
    isMainScreen: Boolean = false,
    actions: @Composable RowScope.() -> Unit = {},
    onNavigationIconClicked: () -> Unit = {},
) {
    TopAppBar(title = { Text(text = title, textAlign = TextAlign.Center) }, navigationIcon = {
        IconButton(onClick = { onNavigationIconClicked.invoke() }) {
            Icon(imageVector = if (isMainScreen) Icons.Default.Menu else Icons.Default.ArrowBack,
                contentDescription = "ddd",
                modifier = Modifier)
        }
    }, actions = actions)
}


@Preview(showBackground = true)
@Composable
fun BottomNavItem(
    imageVector: ImageVector = Icons.Default.DateRange,
    onItemClick: () -> Any = {},
) {
    Box(modifier = Modifier.clickable { onItemClick.invoke() }) {
        Icon(imageVector = imageVector,
            contentDescription = "Home menu",
            modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun LoaderView(
    status: MutableState<ApiStatus>,
    openDialog: MutableState<Boolean> = mutableStateOf(false),
    songBook: State<SongBook> = mutableStateOf(SongBook(name = "Updating database")),
    cancel: () -> Unit = {},
) {
    if (openDialog.value) {
        AlertDialog(onDismissRequest = {
            if (status.value != ApiStatus.LOADING) {
                closeAlertDialog(openDialog)
            }
        },
            title = {
                Text(text = songBook.value.name!!,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth())
            },
            text = {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                    when (status.value) {
                        ApiStatus.LOADING -> CircularProgressIndicator(color = MaterialTheme.colors.onPrimary)
                        ApiStatus.DONE -> Icon(imageVector = Icons.Default.Done,
                            contentDescription = "",
                            tint = Color(0xFF6AD384),
                            modifier = Modifier.size(60.dp))
                        ApiStatus.ERROR -> Icon(imageVector = Icons.Default.Close,
                            contentDescription = "",
                            tint = Color(0xFFFF0000),
                            modifier = Modifier.size(60.dp))
                        else -> CircularProgressIndicator()
                    }
                }

            },
            buttons = {
                Row(modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center) {
                    Column(modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Divider()
                        Text(text = if (status.value == ApiStatus.LOADING) "Cancel" else "Close",
                            modifier = Modifier
                                .padding(14.dp)
                                .clickable {
                                    if (status.value != ApiStatus.LOADING) {
                                        closeAlertDialog(openDialog)
                                    } else {
                                        cancel.invoke()
                                    }
                                },
                            color = MaterialTheme.colors.onPrimary,
                            fontWeight = FontWeight.Bold)
                    }
                }
            },
            modifier = Modifier.border(border = BorderStroke(width = 1.dp,
                color = Color(color = MaterialTheme.colors.onPrimary.value.toInt()))))
    }
}

private fun closeAlertDialog(openDialog: MutableState<Boolean>) {
    SongBookEventBroadcast.setStatus(ApiStatus.IDLE)
    openDialog.value = false
}

fun setOpenDialog(status: MutableState<ApiStatus>): MutableState<Boolean> = when (status.value) {
    ApiStatus.LOADING -> mutableStateOf(true)
    ApiStatus.DONE -> mutableStateOf(true)
    ApiStatus.ERROR -> mutableStateOf(true)
    ApiStatus.IDLE -> mutableStateOf(false)
}