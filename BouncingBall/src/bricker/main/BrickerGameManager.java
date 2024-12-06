package bricker.main;

import bricker.brick_strategies.ExtraBallCollisionStrategy;
import bricker.factories.*;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * The main manager class for the Bricker game. This class is responsible for initializing,
 * managing, and updating all game objects, as well as handling core game logic such as
 * collisions, user inputs, game state transitions, and rendering.
 */
public class BrickerGameManager extends GameManager {
    public static final int NUM_OF_ARGUMENTS = 2;
    public static final float WINDOW_WIDTH = 700;
    public static final float WINDOW_HEIGHT = 500;
    public static final int DEFAULT_ROWS = 7;
    public static final int NUM_OF_BRICK = 8;
    public static final int BALL_SPEED = 200;
    public static final float WALL_FACTOR = 0.001f;
    public static final float FACTOR_OF_HALF = 0.5f;
    public static final int DEFAULT_NUM_OF_LIVES = 3;
    public static final int MAX_NUM_OF_HEARTS = 4;
    public static final int TARGET_FRAME_RATE = 100;
    public static final String BRICK_PICTURE_PATH = "assets/brick.png";
    public static final String HEART_PICTURE_PATH = "assets/heart.png";
    public static final String WINDOW_TITLE = "Bricker Game";
    public static final String BRICK_COLLISION_MESSAGE = "Collision with brick detected";
    public static final String EMPTY_PROMPT = "";
    public static final String LOSE_MESSAGE = "You Lose!";
    public static final String WIN_MESSAGE = "You Win!";
    public static final String ASK_TO_PLAY_AGAIN_MESSAGE = " Play again?";
    private  int numOfBricksInRow;
    private int numOfRows;
    public int numOfHeartsRemain;
    private int bricksHitCounter ;
    public ImageReader imageReader;
    public SoundReader soundReader;
    private WindowController windowController;
    public Vector2 windowDimensions;
    public UserInputListener inputListener;
    private BallFactory ballFactory;
    private PaddleFactory paddleFactory;
    private WallOfBricksFactory wallOfBricks;
    private WallPaperFactory wallPaperFactory;
    private BordersFactory bordersFactory;
    private NumericHeartFactory numericHeartFactory;
    private GraphicHeartFactory graphicHeartFactory;

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
        this.ballFactory = new BallFactory(this);
        this.paddleFactory =  new PaddleFactory(this);
        this.wallOfBricks = new WallOfBricksFactory(this);
        this.wallPaperFactory = new WallPaperFactory(this);
        this.bordersFactory = new BordersFactory(this);
        this.numericHeartFactory = new NumericHeartFactory(this);
        this.graphicHeartFactory = new GraphicHeartFactory(this);
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
        windowController.setTargetFramerate(TARGET_FRAME_RATE);
        // because it sent this error: Warning: your frames are
        // taking too long to update, which means the target frame-rate (120) cannot be reached. If your
        // frame-rate is low, then either your update pass is taking too long (too many objects? an overly
        // complex logic?), or the target frame-rate is set too high for the hardware.

        // Creating wallpaper
        wallPaperFactory.createWallPaper(imageReader, windowDimensions);

        // Creating ball
        ballFactory.createBall(windowDimensions.mult(FACTOR_OF_HALF), Ball.BALL_RADIUS,
                createRandomVelocity(BALL_SPEED), Ball.BALL_PICTURE_PATH,
                Ball.CLASH_SOUND_PATH, Ball.BALL_NAME);

        // Creating user paddles
        Vector2 startingPlacePaddle = new Vector2(
                windowDimensions.mult(FACTOR_OF_HALF).x(),
                windowDimensions.y()-Paddle.PADDLE_GAP_FROM_BUTTOM_WINDOW);
        paddleFactory.createPaddle(Paddle.PADDLE_PICTURE_PATH,startingPlacePaddle,
                Paddle.PADDLE_HEIGHT,Paddle.PADDLE_WIDTH,Paddle.PADDLE_NAME);

        // Creating borders
        bordersFactory.createBorder(windowDimensions, BordersFactory.BORDER_NAME);

