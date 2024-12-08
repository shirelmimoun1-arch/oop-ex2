package bricker.brick_strategies;

import bricker.gameobjects.Paddle;
import danogl.GameObject;
import danogl.util.Vector2;

/**
 * Strategy of creating an extra paddle.
 */
public class ExtraPaddleCollisionStrategy implements CollisionStrategy {
    private BasicCollisionStrategy basicCollisionStrategy;


    /**
     * constructor of the extra paddle collision strategy.
     * @param basicCollisionStrategy The basic collision strategy.
     */
    public ExtraPaddleCollisionStrategy(BasicCollisionStrategy basicCollisionStrategy) {
        this.basicCollisionStrategy = basicCollisionStrategy;
    }

    /**
     * Handles the collision between a brick and the ball.
     * @param object1 brick GameObject.
     * @param object2 ball GameObject.
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