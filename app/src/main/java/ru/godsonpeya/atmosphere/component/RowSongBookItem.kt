package ru.godsonpeya.atmosphere.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import ru.godsonpeya.atmosphere.data.local.entity.SongBookWithLanguage
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RowSongBookItem(
    data: SongBookWithLanguage,
    onUpdateSongBook: () -> Unit,
    onDeleteSongBook: () -> Unit,
    onSongBookClicked: () -> Unit,
) {
    val squareSize = 60.dp
    val swipeableState = rememberSwipeableState(initialValue = 0) {
        false
    }
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    val anchors = mapOf(0f to 0, sizePx to 1, -sizePx to 10)
    val openDialog = remember { mutableStateOf(false) }

    if (openDialog.value) {
        AlertDialog(title = {
            Text(text = "Do you want to delete?",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth())
        }, onDismissRequest = { /*TODO*/ }, text = {

        }, buttons = {
            Surface(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp)) {
                Row(horizontalArrangement = Arrangement.End) {
                    Button(onClick = {
                        openDialog.value = false
                    }, border = BorderStroke(width = 1.dp, color = Color.Gray)) {
                        Text(text = "No")
                    }
                    Button(onClick = {
                        onDeleteSongBook.invoke()
                        openDialog.value = false
                    },
                        border = BorderStroke(width = 1.dp, color = Color.Gray),
                        modifier = Modifier.padding(horizontal = 14.dp)) {
                        Text(text = "Ok")
                    }
                }
            }
        })
    }
    val paddingSong by remember {
        mutableStateOf(20)
    }
    val paddingIcons by remember {
        mutableStateOf(paddingSong / 5)
    }
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.primary)
            .swipeable(state = swipeableState, anchors = anchors, thresholds = { from, to ->
                FractionalThreshold(0.3f)
            }, orientation = Orientation.Horizontal)) {
            Box {
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(paddingIcons.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = {

                        },
                    ) {
                        Icon(
                            Icons.Outlined.Refresh,
                            contentDescription = "Refresh",
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))
                    Row() {

                        IconButton(
                            onClick = {
                                openDialog.value = true
                            },
                        ) {
                            Icon(Icons.Outlined.DeleteOutline, contentDescription = "Delete")
                        }
                    }
                }
            }
            Card(modifier = Modifier
                .fillMaxWidth()
                .offset {
                    val offsetValue =  swipeableState.offset.value.roundToInt()
                    if (offsetValue != 0) {
                        if (offsetValue >= 198) {
                            onUpdateSongBook.invoke()
                        } else if (offsetValue <= -198) {
                            openDialog.value = true
                        }
                    }
                    IntOffset(offsetValue, 0)
                }, elevation = 5.dp, shape = RoundedCornerShape(10.dp)) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onSongBookClicked.invoke()
                    }
                    .padding(paddingSong.dp)
                    .background(MaterialTheme.colors.background),
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = buildAnnotatedString {
                        withStyle(style = SpanStyle()) {
                            append(data.songbook.name!!)
                        }
                    })
                }
            }
            Divider()
        }
    }
}