package com.gurpreetsk.pongwars.models

import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.graphics.Color
import kotlin.test.*

internal class MultipleBallsTest {
    
    private val leftColor = Color(3, 252, 219)
    private val rightColor = Color(2, 122, 107)
    
    @Test
    fun `multiple balls update simultaneously`() {
        val gameState = GameState(leftColor, rightColor)
        gameState.initializeGrid(10, 10)
        
        // Clear default balls and add custom ones
        gameState.balls.clear()
        
        val ball1 = Ball(
            x = mutableFloatStateOf(2.0f),
            y = mutableFloatStateOf(2.0f),
            velocityX = 0.1f,
            velocityY = 0.1f,
            color = leftColor
        )
        
        val ball2 = Ball(
            x = mutableFloatStateOf(7.0f),
            y = mutableFloatStateOf(7.0f),
            velocityX = -0.1f,
            velocityY = -0.1f,
            color = rightColor
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