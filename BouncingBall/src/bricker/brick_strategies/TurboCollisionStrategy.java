package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;

public class TurboCollisionStrategy implements CollisionStrategy {
    public static final int MAX_NUM_OF_BALL_COLLISIONS_IN_TURBO_MODE = 6;
    public static final float VELOCITY_MULTIPLICATION_FACTOR = 1.4f;

    private BasicCollisionStrategy basicCollisionStrategy;

    /**
     * Constructor for TurboCollisionStrategy.
     * @param basicCollisionStrategy The basic collision strategy.
     */
    public TurboCollisionStrategy(BasicCollisionStrategy basicCollisionStrategy) {
        this.basicCollisionStrategy = basicCollisionStrategy;
    }

    /**
     * Handles the collision between a brick and the ball.
     * @param object1 brick GameObject.
     * @param object2 ball GameObject.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        if (object2.getTag().equals(Ball.BALL_NAME)){
            Ball ball = (Ball)object2;
            if(!ball.getTurboMode()){
                Renderable redBallPath = basicCollisionStrategy.brickerGameManager.imageReader.
                        readImage(Ball.RED_BALL_PATH, true);
                ball.activeTurbo(TurboCollisionStrategy.VELOCITY_MULTIPLICATION_FACTOR, redBallPath);
            }
        }
        basicCollisionStrategy.onCollision(object1, object2);
    }
}

