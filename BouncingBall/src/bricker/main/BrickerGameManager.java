package bricker.main;

import bricker.brick_strategies.CollisionStrategy;
import bricker.brick_strategies.ExtraBallCollisionStrategy;
import bricker.factories.*;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * The main manager class for the Bricker game. This class is responsible for initializing,
 * managing, and updating all game objects, as well as handling core game logic such as
 * collisions, user inputs, game state transitions, and rendering.
 */
public class BrickerGameManager extends GameManager {
    // Game window and configuration constants
    /**
     * Number of parameters given by the user.
     */
    public static final int NUM_OF_ARGUMENTS = 2;
    /**
     * Title of the game window
     */
    public static final String WINDOW_TITLE = "Bricker Game";
    /**
     * Width of the game window.
     */
    public static final float WINDOW_WIDTH = 700;
    /**
     * Height of the game window
     */
    public static final float WINDOW_HEIGHT = 500;
    /**
     * Default number of rows of bricks
     */
    public static final int DEFAULT_ROWS = 7;
    /**
     * Default number of bricks per row
     */
    public static final int NUM_OF_BRICK = 8;
    /**
     * Default number of lives
     */
    public static final int DEFAULT_NUM_OF_LIVES = 3;
    /**
     * Maximum number of lives
     */
    public static final int MAX_NUM_OF_LIVES = 4;

    // Game-related constants
    /**
     * Tag for the game borders
     */
    public static final String BORDER_NAME = "Border";
    /**
     * Path to wallpaper image.
     */
    public static final String WALLPAPER_PICTURE_PATH = "assets/DARK_BG2_small.jpeg";

    /**
     * Gap factor for horizontal brick spacing
     */
    public static final float FACTOR_OF_X_GAP = 0.01f;
    /**
     * Gap factor for vertical brick spacing
     */
    public static final float FACTOR_OF_Y_GAP = 0.02f;
    /**
     * The width of the wall as a fraction of the game window.
     */
    public static final float WALL_FACTOR = 0.01f;
    /**
     * A half factor.
     */
    public static final float FACTOR_OF_HALF = 0.5f;
    /**
     * An empty string.
     */
    public static final String EMPTY_PROMPT = "";
    /**
     * Message shown when player loses.
     */
    public static final String LOSE_MESSAGE = "You Lose!";
    /**
     * Message shown when player wins
     */
    public static final String WIN_MESSAGE = "You Win!";
    /**
     * Prompt to play again.
     */
    public static final String ASK_TO_PLAY_AGAIN_MESSAGE = " Play again?";

    /**
     * Image reader for loading images.
     */
    public ImageReader imageReader;
    /**
     * Sound reader for loading sound files.
     */
    public SoundReader soundReader;
    /**
     * Dimensions of the game window.
     */
    public Vector2 windowDimensions;
    /**
     * User input listener for detecting key presses.
     */
    public UserInputListener inputListener;

    private StrategyFactory brickStrategyFactory;
    private int numOfBricksInRow;
    private int numOfRows;
    private int bricksHitCounter ;
    private WindowController windowController;
    private int numOfHeartsRemain;


