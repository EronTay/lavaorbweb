package com.example.lavaorbweb.model

import androidx.compose.ui.graphics.ImageBitmap

data class Orb(
    val bitmap: ImageBitmap = ImageBitmap(1, 1),
    val x: Float = 0f,
    val y: Float = 0f,
    val rotation: Float = 0f,
)