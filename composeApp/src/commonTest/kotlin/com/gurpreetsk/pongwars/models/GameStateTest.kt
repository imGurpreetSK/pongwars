package com.gurpreetsk.pongwars.models

import kotlin.test.*

internal class GameStateTest {
    
    @Test
    fun `game state initializes with empty squares and balls`() {
        val gameState = GameState()
        
        assertTrue(gameState.squares.isEmpty())
        assertTrue(gameState.balls.isEmpty())
        assertEquals(0, gameState.leftScore.value)
        assertEquals(0, gameState.rightScore.value)
    }
}