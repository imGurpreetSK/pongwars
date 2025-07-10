package com.gurpreetsk.pongwars.models

import androidx.compose.runtime.mutableFloatStateOf
import kotlin.test.*

internal class BallWallCollisionsTest {
    
    @Test
    fun `update balls bounces off right wall`() {
        val gameState = GameState()
        gameState.initializeGrid(10, 10)
        
        // Create a ball near the right wall
        val testBall = Ball(
            x = mutableFloatStateOf(9.9f),
            y = mutableFloatStateOf(5.0f),
            velocityX = 0.2f,
            velocityY = 0.0f,
            side = Side.LEFT
        )
        gameState.balls.clear()
        gameState.balls.add(testBall)
        
        gameState.updateBalls()
        
        // Ball should bounce off the wall
        assertTrue(testBall.velocityX < 0)
        assertEquals(10.0f, testBall.x.floatValue)
    }
    
    @Test
    fun `update balls bounces off left wall`() {
        val gameState = GameState()
        gameState.initializeGrid(10, 10)
        
        // Create a ball near the left wall
        val testBall = Ball(
            x = mutableFloatStateOf(0.1f),
            y = mutableFloatStateOf(5.0f),
            velocityX = -0.2f,
            velocityY = 0.0f,
            side = Side.LEFT
        )
        gameState.balls.clear()
        gameState.balls.add(testBall)
        
        gameState.updateBalls()
        
        // Ball should bounce off the wall
        assertTrue(testBall.velocityX > 0)
        assertEquals(0.0f, testBall.x.floatValue)
    }
    
    @Test
    fun `update balls bounces off bottom wall`() {
        val gameState = GameState()
        gameState.initializeGrid(10, 10)
        
        // Create a ball near the bottom wall
        val testBall = Ball(
            x = mutableFloatStateOf(5.0f),
            y = mutableFloatStateOf(9.9f),
            velocityX = 0.0f,
            velocityY = 0.2f,
            side = Side.LEFT
        )
        gameState.balls.clear()
        gameState.balls.add(testBall)
        
        gameState.updateBalls()
        
        // Ball should bounce off the wall
        assertTrue(testBall.velocityY < 0)
        assertEquals(10.0f, testBall.y.floatValue)
    }
    
    @Test
    fun `update balls bounces off top wall`() {
        val gameState = GameState()
        gameState.initializeGrid(10, 10)
        
        // Create a ball near the top wall
        val testBall = Ball(
            x = mutableFloatStateOf(5.0f),
            y = mutableFloatStateOf(0.1f),
            velocityX = 0.0f,
            velocityY = -0.2f,
            side = Side.LEFT
        )
        gameState.balls.clear()
        gameState.balls.add(testBall)
        
        gameState.updateBalls()
        
        // Ball should bounce off the wall
        assertTrue(testBall.velocityY > 0)
        assertEquals(0.0f, testBall.y.floatValue)
    }
    
    @Test
    fun `update balls bounces off corner walls simultaneously`() {
        val gameState = GameState()
        gameState.initializeGrid(10, 10)
        
        // Create a ball in the corner
        val testBall = Ball(
            x = mutableFloatStateOf(9.9f),
            y = mutableFloatStateOf(9.9f),
            velocityX = 0.2f,
            velocityY = 0.2f,
            side = Side.LEFT
        )
        gameState.balls.clear()
        gameState.balls.add(testBall)
        
        gameState.updateBalls()
        
        // Ball should bounce off both walls
        assertTrue(testBall.velocityX < 0)
        assertTrue(testBall.velocityY < 0)
        assertEquals(10.0f, testBall.x.floatValue)
        assertEquals(10.0f, testBall.y.floatValue)
    }
}