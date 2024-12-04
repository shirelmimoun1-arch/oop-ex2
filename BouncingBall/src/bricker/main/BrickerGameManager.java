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
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * The main manager class for the Bricker game. This class is responsible for initializing,
 * managing, and updating all game objects, as well as handling core game logic such as
 * collisions, user inputs, game state transitions, and rendering.
 */
public class BrickerGameManager extends GameManager {
    private static final int DEFAULT_ROWS = 7;
    private static final int NUM_OF_BRICK = 8;
    private static final int PADDLE_HEIGHT = 20;
    private static final int PADDLE_WIDTH = 100;
    private static final int BALL_RADIUS = 35;
    private static final int BALL_SPEED = 250;
    private static final float WALL_FACTOR = 0.001f;
    private static final String PADDLE_PICTURE_PATH = "assets/paddle.png";
    private static final String BALL_PICTURE_PATH = "assets/ball.png";
    private static final String BRICK_PICTURE_PATH = "assets/brick.png";
    private static final String CLASH_SOUND_PATH = "assets/blop.wav";
    private static final String WALLPAPER_PICTURE_PATH = "assets/DARK_BG2_small.jpeg";
    private static final String HEART_PICTURE_PATH = "assets/heart.png";
    private static final String WINDOW_TITLE = "Bricker Game";
    private static final Color INITIAL_COLOR = Color.GREEN;
    private static final String BRICK_COLLISION_MESSAGE = "collision with brick detected";
    private static final String EMPTY_PROMPT = "";
    private static final String LOSE_MESSAGE = "You Lose!";
    private static final String WIN_MESSAGE = "You Win!";
    private static final String ASK_TO_PLAY_AGAIN_MESSAGE = " Play again?";
    private static final float FACTOR_OF_HALF = 0.5f;
    private static final float FACTOR_OF_X_GAP = 0.01f;
    private static final float FACTOR_OF_Y_GAP = 0.02f;
    private static final float BRICK_HEIGHT = 15;
    private  int numOfBricksInRow;
    private int numOfRows;
    private WindowController windowController;
    private Vector2 windowDimensions;
    private static final int DEFAULT_NUM_OF_LIVES = 3;
    private int numOfHeartsRemain = DEFAULT_NUM_OF_LIVES;
    private int bricksHitCounter ;
    UserInputListener inputListener;

    /**
     * Constructs a new BrickerGameManager instance.
     * @param windowTitle The title displayed on the game window.
     * @param windowDimensions The dimensions of the game window.
     * @param numOfRow The number of rows in the wall of bricks.
     * @param numOfBricksInRow The number of bricks per row.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int numOfRow, int numOfBricksInRow) {
        super(windowTitle, windowDimensions);
        this.numOfRows = numOfRow;
        this.numOfBricksInRow = numOfBricksInRow;
        this.bricksHitCounter = 0;
    }

    /**
     * Initializes all game objects and sets up the game window and its properties.
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     *                 See its documentation for help.
     * @param soundReader Contains a single method: readSound, which reads a wav file from
     *                    disk. See its documentation for help.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not. See its
     *                      documentation.
     * @param windowController Contains an array of helpful, self explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        this.windowController = windowController;
        windowController.setTargetFramerate(100); // because it sent this error: Warning: your frames are
        // taking too long to update, which means the target frame-rate (120) cannot be reached. If your
        // frame-rate is low, then either your update pass is taking too long (too many objects? an overly
        // complex logic?), or the target frame-rate is set too high for the hardware.

        this.inputListener = inputListener;
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
        Renderable brickImage = imageReader.readImage(BRICK_PICTURE_PATH, false);
        CollisionStrategy collisionStrategy = new BasicCollisionStrategy(this);
        createWallOfBricks(windowDimensions, brickImage, collisionStrategy);

        // Creating GraphicHearts
        createGraphicHearts(imageReader, windowDimensions);

        // Creating NumericalHearts
        createNumericalHearts();
    }

    /**
     * Increments the number of bricks hit in the game.
     */
    public void increasebricksHitCounter(){
        bricksHitCounter++;
    }

    /**
     * Removes a brick from the game upon collision with the ball.
     * @param brick The Brick object that is removed.
     */
    public void removeBrick(GameObject brick) {
        System.out.println(BRICK_COLLISION_MESSAGE);
        gameObjects().removeGameObject(brick,Layer.STATIC_OBJECTS);
    }

    private void createWallOfBricks(Vector2 windowDimensions, Renderable imageReader,
                                    CollisionStrategy collisionStrategy) {
        float gapX = windowDimensions.x() * FACTOR_OF_X_GAP;
        float gapY = windowDimensions.y() * FACTOR_OF_Y_GAP;
        float brickLength = (windowDimensions.x() - (numOfBricksInRow + 1) * gapX) / (numOfBricksInRow);
        float brickHeight = BRICK_HEIGHT;
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfBricksInRow; j++) {
                float x = (j * (brickLength + gapX) ) + gapX;
                float y = i * (gapY+brickHeight);
                Brick brick = new Brick(
                        new Vector2(x, y),
                        new Vector2(brickLength, brickHeight),
                        imageReader,
                        collisionStrategy);
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
        GameObject ball = new Ball(Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage, collisionSound);
        ball.setCenter(windowDimensions.mult(FACTOR_OF_HALF));
        ball.setVelocity(Vector2.DOWN.mult(BALL_SPEED)); // Vector2(0, 100)
        gameObjects().addGameObject(ball); // gets a private field of the father class that holds all the game objects
        ball.setVelocity(createRandomVelocity());
    }

