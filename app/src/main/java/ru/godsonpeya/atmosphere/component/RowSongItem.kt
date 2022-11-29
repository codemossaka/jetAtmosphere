package ru.godsonpeya.atmosphere.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Note
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import ru.godsonpeya.atmosphere.R
import ru.godsonpeya.atmosphere.data.local.entity.SongWithVerses
import ru.godsonpeya.atmosphere.utils.formatFavorite
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RowSongItem(data: SongWithVerses, onFavoriteClicked: () -> Unit, onRowSongClicked: () -> Unit) {
    val squareSize = 60.dp

    val swipeableState = rememberSwipeableState(initialValue = 0) {
        false
    }
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    val anchors = mapOf(0f to 0, -sizePx to 10)

    val paddingSong by remember {
        mutableStateOf(20)
    }
    val paddingIcons by remember {
        mutableStateOf(paddingSong / 5)
    }
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.primary)
                .swipeable(state = swipeableState, anchors = anchors, thresholds = { _, _ ->
                    FractionalThreshold(0.3f)
                }, orientation = Orientation.Horizontal)) {
                Box {
                    Row(horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(paddingIcons.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = {},
                        ) {
                            Icon(Icons.Filled.Edit, contentDescription = "Edit", tint = Color.White)
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        IconButton(
                            onClick = {},
                        ) {
                            Icon(if (data.song.isFavorite == true) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = Color.Red)
                        }
                    }
                }
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .offset {
                        val offsetValue = swipeableState.offset.value.roundToInt()
                        if (offsetValue != 0) {
                            if (offsetValue <= -181) {
                                onFavoriteClicked.invoke()
                            }
                        }
                        IntOffset(offsetValue, 0)
                    }, elevation = 5.dp, shape = RoundedCornerShape(10.dp)) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onRowSongClicked.invoke()
                        }
                        .padding(paddingSong.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(text = buildAnnotatedString {
                            withStyle(style = SpanStyle()) {
                                append(data.song.number + ". ")
                            }
                            withStyle(style = SpanStyle()) {
                                append(data.song.name!!)
                            }
                        })
                        if (data.song.audio!!) {
                            Icon(painter = painterResource(id = R.drawable.ic_singer),
                                contentDescription = "audio",
                                modifier = Modifier
                                    .padding(top = 0.dp)
                                    .size(13.dp),
                                tint = Color(0xFF4C41E2))
                        }
                    }
                }
            }
        }
        Divider()
    }
}