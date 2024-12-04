package bricker.main;

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
    private static final int DEFAULT_ROWS = 7;
    private static final int NUM_OF_BRICK = 8;
    private static final int BALL_SPEED = 250;
    private static final float WALL_FACTOR = 0.001f;
    private static final String WINDOW_TITLE = "Bricker Game";
    private static final String BRICK_COLLISION_MESSAGE = "collision with brick detected";
    private static final String EMPTY_PROMPT = "";
    private static final String LOSE_MESSAGE = "You Lose!";
    private static final String WIN_MESSAGE = "You Win!";
    private static final String ASK_TO_PLAY_AGAIN_MESSAGE = " Play again?";
    private static final float FACTOR_OF_HALF = 0.5f;
    private  int numOfBricksInRow;
    private int numOfRows;
    private WindowController windowController;
    private Vector2 windowDimensions;
    private static final int DEFAULT_NUM_OF_LIVES = 3;
    private int numOfHeartsRemain = DEFAULT_NUM_OF_LIVES;
    private int bricksHitCounter ;
    private UserInputListener inputListener;

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
        ballFactory = new BallFactory(this);
        paddleFactory =  new PaddleFactory(this);
        wallOfBricks = new WallOfBricksFactory(this);
        wallPaperFactory = new WallPaperFactory(this);
        bordersFactory = new BordersFactory(this);
        numericHeartFactory = new NumericHeartFactory(this);
        graphicHeartFactory = new GraphicHeartFactory(this);
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
        wallPaperFactory.createWallPaper(imageReader, windowDimensions);

        // Creating ball
        ballFactory.createBall(windowDimensions, imageReader, soundReader);

        // Creating user paddles
        paddleFactory.createPaddle(inputListener, windowDimensions, imageReader);

        // Creating borders
        bordersFactory.createBorder(windowDimensions);

        // Creating WallOfBricks
        wallOfBricks.createWallOfBricks(windowDimensions, imageReader, numOfRows, numOfBricksInRow);

        // Creating GraphicHearts
        graphicHeartFactory.createGraphicHearts(imageReader, windowDimensions, numOfHeartsRemain);

        // Creating NumericalHearts
        numericHeartFactory.createNumericalHearts(windowDimensions);
    }

    public void addObjectsToTheList(GameObject gameObject) {
        gameObjects().addGameObject(gameObject);
    }

    public void addObjectsToTheList(GameObject gameObject, int layer) {
        gameObjects().addGameObject(gameObject, layer);
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

    public Vector2 createRandomVelocity(){
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
// 4) explain why we added UpdateNumericalHeart in numerical heart class
// 5) should I, and how to create an object game factory
