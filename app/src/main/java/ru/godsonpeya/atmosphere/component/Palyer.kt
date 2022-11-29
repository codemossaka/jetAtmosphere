package ru.godsonpeya.atmosphere.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PauseCircle
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material.icons.outlined.SkipNext
import androidx.compose.material.icons.outlined.SkipPrevious
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun PlayerView(modifier: Modifier = Modifier) {
    val isPlaying = remember {
        mutableStateOf(false)
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
        LinearProgressIndicator(progress = .7f, modifier = Modifier.fillMaxWidth()
        )
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()) {
            Text(text = "28:12", modifier = Modifier.paddingFromBaseline(bottom = 30.dp))
            Row() {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Outlined.SkipPrevious, contentDescription = "icon")
                }
                IconButton(onClick = { isPlaying.value = !isPlaying.value }) {
                    Icon(imageVector = if (isPlaying.value) Icons.Outlined.PauseCircle else Icons.Outlined.PlayCircle,
                        contentDescription = "icon")
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Outlined.SkipNext, contentDescription = "icon")
                }
            }

            Text(text = "40:12", modifier = Modifier.paddingFromBaseline(bottom = 30.dp))
        }
    }
}