    private void createPaddle(Renderable paddleImage, UserInputListener inputListener,
                              Vector2 windowDimensions){
        GameObject paddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage, inputListener, windowDimensions);
        paddle.setCenter(new Vector2(windowDimensions.x() / 2, (int)windowDimensions.y() - 30));
        gameObjects().addGameObject(paddle);
    }

    private void createWallPaper(ImageReader imageReader,Vector2 windowDimensions){
        Renderable wallPaperImage = imageReader.readImage(WALLPAPER_PICTURE_PATH, false);
        GameObject wallPaper = new GameObject(Vector2.ZERO, windowDimensions, wallPaperImage);
        wallPaper.setCenter(windowDimensions.mult(FACTOR_OF_HALF));
        gameObjects().addGameObject(wallPaper, Layer.BACKGROUND);
    }

    private void createGraphicHearts(ImageReader imageReader, Vector2 windowDimensions){
        Renderable heartImage = imageReader.readImage(HEART_PICTURE_PATH, true);
        for (int i = 0; i < numOfHeartsRemain; i++) {
            GameObject heart = new GraphicHeart(Vector2.ZERO, new Vector2(30, 30), heartImage);
            heart.setCenter(new Vector2(30 * (i + 1), windowDimensions.y() - 20));
            gameObjects().addGameObject(heart, Layer.UI);
        }
    }

    private void removeOneHeart(){
        boolean graficHeartFlag = false;
        boolean numericHeartFlag = false;
        for (GameObject gameObject : gameObjects().objectsInLayer(Layer.UI)) {
            String curTag = gameObject.getTag();
            if (curTag.equals(GraphicHeart.GRAPHIC_HEART_STRING) && !graficHeartFlag){
                gameObjects().removeGameObject(gameObject, Layer.UI);
                graficHeartFlag = true;
            }
            else if(curTag.equals(NumericalHeart.NUMERICAL_HEART_STRING) && !numericHeartFlag){
                NumericalHeart numericHeart = (NumericalHeart) gameObject;
                numericHeart.UpdateNumericalHeart(numOfHeartsRemain);
                numericHeartFlag = true;
            }
        }
    }

    private void createNumericalHearts() {
        TextRenderable numericalHeartText = new TextRenderable(NumericalHeart.NUMERICAL_HEART_TEXT + DEFAULT_NUM_OF_LIVES);
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
                    case Ball.BALL_STRING:
                        numOfHeartsRemain--;
                        removeOneHeart(); // check if last heart needs to be dissapired before pop window
                        gameObject.setVelocity(createRandomVelocity());
                        gameObject.setCenter(windowDimensions.mult(FACTOR_OF_HALF));
                }
            }
        }
    }

    /**
     * Updates the game current state based on the objects. Checks if the game end and prompts a message
     * accordingly.
     * @param deltaTime The time, in seconds, that passed since the last invocation
     *                  of this method (i.e., since the last frame). This is useful
     *                  for either accumulating the total time that passed since some
     *                  event, or for physics integration (i.e., multiply this by
     *                  the acceleration to get an estimate of the added velocity or
     *                  by the velocity to get an estimate of the difference in position).
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkObjectExit();
        checkForGameEnd();
    }

    private void checkForGameEnd() {
        String prompt = EMPTY_PROMPT;
        if (numOfHeartsRemain <= 0){
            prompt = LOSE_MESSAGE;
        }
        if (bricksHitCounter == numOfBricksInRow*numOfRows || inputListener.isKeyPressed(KeyEvent.VK_W)){
            prompt = WIN_MESSAGE;
        }
        if (!prompt.isEmpty()){
            prompt += ASK_TO_PLAY_AGAIN_MESSAGE;
            if(windowController.openYesNoDialog(prompt)){
                windowController.resetGame();
                numOfHeartsRemain = DEFAULT_NUM_OF_LIVES;
            }
            else{
                windowController.closeWindow();
            }

        }

    }

    /**
     * The main function that runs the game.
     * @param args The arguments given by the user.
     */
    public static void main(String[] args) {
        int numOfRows, numOfBricksInRow;
        if (args.length == 2) {
            numOfBricksInRow =  Integer.parseInt(args[0]);
            numOfRows =  Integer.parseInt(args[1]);
        } else {
            numOfRows = DEFAULT_ROWS;
            numOfBricksInRow = NUM_OF_BRICK;
        }
        BrickerGameManager window = new BrickerGameManager(WINDOW_TITLE,
                new Vector2(700, 500), numOfRows, numOfBricksInRow);

        window.run();
    }
}

//todo:
// 1) check if last heart needs to be disappeared before pop window
// 2) check the error:
//         Warning: your frames are
//         taking too long to update, which means the target frame-rate (120) cannot be reached. If your
//         frame-rate is low, then either your update pass is taking too long (too many objects? an overly
//         complex logic?), or the target frame-rate is set too high for the hardware.
// 3) check if the arguments are always valid or it needs to br checked
// 4) explain why we added UpdateNumericalHeart in numericalheart class
