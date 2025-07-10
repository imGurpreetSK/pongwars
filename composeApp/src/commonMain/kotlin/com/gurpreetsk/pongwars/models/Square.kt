package com.gurpreetsk.pongwars.models

internal data class Square(
    val row: Int,
    val col: Int,
    var side: Side,
    var isClaimed: Boolean
)
