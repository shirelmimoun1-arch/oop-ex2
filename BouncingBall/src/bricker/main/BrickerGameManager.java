package bricker.main;
import bricker.brick_strategies.CollisionStrategy;
import bricker.gameobjects.Brick;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.gameobjects.Brick;

public class BrickerGameManager extends GameManager {
    private static final int DEFAULT_ROWS = 7;
    private static final int NUM_OF_BRICK = 8;
    private static final double WALL_FACTOR = 1.5;
//    private  float PREFIX = windowDimensions.mult(WALL_FACTOR).y();
    private  int numOfBricksInRow;
    private int numOfRow;
    private WindowController windowController;
    private float brickLength;

    public BrickerGameManager(int numOfBricksInRow, int numOfRow, WindowController windowController,
                              ImageReader imageReader){
        this.numOfBricksInRow = numOfBricksInRow;
        this.numOfRow = numOfRow;
        this.windowController = windowController;
        Vector2 windowDimensions = windowController.getWindowDimensions();
        this.brickLength = windowDimensions.x() / NUM_OF_BRICK;
//        createWallOfBricks(imageReader, windowDimensions);

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
        gameObjects().removeGameObject(brick);
    }

}
