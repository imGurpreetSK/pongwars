package com.gurpreetsk.pongwars.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gurpreetsk.pongwars.models.Ball
import com.gurpreetsk.pongwars.models.GameState
import com.gurpreetsk.pongwars.models.Side
import com.gurpreetsk.pongwars.models.Square
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun GameScreen(modifier: Modifier = Modifier) {
    val gameState = remember { GameState() }
    
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Grid fills entire screen
        GameGrid(
            gameState = gameState,
            modifier = Modifier.fillMaxSize()
        )
        
        // Score display overlays on top with transparent background
        ScoreDisplay(
            leftScore = gameState.leftScore.value,
            rightScore = gameState.rightScore.value,
            leftColor = gameState.leftColor,
            rightColor = gameState.rightColor,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .background(Color.Black.copy(alpha = 0.5f))
        )
    }
}

@Composable
private fun ScoreDisplay(
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
            text = "Left: $leftScore",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = leftColor
            )
        )
        Text(
            text = "Right: $rightScore",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = rightColor
            )
        )
    }
}

@Composable
private fun GameGrid(
    gameState: GameState,
    modifier: Modifier = Modifier
) {
    val targetSquareSize = 30.dp
    
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val squareSizePx = with(LocalDensity.current) { targetSquareSize.toPx() }
        val cols = (constraints.maxWidth / squareSizePx).toInt().coerceAtLeast(2)
        val rows = (constraints.maxHeight / squareSizePx).toInt().coerceAtLeast(2)
        
        // Ensure even number of columns for equal split
        val adjustedCols = if (cols % 2 == 0) cols else cols - 1
        
        // Initialize grid only when dimensions change
        LaunchedEffect(rows, adjustedCols) {
            gameState.initializeGrid(rows, adjustedCols)
        }
        
        LaunchedEffect(Unit) {
            while (true) {
                delay(16) // ~60 FPS
                gameState.updateBalls()
            }
        }

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            // Calculate actual square size to fill the entire screen
            val actualSquareSize = size.width / adjustedCols

            drawGrid(gameState.squares, actualSquareSize, gameState)
            drawBalls(gameState.balls, actualSquareSize, gameState)
        }
    }
}

private fun DrawScope.drawGrid(squares: List<Square>, squareSizePx: Float, gameState: GameState) {
    squares.forEach { square ->
        drawSquare(square, squareSizePx, gameState)
    }
}

private fun DrawScope.drawSquare(square: Square, squareSizePx: Float, gameState: GameState) {
    val x = square.col * squareSizePx
    val y = square.row * squareSizePx

    // Draw filled square with a tiny inset to create border effect
    val inset = 0.1f
    drawRect(
        color = gameState.getColor(square.side),
        topLeft = Offset(x + inset, y + inset),
        size = Size(squareSizePx - inset * 2, squareSizePx - inset * 2)
    )
}

private fun DrawScope.drawBalls(balls: List<Ball>, squareSizePx: Float, gameState: GameState) {
    balls.forEach { ball ->
        drawBall(ball, squareSizePx, gameState)
    }
}

private fun DrawScope.drawBall(ball: Ball, squareSizePx: Float, gameState: GameState) {
    val centerX = ball.x.floatValue * squareSizePx
    val centerY = ball.y.floatValue * squareSizePx
    val radius = ball.radius.toPx()

    drawCircle(
        color = gameState.getColor(ball.side),
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
        leftColor = Color(3, 252, 219),
        rightColor = Color(2, 122, 107)
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
