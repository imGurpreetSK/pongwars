package com.gurpreetsk.pongwars.models

import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

internal class GameState(
    val leftColor: Color = Color(3, 252, 219),
    val rightColor: Color = Color(2, 122, 107)
) {
    val squares = mutableStateListOf<Square>()
    val balls = mutableStateListOf<Ball>()
    val leftScore = mutableStateOf(0)
    val rightScore = mutableStateOf(0)
    private var gridRows = 0
    private var gridCols = 0

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
                        side = if (isLeftSide) Side.LEFT else Side.RIGHT,
                        isClaimed = false
                    )
                )
            }
        }

        initializeBalls()
        updateScores()
    }

    fun updateBalls() {
        balls.forEach { ball ->
            // Update ball position
            ball.x.floatValue += ball.velocityX
            ball.y.floatValue += ball.velocityY

            // Check wall collisions
            if (ball.x.floatValue <= 0 || ball.x.floatValue >= gridCols) {
                ball.velocityX = -ball.velocityX
                ball.x.floatValue = ball.x.floatValue.coerceIn(0f, gridCols.toFloat())
            }

            if (ball.y.floatValue <= 0 || ball.y.floatValue >= gridRows) {
                ball.velocityY = -ball.velocityY
                ball.y.floatValue = ball.y.floatValue.coerceIn(0f, gridRows.toFloat())
            }

            // Check square collisions
            checkSquareCollisions(ball)
        }
    }

    fun getColor(side: Side): Color = when (side) {
        Side.LEFT -> leftColor
        Side.RIGHT -> rightColor
    }

    private fun initializeBalls() {
        val initialBalls = buildList {
            // Ball on left side has right side (inverted)
            add(
                Ball(
                    x = mutableFloatStateOf(gridCols * 0.25f),
                    y = mutableFloatStateOf(gridRows * 0.5f),
                    velocityX = 0.2f,   // Moving right (reduced by 50%)
                    velocityY = 0.15f,
                    side = Side.RIGHT  // Inverted: right side on left side
                )
            )
            // Ball on right side has left side (inverted)
            add(
                Ball(
                    x = mutableFloatStateOf(gridCols * 0.75f),
                    y = mutableFloatStateOf(gridRows * 0.5f),
                    velocityX = -0.2f,  // Moving left (reduced by 50%)
                    velocityY = -0.15f,
                    side = Side.LEFT   // Inverted: left side on right side
                )
            )
        }
        balls.addAll(initialBalls)
    }

    private fun updateScores() {
        leftScore.value = squares.count { it.side == Side.LEFT }
        rightScore.value = squares.count { it.side == Side.RIGHT }
    }

    private fun getSquareAt(row: Int, col: Int): Square? {
        return squares.find { it.row == row && it.col == col }
    }

    private fun checkSquareCollisions(ball: Ball) {
        val ballRow = ball.y.floatValue.toInt().coerceIn(0, gridRows - 1)
        val ballCol = ball.x.floatValue.toInt().coerceIn(0, gridCols - 1)

        // Check the square at ball position
        val square = getSquareAt(ballRow, ballCol)

        // Ball flips squares of the SAME side to opposite side
        if (square != null && square.side == ball.side) {
            // Flip square to the opposite side
            square.side = if (ball.side == Side.LEFT) Side.RIGHT else Side.LEFT
            square.isClaimed = true

            // Update scores
            updateScores()

            // Always bounce when hitting same color square
            // Calculate bounce direction based on where the ball hit the square
            val squareCenterX = ballCol + 0.5f
            val squareCenterY = ballRow + 0.5f

            val dx = ball.x.floatValue - squareCenterX
            val dy = ball.y.floatValue - squareCenterY

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
