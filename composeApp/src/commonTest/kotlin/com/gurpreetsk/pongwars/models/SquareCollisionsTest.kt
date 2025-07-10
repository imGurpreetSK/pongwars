package com.gurpreetsk.pongwars.models

import androidx.compose.runtime.mutableFloatStateOf
import kotlin.test.*

internal class SquareCollisionsTest {
    
    @Test
    fun `ball flips square when hitting same color`() {
        val gameState = GameState()
        gameState.initializeGrid(10, 10)
        
        // Create a ball with left side at position (5, 5)
        val testBall = Ball(
            x = mutableFloatStateOf(5.5f),
            y = mutableFloatStateOf(5.5f),
            velocityX = 0.1f,
            velocityY = 0.1f,
            side = Side.LEFT
        )
        gameState.balls.clear()
        gameState.balls.add(testBall)
        
        // Make the square at (5, 5) the same color as ball
        val targetSquare = gameState.squares.find { it.row == 5 && it.col == 5 }!!
        targetSquare.side = Side.LEFT
        
        gameState.updateBalls()
        
        // Square should be flipped to opposite side
        assertEquals(Side.RIGHT, targetSquare.side)
        assertTrue(targetSquare.isClaimed)
    }
    
    @Test
    fun `ball does not flip square when hitting different color`() {
        val gameState = GameState()
        gameState.initializeGrid(10, 10)
        
        // Create a ball with left side at position (5, 5)
        val testBall = Ball(
            x = mutableFloatStateOf(5.5f),
            y = mutableFloatStateOf(5.5f),
            velocityX = 0.1f,
            velocityY = 0.1f,
            side = Side.LEFT
        )
        gameState.balls.clear()
        gameState.balls.add(testBall)
        
        // The square at (5, 5) is right side initially
        val targetSquare = gameState.squares.find { it.row == 5 && it.col == 5 }!!
        assertEquals(Side.RIGHT, targetSquare.side)
        
        val originalVelocityX = testBall.velocityX
        val originalVelocityY = testBall.velocityY
        
        gameState.updateBalls()
        
        // Square should NOT be flipped (different colors)
        assertEquals(Side.RIGHT, targetSquare.side)
        assertFalse(targetSquare.isClaimed)
        
        // Ball should not bounce
        assertEquals(originalVelocityX, testBall.velocityX)
        assertEquals(originalVelocityY, testBall.velocityY)
    }
    
    @Test
    fun `ball bounces horizontally when hitting square from side`() {
        val gameState = GameState()
        gameState.initializeGrid(10, 10)
        
        // Test horizontal bounce
        val horizontalBall = Ball(
            x = mutableFloatStateOf(5.9f), // Close to right edge of square
            y = mutableFloatStateOf(5.5f),
            velocityX = 0.2f,
            velocityY = 0.1f,
            side = Side.LEFT
        )
        
        // Make the square at (5, 5) the same color as ball
        val targetSquare = gameState.squares.find { it.row == 5 && it.col == 5 }!!
        targetSquare.side = Side.LEFT
        
        gameState.balls.clear()
        gameState.balls.add(horizontalBall)
        gameState.updateBalls()
        
        // Should bounce horizontally
        assertTrue(horizontalBall.velocityX > 0)
        assertEquals(0.1f, horizontalBall.velocityY)
    }
    
    @Test
    fun `ball bounces vertically when hitting square from top or bottom`() {
        val gameState = GameState()
        gameState.initializeGrid(10, 10)
        
        // Test vertical bounce
        val verticalBall = Ball(
            x = mutableFloatStateOf(5.5f),
            y = mutableFloatStateOf(5.9f), // Close to bottom edge of square
            velocityX = 0.1f,
            velocityY = 0.2f,
            side = Side.LEFT
        )
        
        // Make the square at (5, 5) the same color as ball
        val targetSquare = gameState.squares.find { it.row == 5 && it.col == 5 }!!
        targetSquare.side = Side.LEFT
        
        gameState.balls.clear()
        gameState.balls.add(verticalBall)
        gameState.updateBalls()
        
        // Should bounce vertically
        assertEquals(0.1f, verticalBall.velocityX)
        assertTrue(verticalBall.velocityY > 0)
    }
}
