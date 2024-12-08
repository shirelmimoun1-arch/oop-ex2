package bricker.brick_strategies;

import bricker.gameobjects.Paddle;
import danogl.GameObject;
import danogl.util.Vector2;

/**
 * A collision strategy that introduces an additional paddle into the game
 * when the associated brick is hit by the ball. This strategy checks if an
 * extra paddle already exists and only creates one if it does not.
 */
public class ExtraPaddleCollisionStrategy implements CollisionStrategy {
    private BasicCollisionStrategy basicCollisionStrategy;

    /**
     * Constructor of the extra paddle collision strategy.
     * @param basicCollisionStrategy The base collision strategy to extend with this behavior.
     */
    public ExtraPaddleCollisionStrategy(BasicCollisionStrategy basicCollisionStrategy) {
        this.basicCollisionStrategy = basicCollisionStrategy;
    }

    /**
     * Handles the collision event between a brick and the ball.
     * If an extra paddle does not already exist, a new paddle is created.
     * Additionally, delegates any further collision behavior to the base collision strategy.
     *
     * @param object1 The brick GameObject involved in the collision.
     * @param object2 The ball GameObject involved in the collision.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        if (!basicCollisionStrategy.brickerGameManager.doesExtraPaddleExist()) {
            createExtraPaddle();
        }
        basicCollisionStrategy.onCollision(object1, object2);
    }

    private void createExtraPaddle() {
        Vector2 extraPaddlePosition = new Vector2(
                basicCollisionStrategy.brickerGameManager.windowDimensions.x() / 2,
                basicCollisionStrategy.brickerGameManager.windowDimensions.y() / 2);
        basicCollisionStrategy.brickerGameManager.createPaddle(Paddle.PADDLE_PICTURE_PATH,
                extraPaddlePosition,
                Paddle.PADDLE_HEIGHT,
                Paddle.PADDLE_WIDTH,
                Paddle.EXTRA_PADDLE_NAME);
    }


}