package com.gurpreetsk.pongwars.models

import androidx.compose.ui.graphics.Color
import kotlin.test.*

internal class BallInitializationTest {
    
    private val leftColor = Color(3, 252, 219)
    private val rightColor = Color(2, 122, 107)
    
    @Test
    fun `balls initialize at correct positions with inverted colors`() {
        val gameState = GameState(leftColor, rightColor)
        gameState.initializeGrid(10, 20)
        
        val balls = gameState.balls
        assertEquals(2, balls.size)
        
        // Left ball (has right color)
        val leftBall = balls[0]
        assertEquals(rightColor, leftBall.color)
        assertEquals(5.0f, leftBall.x.floatValue) // 20 * 0.25
        assertEquals(5.0f, leftBall.y.floatValue) // 10 * 0.5
        assertTrue(leftBall.velocityX > 0) // Moving right
        
        // Right ball (has left color)
        val rightBall = balls[1]
        assertEquals(leftColor, rightBall.color)
        assertEquals(15.0f, rightBall.x.floatValue) // 20 * 0.75
        assertEquals(5.0f, rightBall.y.floatValue) // 10 * 0.5
        assertTrue(rightBall.velocityX < 0) // Moving left
    }
}