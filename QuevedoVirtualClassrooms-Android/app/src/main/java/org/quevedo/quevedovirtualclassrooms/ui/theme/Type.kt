package org.quevedo.quevedovirtualclassrooms.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.quevedo.quevedovirtualclassrooms.R

// Set of Material typography styles to start with
val robotoFamily = FontFamily(
    Font(R.font.roboto_black),
    Font(R.font.roboto_bold, weight = FontWeight.Bold),
    Font(R.font.roboto_thin, weight = FontWeight.Thin),
    Font(R.font.roboto_light, weight = FontWeight.Light)
)
val ralewayFamily = FontFamily(
    Font(R.font.raleway_black),
    Font(R.font.raleway_bold, weight = FontWeight.Bold),
    Font(R.font.raleway_thin, weight = FontWeight.Thin),
    Font(R.font.raleway_light, weight = FontWeight.Light),
    Font(R.font.raleway_italic, weight = FontWeight.Normal, style = FontStyle.Italic)
)
val customTypography = Typography(
    h1 = TextStyle(
      fontFamily = ralewayFamily,
      fontWeight = FontWeight.Bold,
      fontSize = 36.sp,
    ),
    h2 = TextStyle(
        fontFamily = ralewayFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp
    ),
    h3 = TextStyle(
        fontFamily = ralewayFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    h4 = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    body1 = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
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