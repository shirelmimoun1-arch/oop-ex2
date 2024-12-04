package bricker.brick_strategies;

import bricker.gameobjects.Ball;

import bricker.gameobjects.Brick;
import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * This class implements the basic collision strategy for bricks in the game.
 */
public class BasicCollisionStrategy implements CollisionStrategy {
    private BrickerGameManager brickerGameManager;

    /**
     * Constructs a new BasicCollisionStrategy instance.
     * @param brickerGameManager An instance of the BrickerGameManager class
     */
    public BasicCollisionStrategy(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Handles the collision event between two game objects.
     * @param object1 The first gameobject instance involved in the collision.
     * @param object2 The second gameobject instance involved in the collision.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        if (object1.getTag().equals(Brick.BRICK_STRING) && object2.getTag().equals(Ball.BALL_STRING)) {
        brickerGameManager.increasebricksHitCounter();
        brickerGameManager.removeBrick(object1);
        }
    }
}
