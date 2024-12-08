package bricker.brick_strategies;

import bricker.gameobjects.GraphicHeart;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.util.Vector2;

/**
 * A collision strategy that generates a falling heart when a brick is hit by a ball.
 * This strategy adds a visual and interactive heart object to the game, representing an extra life.
 */
public class ExtraLifeCollisionStrategy implements CollisionStrategy {
    /**
     * The vertical velocity of the falling heart, measured in units per second.
     * Determines the speed at which the heart descends on the screen.
     */
    public static final int FALLING_HEART_Y_VELOCITY = 100;

    private BasicCollisionStrategy basicCollisionStrategy;

    /**
     * Constructor for ExtraLifeCollisionStrategy.
     * @param basicCollisionStrategy The base collision strategy to extend with this behavior.
     */
    public ExtraLifeCollisionStrategy(BasicCollisionStrategy basicCollisionStrategy){
        this.basicCollisionStrategy = basicCollisionStrategy;
    }

    /**
     * Handles the collision between a brick and a ball.
     * When triggered, it spawns a falling heart at the position of the brick and delegates
     * any additional collision handling to the base collision strategy.
     *
     * @param object1 The brick GameObject involved in the collision.
     * @param object2 The ball GameObject involved in the collision.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        Vector2 graphicHeartPosition = object1.getCenter();
        Vector2 graphicHeartVelocity = new Vector2(0, FALLING_HEART_Y_VELOCITY);
        basicCollisionStrategy.brickerGameManager.createGraphicHearts(
                GraphicHeart.GRAPHIC_HEART_PICTURE_PATH,
                graphicHeartPosition,
                Layer.DEFAULT,GraphicHeart.FALLING_HEART_NAME,
                graphicHeartVelocity);
        basicCollisionStrategy.onCollision(object1, object2);
    }
}

