package bricker.factories;

import bricker.brick_strategies.*;
import bricker.gameobjects.Brick;
import bricker.main.BrickerGameManager;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.gui.ImageReader;

import java.util.Random;


public class WallOfBricksFactory {
    public static final String BRICK_PICTURE_PATH = "assets/brick.png";
    public static final float FACTOR_OF_X_GAP = 0.01f;
    public static final float FACTOR_OF_Y_GAP = 0.02f;
    public static final float BRICK_HEIGHT = 15;
    private BrickerGameManager brickerGameManager;

    public WallOfBricksFactory(GameManager brickerGameManager) {
        this.brickerGameManager = (BrickerGameManager)brickerGameManager;
    }

    public void createWallOfBricks(Vector2 windowDimensions, ImageReader imageReader, int numOfRows,
                                   int numOfBricksInRow) {
        Renderable brickImage = imageReader.readImage(BRICK_PICTURE_PATH, false);
        float gapX = windowDimensions.x() * FACTOR_OF_X_GAP;
        float gapY = windowDimensions.y() * FACTOR_OF_Y_GAP;
        float brickLength = (windowDimensions.x() - (numOfBricksInRow + 1) * gapX) / (numOfBricksInRow);
        float brickHeight = BRICK_HEIGHT;
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfBricksInRow; j++) {
                float x = (j * (brickLength + gapX) ) + gapX;
                float y = i * (gapY+brickHeight);
                addASinglrBrick(new Vector2(x, y), new Vector2(brickLength, brickHeight), brickImage);
            }
        }
    }

    private void addASinglrBrick(Vector2 topLeftCorner, Vector2 dimensions, Renderable brickImage){
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
        brickerGameManager.addObjectsToTheList(brick, Layer.STATIC_OBJECTS);
    }
}
