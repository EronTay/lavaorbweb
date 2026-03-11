package com.example.lavaorbweb.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import lavaorbweb.composeapp.generated.resources.Res
import lavaorbweb.composeapp.generated.resources.cherry_bomb
import org.jetbrains.compose.resources.Font

@Composable
fun getTypography(): Typography {
    val CherryBomb = FontFamily(
        Font(Res.font.cherry_bomb)
    )
    return Typography(
        bodySmall = TextStyle(
            fontSize = 20.sp,
            color = Color(31, 13, 121),
            fontFamily = CherryBomb,
        ),
        titleLarge = TextStyle(
            fontSize = 40.sp,
            color = Color.White,
            fontFamily = CherryBomb,
        ),
        titleMedium = TextStyle(
            fontSize = 28.sp,
            color = Color.White,
            fontFamily = CherryBomb,
        )
    )
}