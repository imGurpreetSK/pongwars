package com.gurpreetsk.pongwars.models

import androidx.compose.ui.graphics.Color
import kotlin.test.*

internal class GridEdgeCasesTest {
    
    private val leftColor = Color(3, 252, 219)
    private val rightColor = Color(2, 122, 107)
    
    @Test
    fun `initialize grid with minimum size 1x2`() {
        val gameState = GameState(leftColor, rightColor)
        
        gameState.initializeGrid(1, 2)
        assertEquals(2, gameState.squares.size)
        assertEquals(1, gameState.squares.filter { it.color == leftColor }.size)
        assertEquals(1, gameState.squares.filter { it.color == rightColor }.size)
    }
    
    @Test
    fun `initialize grid with odd number of columns`() {
        val gameState = GameState(leftColor, rightColor)
        
        gameState.initializeGrid(3, 5)
        assertEquals(15, gameState.squares.size)
        assertEquals(6, gameState.squares.filter { it.color == leftColor }.size)  // 3 * 2
        assertEquals(9, gameState.squares.filter { it.color == rightColor }.size) // 3 * 3
    }
    
    @Test
    fun `grid with 1x1 dimension creates single square with left color`() {
        val gameState = GameState(leftColor, rightColor)
        gameState.initializeGrid(1, 1)
        
        assertEquals(1, gameState.squares.size)
        assertEquals(2, gameState.balls.size)
        
        // With one column, all squares should be left color (col < 1/2 = 0)
        assertEquals(leftColor, gameState.squares[0].color)
    }
    
    @Test
    fun `grid with 0x0 dimensions creates no squares`() {
        val gameState = GameState(leftColor, rightColor)
        gameState.initializeGrid(0, 0)
        
        assertEquals(0, gameState.squares.size)
        assertEquals(2, gameState.balls.size) // Balls are still created
        assertEquals(0, gameState.leftScore.value)
        assertEquals(0, gameState.rightScore.value)
    }
    
    @Test
    fun `grid with 0 rows but positive columns creates no squares`() {
        val gameState = GameState(leftColor, rightColor)
        gameState.initializeGrid(0, 10)
        
        assertEquals(0, gameState.squares.size)
        assertEquals(2, gameState.balls.size)
    }
    
    @Test
    fun `grid with positive rows but 0 columns creates no squares`() {
        val gameState = GameState(leftColor, rightColor)
        gameState.initializeGrid(10, 0)
        
        assertEquals(0, gameState.squares.size)
        assertEquals(2, gameState.balls.size)
    }
}