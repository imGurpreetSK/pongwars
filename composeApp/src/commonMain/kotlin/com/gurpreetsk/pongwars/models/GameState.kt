package com.gurpreetsk.pongwars.models

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

class GameState(
    val gridRows: Int = 20,
    val gridCols: Int = 40,
    val leftColor: Color = Color.Blue,
    val rightColor: Color = Color.Red
) {
    val squares = mutableStateListOf<Square>()
    val balls = mutableStateListOf<Ball>()
    val leftScore = mutableStateOf(0)
    val rightScore = mutableStateOf(0)
    
    init {
        initializeGrid()
        initializeBalls()
    }
    
    private fun initializeGrid() {
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
        updateScores()
    }
    
    private fun initializeBalls() {
        val initialBalls = buildList {
            add(
                Ball(
                    x = gridCols * 0.25f,
                    y = gridRows * 0.5f,
                    velocityX = 0.2f,
                    velocityY = 0.15f,
                    color = rightColor
                )
            )
            add(
                Ball(
                    x = gridCols * 0.75f,
                    y = gridRows * 0.5f,
                    velocityX = -0.2f,
                    velocityY = -0.15f,
                    color = leftColor
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
}