package bricker.brick_strategies;

import bricker.factories.BallFactory;
import bricker.gameobjects.Ball;
import bricker.main.BrickerGameManager;
import danogl.GameManager;
import danogl.GameObject;
import danogl.util.Vector2;

import java.util.Random;

/**
 * A collision strategy that adds a two puck balls to the game when a brick with this strategy
 * is hit by a ball.
 */
public class ExtraBallCollisionStrategy implements CollisionStrategy {
    public static final String PUCK_BALL_PATH = "assets/mockBall.png";
    public static final String PUCK_BALL_NAME = "Puck Ball";
    public static final int NUM_OF_PUCK_BALL_ADDED = 2;
    private BrickerGameManager brickerGameManager;

    /**
     * Construct a ExtraBallCollisionStrategy instance.
     * @param brickerGameManager The game manager instance used to manage objects in the game.
     */
    public ExtraBallCollisionStrategy(GameManager brickerGameManager) {
        this.brickerGameManager = (BrickerGameManager)brickerGameManager;
    }

    /**
     * Handles the collision between a brick using this strategy and another game object (typically a ball).
     * @param object1 The first object involved in the collision (usually the brick).
     * @param object2 The second object involved in the collision (usually the ball).
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        BallFactory ballFactory = new BallFactory(brickerGameManager);
        Vector2 puckBallPosition = object1.getCenter();
        for (int i = 0; i < NUM_OF_PUCK_BALL_ADDED; i++) {
            ballFactory.createBall(puckBallPosition,
                    Ball.BALL_RADIUS * (0.75f), createPuckBallVelocity(),
                    PUCK_BALL_PATH, Ball.CLASH_SOUND_PATH, PUCK_BALL_NAME);
        }
        brickerGameManager.removeBrick(object1);
    }

    private Vector2 createPuckBallVelocity(){
        Random rand = new Random();
        double angle = rand.nextDouble() * Math.PI;
        float velocityX = (float) Math.cos(angle) * Ball.BALL_SPEED;
        float velocityY = (float) Math.sin(angle) * BrickerGameManager.BALL_SPEED;
        return new Vector2(velocityX, velocityY);
    }
}
