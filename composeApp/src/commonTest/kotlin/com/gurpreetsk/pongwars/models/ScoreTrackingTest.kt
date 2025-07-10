package com.gurpreetsk.pongwars.models

import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.graphics.Color
import kotlin.test.*

internal class ScoreTrackingTest {
    
    private val leftColor = Color(3, 252, 219)
    private val rightColor = Color(2, 122, 107)
    
    @Test
    fun `scores update correctly after square flipping`() {
        val gameState = GameState(leftColor, rightColor)
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
            color = leftColor
        )
        gameState.balls.clear()
        gameState.balls.add(testBall)
        
        // Make square at (0, 0) same color as ball (left color)
        val targetSquare = gameState.squares.find { it.row == 0 && it.col == 0 }!!
        targetSquare.color = leftColor
        
        gameState.updateBalls()
        
        // Square should flip to right color, updating scores
        assertEquals(7, gameState.leftScore.value)
        assertEquals(9, gameState.rightScore.value)
    }
}