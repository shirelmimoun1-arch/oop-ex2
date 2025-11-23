# Brick Breaker Game (OOP Ex2)

**Course:** Object-Oriented Programming — Exercise 2
**Repository:** [https://github.com/shirelmimoun1-arch/oop-ex2](https://github.com/shirelmimoun1-arch/oop-ex2)
**Authors:** Shirel Mimoun and Talia Cohen

---

## 🎮 Project Overview

This project is an implementation of a classic **Brick Breaker** game, developed as part of Exercise 2 in the Object-Oriented Programming course. The goal of this assignment is to practice and demonstrate fundamental OOP principles through the design and construction of an interactive 2D game using Java and IntelliJ IDEA.

The game simulates the well-known arcade concept where the player controls a paddle to bounce a ball and break a wall of bricks. Each collision between the ball and a brick removes the brick from the board, and the objective is to clear all bricks without letting the ball fall below the paddle.

This exercise focuses on clean object-oriented design, logical separation of responsibilities, and creating reusable and extensible components within a simple game engine structure.

---

## 🧱 Game Features

* Classic Brick Breaker mechanics
* Controllable paddle (player) that moves horizontally
* Ball with dynamic movement and collision response
* Bricks arranged in a grid pattern
* Brick removal upon collision with the ball
* Game over state when the ball is missed
* Win condition when all bricks are destroyed
* Real-time rendering and game loop management

---

## 🖥️ Technologies & Architecture

* **Language:** Java
* **IDE:** IntelliJ IDEA
* **Build system:** Plain Java project, built and run directly via IntelliJ IDEA (no Maven/Gradle)

### Object-Oriented Design

The project emphasizes:

* **Encapsulation:** Game entities maintain internal state through private fields and controlled access.
* **Inheritance:** Shared behavior between game elements through base classes or abstract classes.
* **Polymorphism:** Different game objects implement or override shared methods for collision and rendering.
* **Separation of concerns:** Clear division between game logic, rendering, and user input handling.

Typical components include:

* `Ball` – handles movement and collision detection
* `Paddle` – controlled by player input
* `Brick` – breaks when hit by the ball
* `GameManager` – controls the game loop and state
* `Main` – entry point of the application

---

## 🎯 Game Objective

The player's goal is to:

1. Use the paddle to keep the ball in play.
2. Break all bricks by hitting them with the ball.
3. Avoid letting the ball fall past the paddle.

Winning occurs when all bricks are destroyed. Losing occurs when the ball passes below the paddle.

---

## 🚀 How to Run

1. Clone the repository:

```bash
git clone https://github.com/shirelmimoun1-arch/oop-ex2.git
```

2. Open the project in IntelliJ IDEA.
3. Ensure the Project SDK is properly set (JDK 11 or higher recommended).
4. Locate the class containing `public static void main(String[] args)`.
5. Right-click the file and choose **Run**.
6. Use the keyboard to control the paddle and play the game.

---

## 📂 Project Structure (Typical)

```
/oop-ex2
├── src/
│   ├── game/
│   │   ├── Ball.java
│   │   ├── Paddle.java
│   │   ├── Brick.java
│   │   └── GameManager.java
│   └── Main.java
```

---

## ✅ Acknowledgements

This game was developed as part of the Object-Oriented Programming course at the Hebrew University of Jerusalem (HUJI).

---

## 📎 License

For educational use only.