        // Creating WallOfBricks
        wallOfBricks.createWallOfBricks(windowDimensions, imageReader, numOfRows, numOfBricksInRow,
                BRICK_PICTURE_PATH);
        // Creating GraphicHearts
        for (int i = 0; i < numOfHeartsRemain; i++) {
            Vector2 setPlace = new Vector2
                    (GraphicHeart.HEART_SIZE * (i + 1),
                    windowDimensions.y() - GraphicHeart.GRAPHIC_HEART_GAP_FROM_BUTTOM_WINDOW);
            graphicHeartFactory.createGraphicHearts(HEART_PICTURE_PATH, setPlace,
                    Layer.UI,GraphicHeart.GRAPHIC_HEART_STRING, Vector2.ZERO);
        }
        // Creating NumericalHearts
        numericHeartFactory.createNumericalHearts(windowDimensions,DEFAULT_NUM_OF_LIVES,NumericalHeart.NUMERICAL_HEART_STRING);
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
                System.out.println(BRICK_COLLISION_MESSAGE);
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
        if(rand.nextBoolean()){
            ballVelY *= -1;
        }
        return new Vector2(ballVelX, ballVelY);
    }


    private void decreaseLives(){
        boolean graphicHeartFlag = false;
        boolean numericalHeartFlag = false;
        for (GameObject gameObject : gameObjects().objectsInLayer(Layer.UI)) {
            String curTag = gameObject.getTag();
            if (curTag.equals(GraphicHeart.GRAPHIC_HEART_STRING) && !graphicHeartFlag){
                //remove the last heart from the list
                if (gameObject.getCenter().x() == (numOfHeartsRemain+1)*GraphicHeart.HEART_SIZE) {
                    gameObjects().removeGameObject(gameObject, Layer.UI);
                    graphicHeartFlag = true;
                }
            }
            if(curTag.equals(NumericalHeart.NUMERICAL_HEART_STRING) && !numericalHeartFlag){
//                System.out.println(true);
                NumericalHeart numericHeart = (NumericalHeart) gameObject;
                numericHeart.UpdateNumericalHeart(numOfHeartsRemain);
                numericalHeartFlag = true;
            }
        }
//        System.out.println(numOfHeartsRemain);
    }

    /**
     * Increases the number of lives (hearts) in the game by one.
     */
    public void increaseLives() {
        if (numOfHeartsRemain < MAX_NUM_OF_HEARTS) {
            numOfHeartsRemain++;
//            System.out.println(numOfHeartsRemain);
            Vector2 setPlace = (new Vector2(GraphicHeart.HEART_SIZE * (numOfHeartsRemain),
                    windowDimensions.y() - GraphicHeart.GRAPHIC_HEART_GAP_FROM_BUTTOM_WINDOW));
            graphicHeartFactory.createGraphicHearts(HEART_PICTURE_PATH, setPlace,
                    Layer.UI,GraphicHeart.GRAPHIC_HEART_STRING, Vector2.ZERO);
            for (GameObject gameObject : gameObjects().objectsInLayer(Layer.UI)) {
                String curTag = gameObject.getTag();
                if (curTag.equals(NumericalHeart.NUMERICAL_HEART_STRING)) {
                    NumericalHeart numericHeart = (NumericalHeart) gameObject;
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

                    case ExtraBallCollisionStrategy.PUCK_BALL_NAME, GraphicHeart.FALLING_HEART_STRING:
                        gameObjects().removeGameObject(gameObject);
                        break;
                }
            }
        }
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

//todo:
// 1) check if last heart needs to be disappeared before pop window
// 2) check the error:
//         Warning: your frames are
//         taking too long to update, which means the target frame-rate (120) cannot be reached. If your
//         frame-rate is low, then either your update pass is taking too long (too many objects? an overly
//         complex logic?), or the target frame-rate is set too high for the hardware.
// 3) check if the arguments are always valid or it needs to br checked V
// 4) explain why we added UpdateNumericalHeart in numerical heart class
// 5) should I, and how to create an object game factory
// 6) should we add lottery factor in wall of bricks factory?
// 7) is it bad practice?
//        this.imageReader = imageReader;
//        this.soundReader = soundReader;
//        this.windowDimensions = windowController.getWindowDimensions();
//        this.windowController = windowController;
//        this.inputListener = inputListener;
// 8) should we enter a velocity to graphic heart creation func?
