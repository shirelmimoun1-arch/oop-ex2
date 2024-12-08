package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * This class implements the basic collision strategy for bricks in the game.
 * In this strategy the bricks simply disappears from the game.
 */
public class BasicCollisionStrategy implements CollisionStrategy {
    /**
     * The bricker game manager instance. Used by the strategies containing the basic strategy.
     */
    protected BrickerGameManager brickerGameManager;

    /**
     * Constructs a new BasicCollisionStrategy instance.
     * @param brickerGameManager An instance of the BrickerGameManager class.
     */
    public BasicCollisionStrategy(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Handles the collision between a brick and the ball.
     * @param object1 The brick GameObject.
     * @param object2 The ball GameObject.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        brickerGameManager.removeGameObject(object1);
    }
}
