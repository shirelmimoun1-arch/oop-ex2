package bricker.brick_strategies;

import bricker.factories.PaddleFactory;
import bricker.gameobjects.Paddle;
import bricker.main.BrickerGameManager;
import danogl.GameManager;
import danogl.GameObject;
import danogl.util.Vector2;

/**
 * Strategy of creating an extra paddle.
 */
public class ExtraPaddleCollisionStrategy implements CollisionStrategy {
    private  BrickerGameManager brickerGameManager;
    public static boolean extraPaddleExists;
    private  PaddleFactory paddleFactory;
    private GameObject extraPaddle;


    /**
     * constructor of the extra paddle collision strategy.
     * @param brickerGameManager An instance of the BrickerGameManager class.
     */
    public ExtraPaddleCollisionStrategy(GameManager brickerGameManager) {
        this.brickerGameManager = (BrickerGameManager) brickerGameManager;
        this.paddleFactory = new PaddleFactory(brickerGameManager);
        this.extraPaddleExists = false;
    }

    /**
     * Handles the collision between a brick using this strategy and the ball.
     * @param object1 brick GameObject.
     * @param object2 ball GameObject.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        if (!extraPaddleExists) {
            createExtraPaddle();
        }
        brickerGameManager.removeBrick(object1);
    }

    private void createExtraPaddle() {
        extraPaddleExists = true;
        Vector2 extraPaddlePosition = new Vector2(
                brickerGameManager.windowDimensions.x() / 2,
                brickerGameManager.windowDimensions.y() / 2);
        paddleFactory.createPaddle(Paddle.PADDLE_PICTURE_PATH,
                extraPaddlePosition,
                Paddle.PADDLE_HEIGHT,
                Paddle.PADDLE_WIDTH,
                Paddle.EXTRA_PADDLE_NAME);

    }

}