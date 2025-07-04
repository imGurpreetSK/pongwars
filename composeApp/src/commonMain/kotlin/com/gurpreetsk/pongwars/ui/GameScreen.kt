package com.gurpreetsk.pongwars.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gurpreetsk.pongwars.models.GameState
import com.gurpreetsk.pongwars.models.Square
import com.gurpreetsk.pongwars.models.Ball
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun GameScreen(modifier: Modifier = Modifier) {
    val gameState = remember { GameState() }
    
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScoreDisplay(
            leftScore = gameState.leftScore.value,
            rightScore = gameState.rightScore.value,
            leftColor = gameState.leftColor,
            rightColor = gameState.rightColor
        )
        
        GameGrid(
            gameState = gameState,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ScoreDisplay(
    leftScore: Int,
    rightScore: Int,
    leftColor: Color,
    rightColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Blue: $leftScore",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = leftColor
            )
        )
        Text(
            text = "Red: $rightScore",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = rightColor
            )
        )
    }
}

@Composable
fun GameGrid(
    gameState: GameState,
    modifier: Modifier = Modifier
) {
    val targetSquareSize = 20.dp
    
    // Animation loop
    LaunchedEffect(gameState) {
        while (true) {
            withFrameMillis { frameTime ->
                gameState.updateBalls()
            }
        }
    }
    
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val squareSizePx = targetSquareSize.toPx()
        val cols = (size.width / squareSizePx).toInt().coerceAtLeast(2)
        val rows = (size.height / squareSizePx).toInt().coerceAtLeast(2)
        
        // Ensure even number of columns for equal split
        val adjustedCols = if (cols % 2 == 0) cols else cols - 1
        
        // Initialize grid if needed
        gameState.initializeGrid(rows, adjustedCols)
        
        // Calculate actual square size to fit exactly
        val actualSquareSize = minOf(
            size.width / adjustedCols,
            size.height / rows
        )
        
        drawGrid(gameState, actualSquareSize)
        drawBalls(gameState, actualSquareSize)
    }
}

fun DrawScope.drawGrid(gameState: GameState, squareSizePx: Float) {
    gameState.squares.forEach { square ->
        drawSquare(square, squareSizePx)
    }
}

fun DrawScope.drawSquare(square: Square, squareSizePx: Float) {
    val x = square.col * squareSizePx
    val y = square.row * squareSizePx
    
    // Draw filled square
    drawRect(
        color = square.color,
        topLeft = Offset(x, y),
        size = Size(squareSizePx, squareSizePx)
    )
    
    // Draw 0.1px black border
    drawRect(
        color = Color.Black,
        topLeft = Offset(x, y),
        size = Size(squareSizePx, squareSizePx),
        style = Stroke(width = 0.1f)
    )
}

fun DrawScope.drawBalls(gameState: GameState, squareSizePx: Float) {
    gameState.balls.forEach { ball ->
        drawBall(ball, squareSizePx)
    }
}

fun DrawScope.drawBall(ball: Ball, squareSizePx: Float) {
    val centerX = ball.x * squareSizePx
    val centerY = ball.y * squareSizePx
    val radius = ball.radius.toPx()
    
    drawCircle(
        color = ball.color,
        radius = radius,
        center = Offset(centerX, centerY)
    )
}

@Preview
@Composable
private fun ScoreDisplayPreview() {
    ScoreDisplay(
        leftScore = 400,
        rightScore = 400,
        leftColor = Color.Blue,
        rightColor = Color.Red
    )
}

@Preview
@Composable
private fun GameGridPreview() {
    GameGrid(
        gameState = GameState(),
        modifier = Modifier.size(400.dp, 200.dp)
    )
}

@Preview
@Composable
private fun GameScreenPreview() {
    GameScreen()
}