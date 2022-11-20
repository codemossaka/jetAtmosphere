package ru.godsonpeya.atmosphere.component

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.godsonpeya.atmosphere.R
import ru.godsonpeya.atmosphere.data.local.entity.LanguageWithSongBook
import ru.godsonpeya.atmosphere.data.local.entity.SongBook
import ru.godsonpeya.atmosphere.screens.manager.SongbookManagerViewModel
import ru.godsonpeya.atmosphere.utils.Constants

@Composable
fun ExpandableLanguageRow(
    songBookItem: LanguageWithSongBook,
    onCardArrowClick: () -> Unit,
    expanded: Boolean,
    expandableSongBookRow: @Composable ()-> Unit
) {
    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }
    val transition = updateTransition(targetState = transitionState, label = "transition")

    val cardElevation by transition.animateDp({
        tween(durationMillis = Constants.ExpandAnimation)
    }, label = "elevationTransition") { _ ->
        if (expanded) 20.dp else 5.dp
    }

    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = Constants.ExpandAnimation)
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

            expandableSongBookRow.invoke()
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
    onSongBookClicked: (songBook: SongBook)-> Unit,
) {
    val enterFadeIn = remember {
        fadeIn(animationSpec = TweenSpec(durationMillis = Constants.FadeInAnimation,
            easing = FastOutLinearInEasing))
    }
    val enterExpand = remember {
        expandVertically(animationSpec = tween(Constants.ExpandAnimation))
    }
    val exitFadeOut = remember {
        fadeOut(animationSpec = TweenSpec(durationMillis = Constants.FadeOutAnimation,
            easing = LinearOutSlowInEasing))
    }
    val exitCollapse = remember {
        shrinkVertically(animationSpec = tween(Constants.CollapseAnimation))
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
                                onSongBookClicked.invoke(songBook)
                            })
                }
            }
        }
    }
}