    /**
     * Constructs a new BrickerGameManager instance.
     * @param windowTitle The title displayed on the game window.
     * @param windowDimensions The dimensions of the game window.
     * @param numOfRow The number of rows in the wall of bricks.
     * @param numOfBricksInRow The number of bricks per row.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int numOfRow,
                              int numOfBricksInRow) {
        super(windowTitle, windowDimensions);
        this.numOfRows = numOfRow;
        this.numOfBricksInRow = numOfBricksInRow;
        this.bricksHitCounter = 0;
        this.brickStrategyFactory = new StrategyFactory(this);
        this.numOfHeartsRemain = DEFAULT_NUM_OF_LIVES;
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
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.windowDimensions = windowController.getWindowDimensions();
        this.windowController = windowController;
        this.inputListener = inputListener;

        createWallPaper(imageReader, windowDimensions);
        createBorder(windowDimensions, BORDER_NAME);
        createWallOfBricks(windowDimensions, imageReader, numOfRows, numOfBricksInRow,
                Brick.BRICK_PICTURE_PATH);
        createBall(windowDimensions.mult(FACTOR_OF_HALF), Ball.BALL_RADIUS,
                createRandomVelocity(Ball.BALL_SPEED), Ball.BALL_PICTURE_PATH,
                Ball.CLASH_SOUND_PATH, Ball.BALL_NAME);

        Vector2 startingPlacePaddle = new Vector2(
                windowDimensions.mult(FACTOR_OF_HALF).x(),
                windowDimensions.y()-Paddle.PADDLE_GAP_FROM_BOTTOM_WINDOW);
        createPaddle(Paddle.PADDLE_PICTURE_PATH,startingPlacePaddle,
                Paddle.PADDLE_HEIGHT,Paddle.PADDLE_WIDTH,Paddle.PADDLE_NAME);

        for (int i = 0; i < numOfHeartsRemain; i++) {
            Vector2 setPlace = new Vector2
                    (GraphicHeart.HEART_SIZE * (i + 1),
                    windowDimensions.y() - GraphicHeart.GRAPHIC_HEART_GAP_FROM_BOTTOM_WINDOW);
            createGraphicHearts(GraphicHeart.GRAPHIC_HEART_PICTURE_PATH, setPlace,
                    Layer.UI,GraphicHeart.GRAPHIC_HEART_NAME, Vector2.ZERO);
        }
        createNumericalHearts(DEFAULT_NUM_OF_LIVES, NumericHeart.NUMERICAL_HEART_NAME);
    }

    private void createWallPaper(ImageReader imageReader, Vector2 windowDimensions){
        Renderable wallPaperImage = imageReader.readImage(WALLPAPER_PICTURE_PATH, false);
        GameObject wallPaper = new GameObject(Vector2.ZERO, windowDimensions, wallPaperImage);
        wallPaper.setCenter(windowDimensions.mult(FACTOR_OF_HALF));
        addObjectsToTheList(wallPaper, Layer.BACKGROUND);
    }

    /**
     * Creates a ball.
     * @param ballPosition the position of the ball.
     * @param ballRadius the radius of the ball.
     * @param ballSpeed the speed of the ball.
     * @param ballPicturePath the path to the ball's picture.
     * @param clashSound the path to the ball's clash sound.
     * @param ballName the name of the ball.
     */
    public void createBall(Vector2 ballPosition, float ballRadius, Vector2 ballSpeed,
                           String ballPicturePath, String clashSound, String ballName){
        Renderable ballImage = imageReader.readImage(ballPicturePath, true);
        Sound collisionSound = soundReader.readSound(clashSound);
        Vector2 ballDimensions = new Vector2(ballRadius, ballRadius);
        GameObject ball = new Ball(Vector2.ZERO, ballDimensions, ballImage, collisionSound);
        ball.setCenter(ballPosition);
        ball.setTag(ballName);
        ball.setVelocity(ballSpeed);
        addObjectsToTheList(ball);
    }

    /**
     * Creates a paddle object and adds it to the game.
     * @param paddlePicturePath The path to the picture of the paddle.
     * @param startingPlace The starting place of the paddle.
     * @param paddleHeight Paddle height.
     * @param paddleWidth  Paddle width.
     * @param paddleName The name of the paddle.
     */
    public void createPaddle(String paddlePicturePath, Vector2 startingPlace,
                             int paddleHeight, int paddleWidth, String paddleName){
        Renderable paddleImage = imageReader.readImage(paddlePicturePath, true);
        GameObject paddle = new Paddle(Vector2.ZERO, new Vector2(paddleWidth, paddleHeight),
                paddleImage,this);
        paddle.setCenter(startingPlace);
        paddle.setTag(paddleName);
        addObjectsToTheList(paddle, Layer.DEFAULT);
    }

    private void createBorder(Vector2 windowDimensions, String borderName) {
        Vector2 topLeftOfDownWall = new Vector2(0, windowDimensions.y());
        Vector2 topLeftOfRightWall = new Vector2(windowDimensions.x(), 0);

        Vector2 UpAndDownWallDimensions =
                new Vector2(windowDimensions.x(), windowDimensions.mult(WALL_FACTOR).y());
        Vector2 RightAndLefWallDimensions =
                new Vector2(windowDimensions.mult(WALL_FACTOR).x(), windowDimensions.y());

        GameObject wallUp = new GameObject(Vector2.ZERO, UpAndDownWallDimensions, null);
        GameObject wallRight = new GameObject(topLeftOfRightWall, RightAndLefWallDimensions,null);
        GameObject wallLeft = new GameObject(Vector2.ZERO, RightAndLefWallDimensions,null);
        GameObject wallDown = new GameObject(topLeftOfDownWall, UpAndDownWallDimensions,null);

        GameObject[] walls = {wallUp, wallRight, wallLeft, wallDown};
        for (int i = 0; i < 3; i++) {
            walls[i].setTag(borderName);
            addObjectsToTheList(walls[i], Layer.STATIC_OBJECTS);
        }

        walls[3].setTag(borderName);
        addObjectsToTheList(walls[3], Layer.BACKGROUND);
    }

