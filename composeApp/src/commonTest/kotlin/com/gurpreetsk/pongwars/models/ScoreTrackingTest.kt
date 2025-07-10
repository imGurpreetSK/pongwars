package com.gurpreetsk.pongwars.models

import androidx.compose.runtime.mutableFloatStateOf
import kotlin.test.*

internal class ScoreTrackingTest {
    
    @Test
    fun `scores update correctly after square flipping`() {
        val gameState = GameState()
        gameState.initializeGrid(4, 4)
        
        // Initially each side has 8 squares
        assertEquals(8, gameState.leftScore.value)
        assertEquals(8, gameState.rightScore.value)
        
        // Create a ball that will flip a square
        val testBall = Ball(
            x = mutableFloatStateOf(0.5f),
            y = mutableFloatStateOf(0.5f),
            velocityX = 0.1f,
            velocityY = 0.1f,
            side = Side.LEFT
        )
        gameState.balls.clear()
        gameState.balls.add(testBall)
        
        // Make square at (0, 0) same side as ball (left side)
        val targetSquare = gameState.squares.find { it.row == 0 && it.col == 0 }!!
        targetSquare.side = Side.LEFT
        
        gameState.updateBalls()
        
        // Square should flip to right side, updating scores
        assertEquals(7, gameState.leftScore.value)
        assertEquals(9, gameState.rightScore.value)
    }
}