# Pongwars

A cross-platform color war game built with Kotlin Multiplatform and Compose Multiplatform.
Watch as balls bounce around the screen, claiming territory for their team in this mesmerizing visual experience.

## Overview

Pongwars is a territorial control game where colored balls represent different teams.
As balls move across the game board, they flip squares to their team's color, creating a dynamic and ever-changing battlefield. The game runs on Android, iOS, Desktop, and Web browsers (via WebAssembly).

![Pongwars across all platforms](/demo/all.png)

### Demo

1. Android: [video](/demo/pongwars-android.mp4)
2. Desktop: [video](/demo/pongwars-desktop.mov)
3. iOS: [video](/demo/pongwars-ios.mp4)
4. Web: [video](/demo/pongwars-web.mov)

## Inspiration

This is a KMP adaptation of Koen van Gilst's [Pong Wars](https://github.com/vnglst/pong-wars), which itself is an
adaptation of Nicolas D'Souza's [tweet](https://twitter.com/nicolasdnl/status/1749715070928433161).
However, it appears to be a much older idea. There's some more information on the history in the [Hacknernews discussion](https://news.ycombinator.com/item?id=39159418).

## Game Mechanics

- **Two Teams**: Left side (one color) vs Right side (another color)
- **Territory Control**: Balls flip squares they pass over to their team's color
- **Real-time Scoring**: Score is based on the number of squares each team controls
- **Physics**: Simple velocity-based ball movement with wall bouncing
- **Dynamic Grid**: Adapts to screen size for optimal gameplay

## Technical Architecture

### Core Components

#### Game Engine (`GameState.kt`)
- Manages game grid and ball states
- Handles collision detection and physics updates
- Updates scores based on territory control
- Runs at ~60 FPS using coroutine-based game loop

#### Rendering System (`GameScreen.kt` & `GameGrid.kt`)
- Canvas-based rendering using Compose Multiplatform
- Reactive UI updates triggered by state changes
- Efficient grid and ball drawing algorithms
- Transparent score overlay

#### Data Models
- `Ball.kt`: Position, velocity, color, and radius properties
- `Square.kt`: Grid squares with position, color, and claimed status
- `GameState.kt`: Central game state management

### Platform Support

- **Android**: Native APK with Material3 theming
- **iOS**: Native app via Kotlin/Native
- **Desktop**: JVM-based desktop application
- **Web**: WebAssembly build for browser deployment

## Building the Project

### Prerequisites
- JDK 17 or higher
- Android Studio (for Android builds)
- Xcode (for iOS builds, macOS only)

### Build Commands

```bash
# Android
./gradlew :composeApp:assembleDebug

# Desktop
./gradlew :composeApp:run

# iOS (macOS only)
./gradlew :composeApp:iosSimulatorArm64Test

# Web (WASM)
./gradlew :composeApp:wasmJsBrowserRun
```

## License

This project is open source. Please check the license file for more details.
