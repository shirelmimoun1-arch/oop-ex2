import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Brick;
import bricker.gameobjects.UserPaddle;
import bricker.main.BrickerGameManager;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.brick_strategies.CollisionStrategy;

import javax.swing.*;
import java.util.Random;

public class BouncingBallGameManager extends GameManager {
    private static final int BORDER_WIDTH = 10;
    private static final int PADDLE_HEIGHT = 20;
    private static final int PADDLE_WIDTH = 100;
    private static final int BALL_RADIUS = 35;
    private static final int BALL_SPEED = 250;
    private static final float WALL_FACTOR = 0.001f;


    public BouncingBallGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        Vector2 windowDimensions = windowController.getWindowDimensions();

        // Creating wallpaper
        createWallPaper(imageReader, windowDimensions);

        // Creating ball
        createBall(windowDimensions, imageReader, soundReader);

        Renderable paddleImage = imageReader.readImage("assets/paddle.png", true);

        // Creating user paddles
        createUserPaddle(paddleImage, inputListener, windowDimensions);

        // Creating ai paddles
        createAiPaddle(paddleImage, windowDimensions);

        // Creating walls
        createWall(windowDimensions);

//         Creating one brick
        BrickerGameManager brickerGameManager = new BrickerGameManager(7, 8, windowController, imageReader);
        Renderable brickImage = imageReader.readImage("assets/brick.png", false);
        CollisionStrategy collisionStrategy = new BasicCollisionStrategy(brickerGameManager);
        Brick brick = new Brick(new Vector2(20,60),
                new Vector2(windowDimensions.x() - 50,15), brickImage,collisionStrategy);
        gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
        System.out.println("hello");
    }

    private void createWall(Vector2 windowDimensions) {
        Vector2 topLeftOfUpperAndLeftWall = Vector2.ZERO;
        Vector2 topLeftOfDownWall = new Vector2(0, windowDimensions.y());
        Vector2 topLeftOfRightWall = new Vector2(windowDimensions.x(), 0);

        Vector2 UpAndDownWallDimensions = new Vector2(windowDimensions.x(),windowDimensions.mult(WALL_FACTOR).y());
        Vector2 RightAndLefWallDimensions = new Vector2(windowDimensions.mult(WALL_FACTOR).x(),windowDimensions.y());

        GameObject wallUp = new GameObject(topLeftOfUpperAndLeftWall, UpAndDownWallDimensions, null);
        GameObject wallDown = new GameObject(topLeftOfDownWall, UpAndDownWallDimensions,null);
        GameObject wallRight = new GameObject(topLeftOfRightWall, RightAndLefWallDimensions,null);
        GameObject wallLeft = new GameObject(topLeftOfUpperAndLeftWall, RightAndLefWallDimensions,null);

        Vector2[] wallHeights = {new Vector2(windowDimensions.x()/2, 0),
                new Vector2(windowDimensions.x()/2, windowDimensions.y()),
                new Vector2(windowDimensions.x(), windowDimensions.y()/2),
                new Vector2(0,windowDimensions.y()/2)
        };
        GameObject[] walls = {wallUp, wallDown, wallRight, wallLeft};
        for (int i = 0; i < wallHeights.length; i++) {
            walls[i].setCenter(wallHeights[i]);
            gameObjects().addGameObject(walls[i]); // add
        }
    }

    private void createBall(Vector2 windowDimensions, ImageReader imageReader, SoundReader soundReader){
        Renderable ballImage = imageReader.readImage("assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop.wav");
        GameObject ball = new Ball(Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage, collisionSound);
        ball.setCenter(windowDimensions.mult(0.5f));
        ball.setVelocity(Vector2.DOWN.mult(BALL_SPEED)); // Vector2(0, 100)
        gameObjects().addGameObject(ball); // gets a private field of the father class that holds all the game objects
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if(rand.nextBoolean()){
            ballVelX *= -1;
        }
        if(rand.nextBoolean()){
            ballVelY *= -1;
        }
        ball.setVelocity(new Vector2(ballVelX,ballVelY));
    }

    private void createUserPaddle(Renderable paddleImage, UserInputListener inputListener, Vector2 windowDimensions){
        GameObject userPaddle = new UserPaddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT), paddleImage, inputListener);
        userPaddle.setCenter(new Vector2(windowDimensions.x()/2, (int)windowDimensions.y()-30));
        gameObjects().addGameObject(userPaddle);
    }

    private void createAiPaddle(Renderable paddleImage, Vector2 windowDimensions){
        GameObject aiPaddle = new GameObject(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT), paddleImage);
        aiPaddle.setCenter(new Vector2(windowDimensions.x()/2, 30));
        gameObjects().addGameObject(aiPaddle);
    }

    private void createWallPaper(ImageReader imageReader,Vector2 windowDimensions){
        Renderable wallPaperImage = imageReader.readImage("assets/DARK_BG2_small.jpeg", false);
        GameObject wallPaper = new GameObject(Vector2.ZERO, windowDimensions, wallPaperImage);
        wallPaper.setCenter(windowDimensions.mult(0.5f));
        gameObjects().addGameObject(wallPaper, Layer.BACKGROUND);
    }

    public static void main(String[] args) {
        BouncingBallGameManager window = new BouncingBallGameManager("Bouncing Ball",
                new Vector2(700, 500));
        window.run();
    }
}

