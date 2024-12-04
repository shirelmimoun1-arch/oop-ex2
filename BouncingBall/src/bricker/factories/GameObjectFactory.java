//package bricker.factories;
//
//import bricker.brick_strategies.BasicCollisionStrategy;
//import bricker.brick_strategies.CollisionStrategy;
//import bricker.gameobjects.*;
//import bricker.main.BrickerGameManager;
//import danogl.GameManager;
//import danogl.GameObject;
//import danogl.collisions.Layer;
//import danogl.gui.ImageReader;
//import danogl.gui.Sound;
//import danogl.gui.SoundReader;
//import danogl.gui.UserInputListener;
//import danogl.gui.rendering.Renderable;
//import danogl.gui.rendering.TextRenderable;
//import danogl.util.Vector2;
//
//import java.awt.*;
//
//public class GameObjectFactory {
//    public static final String WALLPAPER_PICTURE_PATH = "assets/DARK_BG2_small.jpeg";
//    public static final float FACTOR_OF_HALF = 0.5f;
//    public static final String BRICK_PICTURE_PATH = "assets/brick.png";
//    public static final float FACTOR_OF_X_GAP = 0.01f;
//    public static final float FACTOR_OF_Y_GAP = 0.02f;
//    public static final float BRICK_HEIGHT = 15;
//    public static final int PADDLE_HEIGHT = 20;
//    public static final int PADDLE_WIDTH = 100;
//    public static final String PADDLE_PICTURE_PATH = "assets/paddle.png";
//    public static final float WALL_FACTOR = 0.001f;
//    private static final String HEART_PICTURE_PATH = "assets/heart.png";
//    public static final String BALL_PICTURE_PATH = "assets/ball.png";
//    public static final String CLASH_SOUND_PATH = "assets/blop.wav";
//    public static final int BALL_RADIUS = 35;
//    public static final int BALL_SPEED = 250;
//    public static final float FACTOR_OF_HALF = 0.5f;
//    public static final int DEFAULT_NUM_OF_LIVES = 3;
//    public static final Color INITIAL_COLOR = Color.GREEN;
//    private BrickerGameManager brickerGameManager;
//
//    public GameObjectFactory(GameManager brickerGameManager) {
//        this.brickerGameManager = (BrickerGameManager)brickerGameManager;
//    }
//
//    public void createGameObject(Vector2 windowDimensions, ImageReader imageReader, SoundReader soundReader){
//        Renderable ballImage = imageReader.readImage(BALL_PICTURE_PATH, true);
//        Sound collisionSound = soundReader.readSound(CLASH_SOUND_PATH);
//        GameObject ball = new Ball(Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage, collisionSound);
//        ball.setCenter(windowDimensions.mult(FACTOR_OF_HALF));
//        ball.setVelocity(Vector2.DOWN.mult(BALL_SPEED)); // Vector2(0, 100)
//        brickerGameManager.addObjectsToTheList(ball); // gets a private field of the father class that holds all the game objects
//        ball.setVelocity(brickerGameManager.createRandomVelocity());
//    }
//
//    public void createGameObject(Vector2 windowDimensions) {
//        Vector2 topLeftOfUpperAndLeftWall = Vector2.ZERO;
//        Vector2 topLeftOfDownWall = new Vector2(0, windowDimensions.y());
//        Vector2 topLeftOfRightWall = new Vector2(windowDimensions.x(), 0);
//
//        Vector2 UpAndDownWallDimensions = new Vector2(windowDimensions.x(),windowDimensions.mult(WALL_FACTOR).y());
//        Vector2 RightAndLefWallDimensions = new Vector2(windowDimensions.mult(WALL_FACTOR).x(),windowDimensions.y());
//
//        GameObject wallUp = new GameObject(topLeftOfUpperAndLeftWall, UpAndDownWallDimensions, null);
//        GameObject wallRight = new GameObject(topLeftOfRightWall, RightAndLefWallDimensions,null);
//        GameObject wallLeft = new GameObject(topLeftOfUpperAndLeftWall, RightAndLefWallDimensions,null);
//        GameObject wallDown = new GameObject(topLeftOfDownWall, UpAndDownWallDimensions,null);
//
//        Vector2[] wallHeights = {new Vector2(windowDimensions.x()/2, 0),
//                new Vector2(windowDimensions.x(), windowDimensions.y()/2),
//                new Vector2(0,windowDimensions.y()/2),
//                new Vector2(windowDimensions.x()/2, windowDimensions.y())
//        };
//        GameObject[] walls = {wallUp, wallRight, wallLeft, wallDown};
//        for (int i = 0; i < 3; i++) {
//            walls[i].setCenter(wallHeights[i]);
//            brickerGameManager.addObjectsToTheList(walls[i], Layer.STATIC_OBJECTS); // add
//        }
//        walls[3].setCenter(wallHeights[3]);
//        brickerGameManager.addObjectsToTheList(walls[3], Layer.BACKGROUND); // add
//
//    }
//
//    public void createGameObject(ImageReader imageReader, Vector2 windowDimensions, int numOfHeartsRemain){
//        Renderable heartImage = imageReader.readImage(HEART_PICTURE_PATH, true);
//        for (int i = 0; i < numOfHeartsRemain; i++) {
//            GameObject heart = new GraphicHeart(Vector2.ZERO, new Vector2(30, 30), heartImage);
//            heart.setCenter(new Vector2(30 * (i + 1), windowDimensions.y() - 20));
//            brickerGameManager.addObjectsToTheList(heart, Layer.UI);
//        }
//    }
//
//    public void createGameObject(Vector2 windowDimensions) {
//        TextRenderable numericalHeartText = new TextRenderable(NumericalHeart.NUMERICAL_HEART_TEXT +
//                DEFAULT_NUM_OF_LIVES);
//        numericalHeartText.setColor(INITIAL_COLOR);
//        GameObject numericalHeart = new NumericalHeart(new Vector2(0, windowDimensions.y() - 80),
//                new Vector2(30, 30), numericalHeartText);
//        brickerGameManager.addObjectsToTheList(numericalHeart, Layer.UI);
//    }
//
//    public void createGameObject(UserInputListener inputListener, Vector2 windowDimensions, ImageReader imageReader){
//        Renderable paddleImage = imageReader.readImage(PADDLE_PICTURE_PATH, true);
//        GameObject paddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
//                paddleImage, inputListener, windowDimensions);
//        paddle.setCenter(new Vector2(windowDimensions.x() / 2, (int)windowDimensions.y() - 30));
//        brickerGameManager.addObjectsToTheList(paddle);
//    }
//
//    public void createGameObject(Vector2 windowDimensions, ImageReader imageReader, int numOfRows,
//                                 int numOfBricksInRow) {
//        Renderable brickImage = imageReader.readImage(BRICK_PICTURE_PATH, false);
//        CollisionStrategy collisionStrategy = new BasicCollisionStrategy(brickerGameManager);
//        float gapX = windowDimensions.x() * FACTOR_OF_X_GAP;
//        float gapY = windowDimensions.y() * FACTOR_OF_Y_GAP;
//        float brickLength = (windowDimensions.x() - (numOfBricksInRow + 1) * gapX) / (numOfBricksInRow);
//        float brickHeight = BRICK_HEIGHT;
//        for (int i = 0; i < numOfRows; i++) {
//            for (int j = 0; j < numOfBricksInRow; j++) {
//                float x = (j * (brickLength + gapX) ) + gapX;
//                float y = i * (gapY+brickHeight);
//                Brick brick = new Brick(
//                        new Vector2(x, y),
//                        new Vector2(brickLength, brickHeight),
//                        brickImage,
//                        collisionStrategy);
//                brickerGameManager.addObjectsToTheList(brick, Layer.STATIC_OBJECTS);
//            }
//        }
//    }
//
//    public createGameObject(ImageReader imageReader, Vector2 windowDimensions){
//        Renderable wallPaperImage = imageReader.readImage(WALLPAPER_PICTURE_PATH, false);
//        GameObject wallPaper = new GameObject(Vector2.ZERO, windowDimensions, wallPaperImage);
//        wallPaper.setCenter(windowDimensions.mult(FACTOR_OF_HALF));
//        brickerGameManager.addObjectsToTheList(wallPaper, Layer.BACKGROUND);
//    }
//
//}
