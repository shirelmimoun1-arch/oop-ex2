package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.main.BrickerGameManager;
import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;

public class TurboCollisionStrategy implements CollisionStrategy {
    public static final String RED_BALL_PATH = "assets/redball.png";
    public static final int NUM_OF_BALL_COLLISION_WITH_THE_CHANGE = 6;
    public static final int VELOCITY_MULTIPLICATION_FACTOR = 2;
    private BrickerGameManager brickerGameManager;

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
            Ball ball = (Ball)object2;
            if (!ball.inTurboMode) {
                ball.setTurboMode(true);
                ball.setVelocity(ball.getVelocity().mult(VELOCITY_MULTIPLICATION_FACTOR));
                Renderable redBallPath = brickerGameManager.imageReader.readImage(RED_BALL_PATH, true);
                ball.renderer().setRenderable(redBallPath);
            }
        }
        brickerGameManager.removeBrick(object1);
    }
}
