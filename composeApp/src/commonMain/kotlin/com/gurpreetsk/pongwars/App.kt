package com.gurpreetsk.pongwars

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.gurpreetsk.pongwars.ui.GameScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        GameScreen()
    }
}
