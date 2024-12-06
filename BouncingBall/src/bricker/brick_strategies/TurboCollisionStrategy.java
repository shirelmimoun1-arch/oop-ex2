package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.main.BrickerGameManager;
import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;

public class TurboCollisionStrategy implements CollisionStrategy {
    public static final int MAX_NUM_OF_BALL_COLLISIONS_IN_TURBO_MODE = 6;
    public static final int VELOCITY_MULTIPLICATION_FACTOR = 2;
    private BrickerGameManager brickerGameManager;
    public int curCollisions;

    /**
     * Constructor for TurboCollisionStrategy.
     * @param brickerGameManager The BrickerGameManager.
     */
    public TurboCollisionStrategy(GameManager brickerGameManager) {
        this.brickerGameManager = (BrickerGameManager)brickerGameManager;
    }

    /**
     * Handles the collision between a brick using this strategy and the ball.
     * @param object1 brick GameObject.
     * @param object2 ball GameObject.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        if (object2.getTag().equals(Ball.BALL_NAME)){
            Ball ball = (Ball) object2;
            this.curCollisions = ball.getCollisionCounter();
            activeTurbo(object2);
        }
        brickerGameManager.removeGameObject(object1);
    }

    private void activeTurbo(GameObject object) {
        Ball ball = (Ball) object;
        if (!ball.inTurboMode) {
            ball.setTurboMode(true);
            ball.setVelocity(ball.getVelocity().mult(TurboCollisionStrategy.VELOCITY_MULTIPLICATION_FACTOR));
            Renderable redBallPath = brickerGameManager.imageReader.readImage(Ball.RED_BALL_PATH,
                    true);
            ball.renderer().setRenderable(redBallPath);
        }
    }
}

