package com.gurpreetsk.pongwars.models

import androidx.compose.ui.graphics.Color

data class Square(
    val row: Int,
    val col: Int,
    var color: Color,
    var isClaimed: Boolean
)