package com.gurpreetsk.pongwars.models

import kotlin.test.*

internal class GridInitializationTest {
    
    @Test
    fun `initialize grid creates correct number of squares and balls`() {
        val gameState = GameState()
        val rows = 10
        val cols = 20
        
        gameState.initializeGrid(rows, cols)
        
        assertEquals(rows * cols, gameState.squares.size)
        assertEquals(2, gameState.balls.size)
    }
    
    @Test
    fun `initialize grid assigns correct colors to left and right sides`() {
        val gameState = GameState()
        val rows = 10
        val cols = 20
        
        gameState.initializeGrid(rows, cols)
        
        val leftSideSquares = gameState.squares.filter { it.col < cols / 2 }
        assertTrue(leftSideSquares.all { it.side == Side.LEFT })
        assertEquals(rows * (cols / 2), leftSideSquares.size)
        
        val rightSideSquares = gameState.squares.filter { it.col >= cols / 2 }
        assertTrue(rightSideSquares.all { it.side == Side.RIGHT })
        assertEquals(rows * (cols / 2), rightSideSquares.size)
    }
    
    @Test
    fun `initialize grid sets all squares as unclaimed initially`() {
        val gameState = GameState()
        gameState.initializeGrid(10, 20)
        
        assertTrue(gameState.squares.all { !it.isClaimed })
    }
    
    @Test
    fun `initialize grid calculates initial scores correctly`() {
        val gameState = GameState()
        val rows = 10
        val cols = 20
        
        gameState.initializeGrid(rows, cols)
        
        assertEquals(rows * (cols / 2), gameState.leftScore.value)
        assertEquals(rows * (cols / 2), gameState.rightScore.value)
    }
    
    @Test
    fun `initialize grid with same dimensions does not recreate squares`() {
        val gameState = GameState()
        
        gameState.initializeGrid(10, 20)
        val initialSquareCount = gameState.squares.size
        val initialBallCount = gameState.balls.size
        
        gameState.initializeGrid(10, 20)
        
        assertEquals(initialSquareCount, gameState.squares.size)
        assertEquals(initialBallCount, gameState.balls.size)
    }
    
    @Test
    fun `initialize grid with different dimensions recreates grid`() {
        val gameState = GameState()
        
        gameState.initializeGrid(10, 20)
        assertEquals(200, gameState.squares.size)
        
        gameState.initializeGrid(5, 10)
        assertEquals(50, gameState.squares.size)
        assertEquals(2, gameState.balls.size)
    }
}