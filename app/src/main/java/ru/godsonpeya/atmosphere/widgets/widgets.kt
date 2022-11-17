package ru.godsonpeya.atmosphere.widgets

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
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
import androidx.navigation.NavController
import ru.godsonpeya.atmosphere.R
import ru.godsonpeya.atmosphere.data.local.entity.ApiStatus
import ru.godsonpeya.atmosphere.data.local.entity.LanguageWithSongBook
import ru.godsonpeya.atmosphere.data.local.entity.SongBook
import ru.godsonpeya.atmosphere.screens.manager.SongbookManagerViewModel
import ru.godsonpeya.atmosphere.theme.Purple500
import ru.godsonpeya.atmosphere.utils.Constants.CollapseAnimation
import ru.godsonpeya.atmosphere.utils.Constants.ExpandAnimation
import ru.godsonpeya.atmosphere.utils.Constants.FadeInAnimation
import ru.godsonpeya.atmosphere.utils.Constants.FadeOutAnimation
import ru.godsonpeya.atmosphere.utils.SongBookEventBroadcast

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
fun ExpandableCard(
    songBookItem: LanguageWithSongBook,
    onCardArrowClick: () -> Unit,
    expanded: Boolean,
    viewModel: SongbookManagerViewModel,
) {
    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }
    val transition = updateTransition(targetState = transitionState, label = "transition")

    val cardElevation by transition.animateDp({
        tween(durationMillis = ExpandAnimation)
    }, label = "elevationTransition") { _ ->
        if (expanded) 20.dp else 5.dp
    }

    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = ExpandAnimation)
    }, label = "rotationDegreeTransition") { _ ->
        if (expanded) 0f else 180f
    }

    Surface(elevation = cardElevation, modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(0.85f)) {
                        Text(text = songBookItem.language.name!!,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp))
                    }
                    Column(modifier = Modifier.weight(0.15f)) {
                        CardArrow(degrees = arrowRotationDegree, onClick = onCardArrowClick)
                    }
                }
            }
            ExpandableSongBookRow(expanded = expanded,
                songBooks = songBookItem.songbooks,
                viewModel = viewModel)
        }
    }
}

@Composable
fun CardArrow(
    degrees: Float,
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick, content = {
        Icon(painter = painterResource(id = R.drawable.ic_baseline_expand_less_24),
            contentDescription = "Expandable Arrow",
            modifier = Modifier.rotate(degrees))
    })
}


@Composable
fun ExpandableSongBookRow(
    expanded: Boolean = true,
    songBooks: List<SongBook>,
    viewModel: SongbookManagerViewModel,
) {
    val enterFadeIn = remember {
        fadeIn(animationSpec = TweenSpec(durationMillis = FadeInAnimation,
            easing = FastOutLinearInEasing))
    }
    val enterExpand = remember {
        expandVertically(animationSpec = tween(ExpandAnimation))
    }
    val exitFadeOut = remember {
        fadeOut(animationSpec = TweenSpec(durationMillis = FadeOutAnimation,
            easing = LinearOutSlowInEasing))
    }
    val exitCollapse = remember {
        shrinkVertically(animationSpec = tween(CollapseAnimation))
    }

    AnimatedVisibility(visible = expanded,
        enter = enterExpand + enterFadeIn,
        exit = exitCollapse + exitFadeOut) {
        Column(modifier = Modifier.fillMaxWidth()) {
            songBooks.forEach { songBook ->
                Divider()
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = songBook.name!!,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .weight(6f),

                        )
                    Icon(painter = painterResource(id = if (songBook.isDownLoaded) R.drawable.ic_download_done else R.drawable.ic_download),
                        contentDescription = "ddd",
                        modifier = Modifier
                            .size(20.dp)
                            .weight(1f)
                            .clickable {
                                if (!songBook.isDownLoaded) viewModel.downLoadSongBook(songBook) else viewModel.deleteSongBook(
                                    songBook.id!!)
                            })
                }
            }
        }
    }
}

@Composable
fun LoaderView(
    status: MutableState<ApiStatus>,
    openDialog: MutableState<Boolean> = mutableStateOf(false),
    songBook: State<SongBook> = mutableStateOf(SongBook(name = "Updating database")),
) {
    if (openDialog.value) {
        AlertDialog(onDismissRequest = {
            if (status.value != ApiStatus.LOADING) {
                closeAlertDialog(openDialog)
            }
        }, title = {
            Text(text = songBook.value.name!!,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold)
        }, text = {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                when (status.value) {
                    ApiStatus.LOADING -> CircularProgressIndicator()
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

        }, buttons = {
            Row(modifier = Modifier.padding(all = 8.dp),
                horizontalArrangement = Arrangement.Center) {
                Column(modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Divider()
                    Text(text = "Cancel", modifier = Modifier
                        .padding(14.dp)
                        .clickable {
                            if (status.value != ApiStatus.LOADING) {
                                closeAlertDialog(openDialog)
                            }
                        }, color = MaterialTheme.colors.primary)
                }
            }
        })
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
    else -> mutableStateOf(false)
}

@Composable
fun TopBar(title: String, navController: NavController, isMainScreen: Boolean = false) {
    TopAppBar(title = { Text(text = title, textAlign = TextAlign.Center) }, navigationIcon = {
        Icon(imageVector = if (isMainScreen) Icons.Default.Menu else Icons.Default.ArrowBack,
            contentDescription = "ddd",
            modifier = Modifier.clickable {
                navController.popBackStack()
            })
    })
}

