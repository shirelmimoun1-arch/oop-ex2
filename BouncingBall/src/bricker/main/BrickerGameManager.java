package bricker.main;
import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.brick_strategies.CollisionStrategy;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import bricker.gameobjects.Brick;

import java.awt.*;
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
    private static final String PADDLE_PICTURE_PATH = "assets/paddle.png";
    private static final String BALL_PICTURE_PATH = "assets/ball.png";
    private static final String BRICK_PICTURE_PATH = "assets/brick.png";
    private static final String CLASH_SOUND_PATH = "assets/blop.wav";
    private static final String WALLPAPER_PICTURE_PATH = "assets/DARK_BG2_small.jpeg";
    private static final String WINDOW_TITLE = "Bouncing Ball";
    private static final Color INITIAL_COLOR = Color.GREEN;
    private  int numOfBricksInRow;
    private int numOfRows;
    private Ball ball;
    private WindowController windowController;
    private Vector2 windowDimensions;
    private static final int DEFAULT_NUM_OF_LIVES = 3;
    private int numOfHeartsRemain = DEFAULT_NUM_OF_LIVES;
    private int bricksHitCounter ;



    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int numOfRow, int numOfBricksInRow) {
        super(windowTitle, windowDimensions);
        this.numOfRows = numOfRow;
        this.numOfBricksInRow = numOfBricksInRow;
        this.bricksHitCounter = 0;
    }

    public void increasebricksHitCounter(){
        bricksHitCounter++;
    }

    public void removeBrick(GameObject brick) {
        System.out.println("collision with brick detected");
        gameObjects().removeGameObject(brick,Layer.STATIC_OBJECTS);
    }


    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        this.windowController = windowController;
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowDimensions = windowController.getWindowDimensions();

        // Creating wallpaper
        createWallPaper(imageReader, windowDimensions);

        // Creating ball
        createBall(windowDimensions, imageReader, soundReader);

        Renderable paddleImage = imageReader.readImage(PADDLE_PICTURE_PATH, true);

        // Creating user paddles
        createPaddle(paddleImage, inputListener, windowDimensions);

        // Creating borders
        createBorder(windowDimensions);

        // Creating WallOfBricks
        Renderable brickImage = imageReader.readImage("assets/brick.png", false);
        CollisionStrategy collisionStrategy = new BasicCollisionStrategy(this);
        createWallOfBricks(windowDimensions, brickImage, collisionStrategy);

        // Creating GraphicHearts
        createGraphicHearts(imageReader, windowDimensions);

        // Creating NumericalHearts
        createNumericalHearts();
    }

    private void createWallOfBricks(Vector2 windowDimensions, Renderable imageReader,CollisionStrategy collisionStrategy) {
        float brickLength = (windowDimensions.x() / (numOfBricksInRow+1));
        float gapFromUpperWall = windowDimensions.mult(0.02f).y();
        //float gapFromLeftWall = windowDimensions.mult(0.02f).x();
        float brickHeight = 15; //make const from args
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfBricksInRow; j++) {
                Brick brick = new Brick(new Vector2((j * brickLength) +(brickLength/(numOfBricksInRow))
                        ,(i*brickHeight)+gapFromUpperWall),
                        new Vector2(brickLength, brickHeight), imageReader,collisionStrategy);
                float x = (j * brickLength) +(brickLength/(numOfBricksInRow-1));
                float y = (i*brickHeight)+gapFromUpperWall;
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }
        }
    }

    private void createBorder(Vector2 windowDimensions) {
        Vector2 topLeftOfUpperAndLeftWall = Vector2.ZERO;
        Vector2 topLeftOfDownWall = new Vector2(0, windowDimensions.y());
        Vector2 topLeftOfRightWall = new Vector2(windowDimensions.x(), 0);

        Vector2 UpAndDownWallDimensions = new Vector2(windowDimensions.x(),windowDimensions.mult(WALL_FACTOR).y());
        Vector2 RightAndLefWallDimensions = new Vector2(windowDimensions.mult(WALL_FACTOR).x(),windowDimensions.y());

        GameObject wallUp = new GameObject(topLeftOfUpperAndLeftWall, UpAndDownWallDimensions, null);
        GameObject wallRight = new GameObject(topLeftOfRightWall, RightAndLefWallDimensions,null);
        GameObject wallLeft = new GameObject(topLeftOfUpperAndLeftWall, RightAndLefWallDimensions,null);
        GameObject wallDown = new GameObject(topLeftOfDownWall, UpAndDownWallDimensions,null);

        Vector2[] wallHeights = {new Vector2(windowDimensions.x()/2, 0),
                new Vector2(windowDimensions.x(), windowDimensions.y()/2),
                new Vector2(0,windowDimensions.y()/2),
                new Vector2(windowDimensions.x()/2, windowDimensions.y())
        };
        GameObject[] walls = {wallUp, wallRight, wallLeft, wallDown};
        for (int i = 0; i < 3; i++) {
            walls[i].setCenter(wallHeights[i]);
            gameObjects().addGameObject(walls[i],Layer.STATIC_OBJECTS); // add
        }
        walls[3].setCenter(wallHeights[3]);
        gameObjects().addGameObject(walls[3],Layer.BACKGROUND); // add

    }
    private Vector2 createRandomVelocity(){
        Random rand = new Random();
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        if(rand.nextBoolean()){
            ballVelX *= -1;
        }
        if(rand.nextBoolean()){
            ballVelY *= -1;
        }
        return new Vector2(ballVelX, ballVelY);
    }

    private void createBall(Vector2 windowDimensions, ImageReader imageReader, SoundReader soundReader){
        Renderable ballImage = imageReader.readImage(BALL_PICTURE_PATH, true);
        Sound collisionSound = soundReader.readSound(CLASH_SOUND_PATH);
        this.ball = new Ball(Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage, collisionSound);
        //GameObject ball = new Ball(Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage, collisionSound);
        ball.setCenter(windowDimensions.mult(0.5f));
        ball.setVelocity(Vector2.DOWN.mult(BALL_SPEED)); // Vector2(0, 100)
        gameObjects().addGameObject(ball); // gets a private field of the father class that holds all the game objects
        ball.setVelocity(createRandomVelocity());
    }

    private void createPaddle(Renderable paddleImage, UserInputListener inputListener,
                              Vector2 windowDimensions){
        GameObject paddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage, inputListener, windowDimensions);
        paddle.setCenter(new Vector2(windowDimensions.x()/2, (int)windowDimensions.y()-30));
        gameObjects().addGameObject(paddle);
    }

    private void createWallPaper(ImageReader imageReader,Vector2 windowDimensions){
        Renderable wallPaperImage = imageReader.readImage(WALLPAPER_PICTURE_PATH, false);
        GameObject wallPaper = new GameObject(Vector2.ZERO, windowDimensions, wallPaperImage);
        wallPaper.setCenter(windowDimensions.mult(0.5f));
        gameObjects().addGameObject(wallPaper, Layer.BACKGROUND);
    }

    private void createGraphicHearts(ImageReader imageReader, Vector2 windowDimensions){
        Renderable heartImage = imageReader.readImage("assets/heart.png", true);
        for (int i = 0; i < numOfHeartsRemain; i++) {
            GameObject heart = new GraphicHeart(Vector2.ZERO, new Vector2(30, 30), heartImage);
            heart.setCenter(new Vector2(30*(i+1), windowDimensions.y()-20));
            gameObjects().addGameObject(heart, Layer.UI);
        }
    }

    private void removeOneHeart(){
        boolean graficHeartFlag = false;
        boolean numericHeartFlag = false;
        for (GameObject gameObject : gameObjects().objectsInLayer(Layer.UI)) {
            String curTag = gameObject.getTag();
            if (curTag.equals("Graphic Heart") && !graficHeartFlag){
                gameObjects().removeGameObject(gameObject, Layer.UI);
                graficHeartFlag = true;
            }
            else if(curTag.equals("Numerical Heart") && !numericHeartFlag){
                NumericalHeart numericHeart = (NumericalHeart) gameObject;
                numericHeart.UpdateNumericalHeart(numOfHeartsRemain);
                numericHeartFlag = true;
            }
        }
    }

    private void createNumericalHearts() {
        TextRenderable numericalHeartText = new TextRenderable("Lives: " + DEFAULT_NUM_OF_LIVES);
        numericalHeartText.setColor(INITIAL_COLOR);
        GameObject numericalHeart = new NumericalHeart(new Vector2(0, windowDimensions.y() - 80),
                new Vector2(30, 30), numericalHeartText);
        gameObjects().addGameObject(numericalHeart, Layer.UI);
    }


    private void checkObjectExit() {
        for (GameObject gameObject : gameObjects().objectsInLayer(Layer.DEFAULT)) {
            double objectHeight = gameObject.getCenter().y();
            if (objectHeight >= windowDimensions.y() - windowDimensions.mult(WALL_FACTOR).y()) {
                switch (gameObject.getTag()) {
                    case "Ball":
                        numOfHeartsRemain--;
                        removeOneHeart(); // check if last heart needs to be dissapired before pop window
                        gameObject.setVelocity(createRandomVelocity());
                        gameObject.setCenter(windowDimensions.mult(0.5f));
                }
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkObjectExit();
        checkForGameEnd();
    }


    private void checkForGameEnd() {
        String prompt = "";
        if (numOfHeartsRemain <= 0){
            prompt = "You Lose!";
        }
        if (bricksHitCounter == numOfBricksInRow*numOfRows){
            prompt = "You Win!";
        }
        //|| inputListener.isKeyPressed(KeyEvent.VK_W))
        if (!prompt.isEmpty()){
            prompt += " Play again?";
            if(windowController.openYesNoDialog(prompt)){
                windowController.resetGame();
                numOfHeartsRemain = DEFAULT_NUM_OF_LIVES;
            }
            else{
                windowController.closeWindow();
            }

        }

    }

    public static void main(String[] args) {
        int numOfRows, numOfBricksInRow = 0;
        if (args.length == 2) {
            numOfBricksInRow =  Integer.parseInt(args[0]);
            numOfRows =  Integer.parseInt(args[1]);
        } else {
            numOfRows = DEFAULT_ROWS;
            numOfBricksInRow = NUM_OF_BRICK;
        }
        BrickerGameManager window = new BrickerGameManager("Bouncing Ball",
                new Vector2(700, 500), numOfRows, numOfBricksInRow);

        window.run();
    }
}
