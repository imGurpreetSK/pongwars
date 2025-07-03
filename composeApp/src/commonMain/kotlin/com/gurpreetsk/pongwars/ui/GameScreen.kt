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
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val squareSize = minOf(
            maxWidth.value / gameState.gridCols,
            maxHeight.value / gameState.gridRows
        ).dp
        
        Canvas(
            modifier = Modifier
                .size(
                    width = squareSize * gameState.gridCols,
                    height = squareSize * gameState.gridRows
                )
                .align(Alignment.Center)
                .background(Color.Black)
        ) {
            drawGrid(gameState, squareSize.value)
        }
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
    
    // Draw 1px black border
    drawRect(
        color = Color.Black,
        topLeft = Offset(x, y),
        size = Size(squareSizePx, squareSizePx),
        style = Stroke(width = 1f)
    )
}