    private void createWallOfBricks(Vector2 windowDimensions,
                                   ImageReader imageReader,
                                   int numOfRows,
                                   int numOfBricksInRow,String imagePath) {
        Renderable brickImage = imageReader.readImage(imagePath, false);
        float gapX = windowDimensions.x() * FACTOR_OF_X_GAP;
        float gapY = windowDimensions.y() * FACTOR_OF_Y_GAP;
        float brickLength = (windowDimensions.x() - (numOfBricksInRow + 1) * gapX) / (numOfBricksInRow);
        float brickHeight = Brick.BRICK_HEIGHT;
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfBricksInRow; j++) {
                float x = (j * (brickLength + gapX) ) + gapX;
                float y = i * (gapY+brickHeight);
                addASingleBrick(brickStrategyFactory,
                        new Vector2(x, y),
                        new Vector2(brickLength, brickHeight),
                        brickImage);
            }
        }
    }

    private void addASingleBrick(StrategyFactory brickStrategyFactory, Vector2 topLeftCorner,
                                 Vector2 dimensions, Renderable brickImage){
        CollisionStrategy collisionStrategy = brickStrategyFactory.createStrategy();
        Brick brick = new Brick(topLeftCorner, dimensions, brickImage, collisionStrategy);
        brick.setTag(Brick.BRICK_NAME);
        addObjectsToTheList(brick, Layer.STATIC_OBJECTS);
    }

    /**
     * Creates a new heart object with the specified image, position, layer, and tag.
     * @param pathImage The path to the image file for the heart.
     * @param setPlace The position of the heart object.
     * @param layer The layer on which the heart object will be rendered.
     * @param graphicHeartName The tag for the heart object.
     * @param velocity The velocity of the heart object.
     */
    public void createGraphicHearts(String pathImage,
                                    Vector2 setPlace,
                                    int layer,
                                    String graphicHeartName,
                                    Vector2 velocity) {
        Renderable heartImage = imageReader.readImage(pathImage, true);
        GameObject heart = new GraphicHeart(Vector2.ZERO,
                new Vector2(GraphicHeart.HEART_SIZE, GraphicHeart.HEART_SIZE),
                heartImage, this);
        heart.setCenter(setPlace);
        heart.setVelocity(velocity);
        heart.setTag(graphicHeartName);
        addObjectsToTheList(heart, layer);
    }

    /**
     * Creates the numerical hearts in the game.
     * @param numOfLives The number of lives remaining.
     * @param numericalHeartName The name of the numerical heart object.
     */
    public void createNumericalHearts(int numOfLives,String numericalHeartName) {
        TextRenderable numericalHeartText = new TextRenderable(
                NumericHeart.NUMERICAL_HEART_TEXT_FORMAT + numOfLives);
        numericalHeartText.setColor(NumericHeart.INITIAL_COLOR);
        GameObject numericalHeart = new NumericHeart(
                new Vector2(0, windowDimensions.y() -
                        NumericHeart.NUMERIC_HEART_GAP_FROM_BOTTOM_WINDOW),
                new Vector2(GraphicHeart.HEART_SIZE, GraphicHeart.HEART_SIZE), numericalHeartText);
        numericalHeart.setTag(numericalHeartName);
        addObjectsToTheList(numericalHeart, Layer.UI);
    }

    /**
     * Adds a game object to the game's list of objects.
     * @param gameObject The game object to be added.
     */
    public void addObjectsToTheList(GameObject gameObject) {
        gameObjects().addGameObject(gameObject);
    }

    /**
     * Adds a game object to the game's list of objects, specifying the layer.
     * @param gameObject The game object to be added.
     * @param layer The layer to which the game object should be added.
     */
    public void addObjectsToTheList(GameObject gameObject, int layer) {
        gameObjects().addGameObject(gameObject, layer);
    }

    /**
     * Removes a game object from the game's list of objects unless bricks.
     * @param gameObject The game object to be removed.
     */
    public void removeGameObject(GameObject gameObject) {
        if (gameObject != null) {
            if(gameObject.getTag().startsWith(Brick.BRICK_NAME)) {
                boolean disappearFlag = gameObjects().removeGameObject(gameObject, Layer.STATIC_OBJECTS);
                if (disappearFlag) {
                    increaseBricksHitCounter();
                }
            }
            else {
                gameObjects().removeGameObject(gameObject);
            }
        }
    }

    /**
     * Increments the number of bricks hit in the game.
     */
    public void increaseBricksHitCounter(){

        bricksHitCounter++;
    }

    /**
     * Creates a random velocity vector for the ball.
     * @param ballSpeed The speed of the ball.
     * @return A Vector2 object representing the random velocity.
     */
    public Vector2 createRandomVelocity(float ballSpeed){
        Random rand = new Random();
        float ballVelX =  ballSpeed;
        float ballVelY = ballSpeed;
        if(rand.nextBoolean()){
            ballVelX *= -1;
        }
        if(rand.nextBoolean()) {
            ballVelY *= -1;
        }
        return new Vector2(ballVelX, ballVelY);
    }

    private void decreaseLives(){
        boolean graphicHeartFlag = false;
        boolean numericalHeartFlag = false;
        for (GameObject gameObject : gameObjects().objectsInLayer(Layer.UI)) {
            String curTag = gameObject.getTag();
            if (curTag.equals(GraphicHeart.GRAPHIC_HEART_NAME) && !graphicHeartFlag){
                if (gameObject.getCenter().x() == (numOfHeartsRemain + 1) * GraphicHeart.HEART_SIZE) {
                    gameObjects().removeGameObject(gameObject, Layer.UI);
                    graphicHeartFlag = true;
                }
            }
            if(curTag.equals(NumericHeart.NUMERICAL_HEART_NAME) && !numericalHeartFlag){
                NumericHeart numericHeart = (NumericHeart) gameObject;
                numericHeart.UpdateNumericalHeart(numOfHeartsRemain);
                numericalHeartFlag = true;
            }
        }
    }

    /**
     * Increases the number of lives (hearts) in the game by one.
     */
    public void increaseLives() {
        if (numOfHeartsRemain < MAX_NUM_OF_LIVES) {
            numOfHeartsRemain++;
            Vector2 setPlace = (new Vector2(GraphicHeart.HEART_SIZE * (numOfHeartsRemain),
                    windowDimensions.y() - GraphicHeart.GRAPHIC_HEART_GAP_FROM_BOTTOM_WINDOW));
            createGraphicHearts(GraphicHeart.GRAPHIC_HEART_PICTURE_PATH, setPlace,
                    Layer.UI,GraphicHeart.GRAPHIC_HEART_NAME, Vector2.ZERO);
            for (GameObject gameObject : gameObjects().objectsInLayer(Layer.UI)) {
                String curTag = gameObject.getTag();
                if (curTag.equals(NumericHeart.NUMERICAL_HEART_NAME)) {
                    NumericHeart numericHeart = (NumericHeart) gameObject;
                    numericHeart.UpdateNumericalHeart(numOfHeartsRemain);
                }
            }
        }
    }

    private void checkObjectExit() {
        for (GameObject gameObject : gameObjects().objectsInLayer(Layer.DEFAULT)) {
            double objectHeight = gameObject.getCenter().y();
            if (objectHeight >= windowDimensions.y() - windowDimensions.mult(WALL_FACTOR).y()) {
                switch (gameObject.getTag()) {
                    case Ball.BALL_NAME:
                        numOfHeartsRemain--;
                        decreaseLives();
                        gameObject.setCenter(windowDimensions.mult(FACTOR_OF_HALF));
                        break;
                    case ExtraBallCollisionStrategy.PUCK_BALL_NAME, GraphicHeart.FALLING_HEART_NAME:
                        gameObjects().removeGameObject(gameObject);
                        break;
                }
            }
        }
    }

    /**
     * Checks if an extra paddle currently exists in the game.
     * @return true if an extra paddle exists, false otherwise.
     */
    public boolean doesExtraPaddleExist() {
        for (GameObject gameObject : gameObjects().objectsInLayer(Layer.DEFAULT)) {
            if (gameObject.getTag().equals(Paddle.EXTRA_PADDLE_NAME)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Updates the game current state based on the objects. Checks if the game end and prompts a message
     * accordingly.
     * @param deltaTime The time, in seconds, that passed since the last invocation
     *                  of this method (i.e., since the last frame).
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
        if (bricksHitCounter == numOfBricksInRow * numOfRows || inputListener.isKeyPressed(KeyEvent.VK_W)) {
            prompt = WIN_MESSAGE;
        }
        if (!prompt.isEmpty()){
            prompt += ASK_TO_PLAY_AGAIN_MESSAGE;
            if(windowController.openYesNoDialog(prompt)){
                windowController.resetGame();
                numOfHeartsRemain = DEFAULT_NUM_OF_LIVES;
                bricksHitCounter = 0;
            }
            else {
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
        if (args.length == NUM_OF_ARGUMENTS) {
            numOfBricksInRow =  Integer.parseInt(args[0]);
            numOfRows =  Integer.parseInt(args[1]);
        } else {
            numOfRows = DEFAULT_ROWS;
            numOfBricksInRow = NUM_OF_BRICK;
        }
        BrickerGameManager window = new BrickerGameManager(WINDOW_TITLE,
                new Vector2(WINDOW_WIDTH, WINDOW_HEIGHT), numOfRows, numOfBricksInRow);
        window.run();
    }
}
