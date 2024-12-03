package bricker.main;
import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.brick_strategies.CollisionStrategy;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Brick;
import bricker.gameobjects.UserPaddle;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.gameobjects.Brick;

import java.util.Random;

public class BrickerGameManager extends GameManager {
    private static final int DEFAULT_ROWS = 7;
    private static final int NUM_OF_BRICK = 8;
    private static final int BORDER_WIDTH = 10;
    private static final int PADDLE_HEIGHT = 20;
    private static final int PADDLE_WIDTH = 100;
    private static final int BALL_RADIUS = 35;
    private static final int BALL_SPEED = 250;
    private static final float WALL_FACTOR = 0.001f;
    private static final double SPACE_FROM_WALL = 1.5;
//    private  float PREFIX = windowDimensions.mult(SPACE_FROM_WALL).y();

    private  int numOfBricksInRow;
    private int numOfRow;
    private WindowController windowController;
    private ImageReader imageReader;
    private float brickLength;

    public BrickerGameManager(int numOfBricksInRow, int numOfRow, WindowController windowController,
                              ImageReader imageReader){
        this.numOfBricksInRow = numOfBricksInRow;
        this.numOfRow = numOfRow;
        this.windowController = windowController;
        this.imageReader = imageReader;
        Vector2 windowDimensions = windowController.getWindowDimensions();
        this.brickLength = windowDimensions.x() / NUM_OF_BRICK;
//        createWallOfBricks(imageReader, windowDimensions);

    }
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }


    public BrickerGameManager(){
        this.numOfRow = DEFAULT_ROWS;
        this.numOfBricksInRow = NUM_OF_BRICK;
//        this.windowController = windowController;
//        Vector2 windowDimensions = windowController.getWindowDimensions();
//        this.brickLength = windowDimensions.x() / NUM_OF_BRICK;
//        createWallOfBricks(imageReader);
    }

//    private void createWallOfBricks(ImageReader imageReader, Vector2 windowDimensions){
//        for(int i = 0; i < numOfRow; i++) {
//            for(int j = 0; j < numOfBricksInRow; j++) {
//                Renderable brickImage = imageReader.readImage("assets/brick.png", false);
////                CollisionStrategy collisionStrategy = new CollisionStrategy();
//                Brick brick = new Brick(Vector2.ZERO, new Vector2(brickLength, 15), brickImage, null);
//                brick.setCenter(new Vector2(, (int) windowDimensions.y() - 30));
//                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
//            }
//        }
//    }





    public void removeBrick(GameObject brick) {
        System.out.println("collision with brick detected");
        GameObjectCollection a = super.gameObjects();
        gameObjects().removeGameObject(brick,Layer.STATIC_OBJECTS);
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

        // Creating walls
        createWall(windowDimensions);

//         Creating one brick
        Renderable brickImage = imageReader.readImage("assets/brick.png", false);
        CollisionStrategy collisionStrategy = new BasicCollisionStrategy(this);
        Brick brick = new Brick(new Vector2(20,60),
                new Vector2(windowDimensions.x() - 50,15), brickImage,collisionStrategy);
        gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
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
        System.out.print("hi");
    }

    private void createUserPaddle(Renderable paddleImage, UserInputListener inputListener, Vector2 windowDimensions){
        GameObject userPaddle = new UserPaddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT), paddleImage, inputListener);
        userPaddle.setCenter(new Vector2(windowDimensions.x()/2, (int)windowDimensions.y()-30));
        gameObjects().addGameObject(userPaddle);
    }

    private void createWallPaper(ImageReader imageReader,Vector2 windowDimensions){
        Renderable wallPaperImage = imageReader.readImage("assets/DARK_BG2_small.jpeg", false);
        GameObject wallPaper = new GameObject(Vector2.ZERO, windowDimensions, wallPaperImage);
        wallPaper.setCenter(windowDimensions.mult(0.5f));
        gameObjects().addGameObject(wallPaper, Layer.BACKGROUND);
    }

    public static void main(String[] args) {
        BrickerGameManager window = new BrickerGameManager("Bouncing Ball",
                new Vector2(700, 500));
        window.run();
    }
}
