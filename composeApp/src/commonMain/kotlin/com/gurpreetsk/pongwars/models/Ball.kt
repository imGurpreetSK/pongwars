package com.gurpreetsk.pongwars.models

import androidx.compose.runtime.MutableFloatState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal data class Ball(
    var x: MutableFloatState,
    var y: MutableFloatState,
    var velocityX: Float,
    var velocityY: Float,
    val side: Side,
    val radius: Dp = 8.dp
)
