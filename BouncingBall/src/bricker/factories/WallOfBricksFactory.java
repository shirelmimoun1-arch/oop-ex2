package bricker.factories;

import bricker.brick_strategies.*;
import bricker.gameobjects.Brick;
import bricker.main.BrickerGameManager;
import danogl.GameManager;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.gui.ImageReader;
import java.util.Random;

/**
 * This class is responsible for creating a wall of bricks in the game.
 */
public class WallOfBricksFactory {
    public static final float FACTOR_OF_X_GAP = 0.01f;
    public static final float FACTOR_OF_Y_GAP = 0.02f;
    public static final float BRICK_HEIGHT = 15;
    //public static final int LOTTERY_FACTOR = 10;
    private BrickerGameManager brickerGameManager;

    /**
     * Constructor for the WallOfBricksFactory class.
     * @param brickerGameManager The BrickerGameManager instance.
     */
    public WallOfBricksFactory(GameManager brickerGameManager) {
        this.brickerGameManager = (BrickerGameManager)brickerGameManager;
    }


    /**
     * Creates a wall of bricks in the game.
     * @param windowDimensions The dimensions of the game window.
     * @param imageReader The ImageReader instance used to read brick images.
     * @param numOfRows The number of rows of bricks.
     * @param numOfBricksInRow The number of bricks in each row.
     * @param nameOfBrick The name of the brick.
     * @param imagePath The path to the brick image.
     */
    public void createWallOfBricks(Vector2 windowDimensions,
                                   ImageReader imageReader,
                                   int numOfRows,
                                   int numOfBricksInRow,
                                   String nameOfBrick,String imagePath) {
        Renderable brickImage = imageReader.readImage(imagePath, false);
        float gapX = windowDimensions.x() * FACTOR_OF_X_GAP;
        float gapY = windowDimensions.y() * FACTOR_OF_Y_GAP;
        float brickLength = (windowDimensions.x() - (numOfBricksInRow + 1) * gapX) / (numOfBricksInRow);
        float brickHeight = BRICK_HEIGHT;
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfBricksInRow; j++) {
                float x = (j * (brickLength + gapX) ) + gapX;
                float y = i * (gapY+brickHeight);
                addASingleBrick(
                        new Vector2(x, y),
                        new Vector2(brickLength, brickHeight),
                        brickImage,
                        nameOfBrick);
            }
        }
    }

    private void addASingleBrick(Vector2 topLeftCorner, Vector2 dimensions, Renderable brickImage,
                                 String nameOfBrick){
        Random rand = new Random();
        CollisionStrategy collisionStrategy;

        int randomValue = rand.nextInt(10);
        if (randomValue < 5) {
            collisionStrategy = new BasicCollisionStrategy(brickerGameManager);
        }
        else if (randomValue == 5) {
            collisionStrategy = new ExtraBallCollisionStrategy(brickerGameManager);
        }
        else if(randomValue == 6){
            collisionStrategy = new ExtraPaddleCollisionStrategy(brickerGameManager);
        }
        else if (randomValue == 7){
            collisionStrategy = new TurboCollisionStrategy(brickerGameManager);
        }
        else if (randomValue == 8){
            collisionStrategy = new ExtraLifeCollisionStrategy(brickerGameManager);
        }
        else{
            collisionStrategy = new DubbleBehaviorCollisionStrategy(brickerGameManager);
        }
        Brick brick = new Brick(topLeftCorner, dimensions, brickImage, collisionStrategy);
        brick.setTag(nameOfBrick);
        brickerGameManager.addObjectsToTheList(brick, Layer.STATIC_OBJECTS);
    }
}
