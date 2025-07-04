package com.gurpreetsk.pongwars.models

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

class GameState(
    val leftColor: Color = Color.Blue,
    val rightColor: Color = Color.Red
) {
    val squares = mutableStateListOf<Square>()
    val balls = mutableStateListOf<Ball>()
    val leftScore = mutableStateOf(0)
    val rightScore = mutableStateOf(0)
    var gridRows = 0
    var gridCols = 0
    
    fun initializeGrid(rows: Int, cols: Int) {
        if (rows == gridRows && cols == gridCols && squares.isNotEmpty()) {
            return // Already initialized with same dimensions
        }
        
        gridRows = rows
        gridCols = cols
        squares.clear()
        balls.clear()
        
        for (row in 0 until gridRows) {
            for (col in 0 until gridCols) {
                val isLeftSide = col < gridCols / 2
                squares.add(
                    Square(
                        row = row,
                        col = col,
                        color = if (isLeftSide) leftColor else rightColor,
                        isClaimed = false
                    )
                )
            }
        }
        
        initializeBalls()
        updateScores()
    }
    
    private fun initializeBalls() {
        val initialBalls = buildList {
            // Left side ball (red color, to claim blue squares)
            add(
                Ball(
                    x = gridCols * 0.25f,
                    y = gridRows * 0.5f,
                    velocityX = 0.2f,
                    velocityY = 0.15f,
                    color = rightColor  // Red ball on left side
                )
            )
            // Right side ball (blue color, to claim red squares)
            add(
                Ball(
                    x = gridCols * 0.75f,
                    y = gridRows * 0.5f,
                    velocityX = -0.2f,
                    velocityY = -0.15f,
                    color = leftColor   // Blue ball on right side
                )
            )
        }
        balls.addAll(initialBalls)
    }
    
    fun updateScores() {
        leftScore.value = squares.count { it.color == leftColor }
        rightScore.value = squares.count { it.color == rightColor }
    }
    
    fun getSquareAt(row: Int, col: Int): Square? {
        return squares.find { it.row == row && it.col == col }
    }
    
    fun updateBalls() {
        balls.forEach { ball ->
            // Update ball position
            ball.x += ball.velocityX
            ball.y += ball.velocityY
            
            // Check wall collisions
            if (ball.x <= 0 || ball.x >= gridCols) {
                ball.velocityX = -ball.velocityX
                ball.x = ball.x.coerceIn(0f, gridCols.toFloat())
            }
            if (ball.y <= 0 || ball.y >= gridRows) {
                ball.velocityY = -ball.velocityY
                ball.y = ball.y.coerceIn(0f, gridRows.toFloat())
            }
            
            // Check square collisions
            checkSquareCollisions(ball)
        }
    }
    
    private fun checkSquareCollisions(ball: Ball) {
        val ballRow = ball.y.toInt().coerceIn(0, gridRows - 1)
        val ballCol = ball.x.toInt().coerceIn(0, gridCols - 1)
        
        // Check the square at ball position
        val square = getSquareAt(ballRow, ballCol)
        
        // Determine which side the ball is on
        val isLeftSide = ballCol < gridCols / 2
        val ballIsLeftColor = ball.color == leftColor
        
        // Ball should only claim squares that are:
        // 1. Of opposite color to the ball
        // 2. NOT on the ball's starting side
        // Red ball starts on left, should only claim blue squares on RIGHT
        // Blue ball starts on right, should only claim red squares on LEFT
        if (square != null && square.color != ball.color) {
            // A ball can only claim squares if the square is the opposite color
            // AND the ball has crossed to the other side
            val ballStartsLeft = ball.color == rightColor  // Red ball starts left
            val ballStartsRight = ball.color == leftColor  // Blue ball starts right
            
            if ((ballStartsLeft && !isLeftSide) || (ballStartsRight && isLeftSide)) {
                // Change square color to ball's color
                square.color = ball.color
                square.isClaimed = true
                
                // Update scores
                updateScores()
            }
            
            // Always bounce when hitting opposite color square
            // Calculate bounce direction based on where the ball hit the square
            val squareCenterX = ballCol + 0.5f
            val squareCenterY = ballRow + 0.5f
            
            val dx = ball.x - squareCenterX
            val dy = ball.y - squareCenterY
            
            // Determine which edge was hit
            if (kotlin.math.abs(dx) > kotlin.math.abs(dy)) {
                // Hit left or right edge
                ball.velocityX = -ball.velocityX
            } else {
                // Hit top or bottom edge
                ball.velocityY = -ball.velocityY
            }
        }
    }
}