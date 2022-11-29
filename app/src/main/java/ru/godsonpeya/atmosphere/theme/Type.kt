package ru.godsonpeya.atmosphere.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.godsonpeya.atmosphere.R

val cormorantinfant = FontFamily(
    Font(R.font.cormorantinfant_regular, FontWeight.Normal),
    Font(R.font.cormorantinfant_bold, FontWeight.Bold),
    Font(R.font.cormorantinfant_light, FontWeight.Light),
    Font(R.font.cormorantinfant_medium, FontWeight.Medium),
    Font(R.font.cormorantinfant_semibold, FontWeight.SemiBold),
    Font(R.font.cormorantinfant_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.cormorantinfant_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.cormorantinfant_mediumitalic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.cormorantinfant_bolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.cormorantinfant_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)