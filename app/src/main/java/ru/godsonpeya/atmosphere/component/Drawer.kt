package ru.godsonpeya.atmosphere.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.godsonpeya.atmosphere.BuildConfig
import ru.godsonpeya.atmosphere.R
import ru.godsonpeya.atmosphere.data.local.entity.SongBookWithLanguage

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    songBooks: State<List<SongBookWithLanguage>>,
    onDestinationClicked: (route: String, title: String) -> Unit,
) {
    val versionName = BuildConfig.VERSION_NAME

    Column(modifier
        .fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "v $versionName",
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        Divider(color = Color.White, thickness = 1.dp)

        SelectComponent(songBooks)
//        screenFromDrawer.forEach { screen ->
//            Spacer(modifier = Modifier.height(25.dp))
//            Row(
//                modifier = Modifier
//                    .clickable {
//                        onDestinationClicked(screen.title, screen.route)
//                    }
//                    .fillMaxWidth()
//                    .padding(10.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                Icon(screen.icon, contentDescription = "Small Icon", tint = Color.White)
//
//                Text(
//                    text = screen.title,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.White,
//                    fontSize = 20.sp
//                )
//            }
//        }
    }
}



















