package com.gurpreetsk.pongwars.models

import androidx.compose.ui.graphics.Color
import kotlin.test.*

internal class GameStateTest {

    private val leftColor = Color(3, 252, 219)
    private val rightColor = Color(2, 122, 107)
    
    @Test
    fun `game state initializes with empty squares and balls`() {
        val gameState = GameState(leftColor, rightColor)
        
        assertTrue(gameState.squares.isEmpty())
        assertTrue(gameState.balls.isEmpty())
        assertEquals(0, gameState.leftScore.value)
        assertEquals(0, gameState.rightScore.value)
    }
}