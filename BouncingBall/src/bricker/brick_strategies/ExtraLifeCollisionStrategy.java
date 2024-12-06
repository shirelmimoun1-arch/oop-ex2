package bricker.brick_strategies;

import bricker.factories.GraphicHeartFactory;
import bricker.gameobjects.GraphicHeart;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.util.Vector2;

/**
 * Collision strategy for bricks that creates a falling heart when collided with.
 */
public class ExtraLifeCollisionStrategy implements CollisionStrategy {
    public static final int FALLING_HEART_Y_VELOCITY = 100;
    private BrickerGameManager brickerGameManager;
    private GraphicHeartFactory graphicHeartFactory;

    /**
     * Constructor for ExtraLifeCollisionStrategy.
     * @param brickerGameManager An instance of the BrickerGameManager class.
     */
    public ExtraLifeCollisionStrategy(BrickerGameManager brickerGameManager){
        this.brickerGameManager = brickerGameManager;
        this.graphicHeartFactory = new GraphicHeartFactory(brickerGameManager);
    }

    /**
     * Handles the collision between a brick using this strategy and the ball.
     * @param object1 brick GameObject.
     * @param object2 ball GameObject.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        Vector2 brickPosition = object1.getCenter();
        GraphicHeart fallingHeart = createFallingHeart(brickPosition);
        Vector2 velocity = new Vector2(0, FALLING_HEART_Y_VELOCITY);
        fallingHeart.setVelocity(velocity);
        brickerGameManager.removeBrick(object1);
    }

    private GraphicHeart createFallingHeart(Vector2 brickPosition) {
        GameObject heart = graphicHeartFactory.createGraphicHearts(
                BrickerGameManager.HEART_PICTURE_PATH,
                brickPosition,
                Layer.DEFAULT,GraphicHeart.FALLING_HEART_STRING);
        return (GraphicHeart) heart;
    }
}

