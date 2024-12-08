package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;

/**
 * A collision strategy that activates turbo mode for the ball when the associated brick is hit.
 * In turbo mode, the ball moves faster and changes its appearance for a limited number of collisions.
 */
public class TurboCollisionStrategy implements CollisionStrategy {
    /**
     * The maximum number of ball collisions allowed while in turbo mode.
     */
    public static final int MAX_NUM_OF_BALL_COLLISIONS_IN_TURBO_MODE = 6;

    /**
     * The factor by which the ball's velocity is increased in turbo mode.
     */
    public static final float TURBO_MODE_VELOCITY_MULTIPLICATION_FACTOR = 1.4f;

    /**
     * Path to the image used for the ball in turbo mode.
     */
    public static final String RED_BALL_PATH = "assets/redball.png";

    private BasicCollisionStrategy basicCollisionStrategy;

    /**
     * Constructor for TurboCollisionStrategy.
     * @param basicCollisionStrategy The base collision strategy to extend with this behavior.
     */
    public TurboCollisionStrategy(BasicCollisionStrategy basicCollisionStrategy) {
        this.basicCollisionStrategy = basicCollisionStrategy;
    }

    /**
     * Handles the collision event between a brick and the ball. If the ball is not already in turbo mode,
     * turbo mode is activated, increasing the ball's speed and changing its appearance.
     * The collision is then further handled by the base collision strategy.
     *
     * @param object1 The brick GameObject involved in the collision.
     * @param object2 The ball GameObject involved in the collision.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        if (object2.getTag().equals(Ball.BALL_NAME)){
            Ball ball = (Ball)object2;
            if(!ball.getTurboMode()){
                Renderable redBallPath = basicCollisionStrategy.brickerGameManager.imageReader.
                        readImage(RED_BALL_PATH, true);
                ball.activeTurbo(TurboCollisionStrategy.TURBO_MODE_VELOCITY_MULTIPLICATION_FACTOR,
                        redBallPath);
            }
        }
        basicCollisionStrategy.onCollision(object1, object2);
    }
}

