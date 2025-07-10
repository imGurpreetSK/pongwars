package com.gurpreetsk.pongwars.models

import kotlin.test.*

internal class BallInitializationTest {
    
    @Test
    fun `balls initialize at correct positions with inverted colors`() {
        val gameState = GameState()
        gameState.initializeGrid(10, 20)
        
        val balls = gameState.balls
        assertEquals(2, balls.size)
        
        // Left ball (has right side)
        val leftBall = balls[0]
        assertEquals(Side.RIGHT, leftBall.side)
        assertEquals(5.0f, leftBall.x.floatValue) // 20 * 0.25
        assertEquals(5.0f, leftBall.y.floatValue) // 10 * 0.5
        assertTrue(leftBall.velocityX > 0) // Moving right
        
        // Right ball (has left side)
        val rightBall = balls[1]
        assertEquals(Side.LEFT, rightBall.side)
        assertEquals(15.0f, rightBall.x.floatValue) // 20 * 0.75
        assertEquals(5.0f, rightBall.y.floatValue) // 10 * 0.5
        assertTrue(rightBall.velocityX < 0) // Moving left
    }
}