package com.gurpreetsk.pongwars.models

import androidx.compose.runtime.mutableFloatStateOf
import kotlin.test.*

internal class MultipleBallsTest {
    
    @Test
    fun `multiple balls update simultaneously`() {
        val gameState = GameState()
        gameState.initializeGrid(10, 10)
        
        // Clear default balls and add custom ones
        gameState.balls.clear()
        
        val ball1 = Ball(
            x = mutableFloatStateOf(2.0f),
            y = mutableFloatStateOf(2.0f),
            velocityX = 0.1f,
            velocityY = 0.1f,
            side = Side.LEFT
        )
        
        val ball2 = Ball(
            x = mutableFloatStateOf(7.0f),
            y = mutableFloatStateOf(7.0f),
            velocityX = -0.1f,
            velocityY = -0.1f,
            side = Side.RIGHT
        )
        
        gameState.balls.add(ball1)
        gameState.balls.add(ball2)
        
        val initialBall1X = ball1.x.floatValue
        val initialBall2X = ball2.x.floatValue
        
        gameState.updateBalls()
        
        // Both balls should move
        assertNotEquals(initialBall1X, ball1.x.floatValue)
        assertNotEquals(initialBall2X, ball2.x.floatValue)
    }
}