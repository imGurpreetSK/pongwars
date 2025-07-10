package com.gurpreetsk.pongwars.models

import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.graphics.Color
import kotlin.test.*

internal class SquareCollisionsTest {
    
    private val leftColor = Color(3, 252, 219)
    private val rightColor = Color(2, 122, 107)
    
    @Test
    fun `ball flips square when hitting same color`() {
        val gameState = GameState(leftColor, rightColor)
        gameState.initializeGrid(10, 10)
        
        // Create a ball with left color at position (5, 5)
        val testBall = Ball(
            x = mutableFloatStateOf(5.5f),
            y = mutableFloatStateOf(5.5f),
            velocityX = 0.1f,
            velocityY = 0.1f,
            color = leftColor
        )
        gameState.balls.clear()
        gameState.balls.add(testBall)
        
        // Make the square at (5, 5) the same color as ball
        val targetSquare = gameState.squares.find { it.row == 5 && it.col == 5 }!!
        targetSquare.color = leftColor
        
        gameState.updateBalls()
        
        // Square should be flipped to opposite color
        assertEquals(rightColor, targetSquare.color)
        assertTrue(targetSquare.isClaimed)
    }
    
    @Test
    fun `ball does not flip square when hitting different color`() {
        val gameState = GameState(leftColor, rightColor)
        gameState.initializeGrid(10, 10)
        
        // Create a ball with left color at position (5, 5)
        val testBall = Ball(
            x = mutableFloatStateOf(5.5f),
            y = mutableFloatStateOf(5.5f),
            velocityX = 0.1f,
            velocityY = 0.1f,
            color = leftColor
        )
        gameState.balls.clear()
        gameState.balls.add(testBall)
        
        // The square at (5, 5) is right color initially
        val targetSquare = gameState.squares.find { it.row == 5 && it.col == 5 }!!
        assertEquals(rightColor, targetSquare.color)
        
        val originalVelocityX = testBall.velocityX
        val originalVelocityY = testBall.velocityY
        
        gameState.updateBalls()
        
        // Square should NOT be flipped (different colors)
        assertEquals(rightColor, targetSquare.color)
        assertFalse(targetSquare.isClaimed)
        
        // Ball should not bounce
        assertEquals(originalVelocityX, testBall.velocityX)
        assertEquals(originalVelocityY, testBall.velocityY)
    }
    
    @Test
    fun `ball bounces horizontally when hitting square from side`() {
        val gameState = GameState(leftColor, rightColor)
        gameState.initializeGrid(10, 10)
        
        // Test horizontal bounce
        val horizontalBall = Ball(
            x = mutableFloatStateOf(5.9f), // Close to right edge of square
            y = mutableFloatStateOf(5.5f),
            velocityX = 0.2f,
            velocityY = 0.1f,
            color = leftColor
        )
        
        // Make the square at (5, 5) the same color as ball
        val targetSquare = gameState.squares.find { it.row == 5 && it.col == 5 }!!
        targetSquare.color = leftColor
        
        gameState.balls.clear()
        gameState.balls.add(horizontalBall)
        gameState.updateBalls()
        
        // Should bounce horizontally
        assertTrue(horizontalBall.velocityX < 0)
        assertEquals(0.1f, horizontalBall.velocityY)
    }
    
    @Test
    fun `ball bounces vertically when hitting square from top or bottom`() {
        val gameState = GameState(leftColor, rightColor)
        gameState.initializeGrid(10, 10)
        
        // Test vertical bounce
        val verticalBall = Ball(
            x = mutableFloatStateOf(5.5f),
            y = mutableFloatStateOf(5.9f), // Close to bottom edge of square
            velocityX = 0.1f,
            velocityY = 0.2f,
            color = leftColor
        )
        
        // Make the square at (5, 5) the same color as ball
        val targetSquare = gameState.squares.find { it.row == 5 && it.col == 5 }!!
        targetSquare.color = leftColor
        
        gameState.balls.clear()
        gameState.balls.add(verticalBall)
        gameState.updateBalls()
        
        // Should bounce vertically
        assertEquals(0.1f, verticalBall.velocityX)
        assertTrue(verticalBall.velocityY < 0)
    }
}