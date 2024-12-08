package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import danogl.GameObject;
import danogl.util.Vector2;

import java.util.Random;

/**
 * * A collision strategy that creates two additional "puck balls" when a brick is hit by a ball.
 */
public class ExtraBallCollisionStrategy implements CollisionStrategy {
    /**
     * Path to the image used for the puck balls.
     */
    public static final String PUCK_BALL_PATH = "assets/mockBall.png";

    /**
     * The name assigned to the puck balls for identification purposes.
     */
    public static final String PUCK_BALL_NAME = "Puck Ball";

    /**
     * Defines the size of the puck ball as a fraction of the original ball's size.
     * A value of 0.75 indicates the puck ball is 75% the size of the original ball.
     */
    public static final float PUCK_BALL_SIZE_FACTOR = 0.75f;

    /**
     * Number of puck balls created upon collision.
     */
    public static final int NUM_OF_PUCK_BALL_ADDED = 2;

    private BasicCollisionStrategy basicCollisionStrategy;

    /**
     * Construct a ExtraBallCollisionStrategy instance.
     * @param basicCollisionStrategy The base collision strategy to extend with this behavior.
     */
    public ExtraBallCollisionStrategy(BasicCollisionStrategy basicCollisionStrategy) {
        this.basicCollisionStrategy = basicCollisionStrategy;
    }

    /**
     * Handles the collision event between a brick and a ball.
     * Creates additional puck balls at the brick's position with randomized velocities.
     * Delegates further collision handling to the basic collision strategy.
     * @param object1 The brick GameObject involved in the collision.
     * @param object2 The ball GameObject involved in the collision.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        Vector2 puckBallPosition = object1.getCenter();
        for (int i = 0; i < NUM_OF_PUCK_BALL_ADDED; i++) {
            basicCollisionStrategy.brickerGameManager.createBall(puckBallPosition,
                    Ball.BALL_RADIUS * (PUCK_BALL_SIZE_FACTOR), createPuckBallVelocity(),
                    PUCK_BALL_PATH, Ball.CLASH_SOUND_PATH, PUCK_BALL_NAME);
        }
        basicCollisionStrategy.onCollision(object1, object2);
    }

    private Vector2 createPuckBallVelocity(){
        Random rand = new Random();
        double angle = rand.nextDouble() * Math.PI;
        float velocityX = (float) Math.cos(angle) * Ball.BALL_SPEED;
        float velocityY = (float) Math.sin(angle) * Ball.BALL_SPEED;
        return new Vector2(velocityX, velocityY);
    }
}
