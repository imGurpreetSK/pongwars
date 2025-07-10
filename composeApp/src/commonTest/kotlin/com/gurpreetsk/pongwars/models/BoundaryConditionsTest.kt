package com.gurpreetsk.pongwars.models

import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.graphics.Color
import kotlin.test.*

internal class BoundaryConditionsTest {
    
    private val leftColor = Color(3, 252, 219)
    private val rightColor = Color(2, 122, 107)
    
    @Test
    fun `ball position clamped when outside boundaries`() {
        val gameState = GameState(leftColor, rightColor)
        gameState.initializeGrid(10, 10)
        
        val testBall = Ball(
            x = mutableFloatStateOf(-0.5f),
            y = mutableFloatStateOf(-0.5f),
            velocityX = -0.2f,
            velocityY = -0.2f,
            color = leftColor
        )
        gameState.balls.clear()
        gameState.balls.add(testBall)
        
        gameState.updateBalls()
        
        // Position should be clamped to 0
        assertEquals(0.0f, testBall.x.floatValue)
        assertEquals(0.0f, testBall.y.floatValue)
        
        // Velocity should be reversed
        assertTrue(testBall.velocityX > 0)
        assertTrue(testBall.velocityY > 0)
    }
    
    @Test
    fun `ball with zero velocity does not move`() {
        val gameState = GameState(leftColor, rightColor)
        gameState.initializeGrid(10, 10)
        
        val testBall = Ball(
            x = mutableFloatStateOf(5.0f),
            y = mutableFloatStateOf(5.0f),
            velocityX = 0.0f,
            velocityY = 0.0f,
            color = leftColor
        )
        gameState.balls.clear()
        gameState.balls.add(testBall)
        
        gameState.updateBalls()
        
        // Ball should not move
        assertEquals(5.0f, testBall.x.floatValue)
        assertEquals(5.0f, testBall.y.floatValue)
    }
    
    @Test
    fun `ball position coerced to valid range at boundaries`() {
        val gameState = GameState(leftColor, rightColor)
        gameState.initializeGrid(5, 5)
        
        val testBall = Ball(
            x = mutableFloatStateOf(10.0f), // Beyond grid
            y = mutableFloatStateOf(10.0f), // Beyond grid
            velocityX = 0.5f,
            velocityY = 0.5f,
            color = leftColor
        )
        gameState.balls.clear()
        gameState.balls.add(testBall)
        
        gameState.updateBalls()
        
        // Position should be clamped to grid size
        assertEquals(5.0f, testBall.x.floatValue)
        assertEquals(5.0f, testBall.y.floatValue)
        
        // Velocity should be reversed
        assertTrue(testBall.velocityX < 0)
        assertTrue(testBall.velocityY < 0)
    }
}