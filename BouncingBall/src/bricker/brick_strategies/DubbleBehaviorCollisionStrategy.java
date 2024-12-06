package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameManager;
import danogl.GameObject;

/**
 * Strategy of double behavior of the brick
 */
public class DubbleBehaviorCollisionStrategy implements CollisionStrategy {
    private BrickerGameManager brickerGameManager;
    public DubbleBehaviorCollisionStrategy(GameManager brickerGameManager){
        this.brickerGameManager = (BrickerGameManager)brickerGameManager;
    }

    /**
     * Handles the collision between a brick using this strategy and the ball.
     * @param object1 brick GameObject.
     * @param object2 ball GameObject.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {

    }
}
