package bricker.gameobjects;

import bricker.brick_strategies.ExtraPaddleCollisionStrategy;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;

/**
 * This class is responsible for the Paddle game object.
 */
public class Paddle extends GameObject {
    public static final String PADDLE_NAME = "Paddle";
    public static final String EXTRA_PADDLE_NAME = "ExtraPaddle";
    public static final String PADDLE_PICTURE_PATH = "assets/paddle.png";
    public static final int PADDLE_HEIGHT = 20;
    public static final int PADDLE_WIDTH = 150;
    public static final int PADDLE_GAP_FROM_BUTTOM_WINDOW = 30;
    public static final float MOVEMENT_SPEED = 600;
    public static final int MAX_NUM_OF_HIT = 4;
    private int extraPaddleNumOfHit;
    private BrickerGameManager brickerGameManager;


    /**
     * Constructs a new Paddle instance.
     * @param topLeftCorner The top-left corner position of the paddle.
     * @param dimensions The dimensions (width and height) of the paddle.
     * @param renderable The renderable object used to render the paddle.
     * @param brickerGameManager The BrickerGameManager instance that manages the game.
     */
    public Paddle(Vector2 topLeftCorner,
                  Vector2 dimensions,
                  Renderable renderable,
                  BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.brickerGameManager = brickerGameManager;
        this.extraPaddleNumOfHit =0;
    }

    /**
     * Handles the collision between the paddle and another game object.
     * If the other object is a ball or a puck ball, and the paddle has an extra paddle,
     * the extra paddle is removed after 4 hit.
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if ((other.getTag().equals(Ball.BALL_NAME)||other.getTag().equals(Ball.PUCK_BALL_NAME))
                && getTag().equals(EXTRA_PADDLE_NAME)){
            extraPaddleNumOfHit++;
            if (extraPaddleNumOfHit == MAX_NUM_OF_HIT) {
                extraPaddleNumOfHit = 0;
                removeExtraPaddle();
            }
        }
    }

    private void removeExtraPaddle() {
            brickerGameManager.removeGameObject(this);
            ExtraPaddleCollisionStrategy.extraPaddleExists = false;
    }

    /**
     * Updates the behavior of the paddle during each frame.
     * @param deltaTime The time elapsed.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if(brickerGameManager.inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            if (getTopLeftCorner().x() <= 0) {
                setTopLeftCorner(new Vector2(0, getTopLeftCorner().y()));
            }
            movementDir = movementDir.add(Vector2.LEFT);
        }
        if(brickerGameManager.inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            float topRightCorner = getTopLeftCorner().x() + this.getDimensions().x();
            if (topRightCorner >= brickerGameManager.windowDimensions.x()) {
                setTopLeftCorner(new Vector2(brickerGameManager.windowDimensions.x() - this.getDimensions().x(),
                        getTopLeftCorner().y()));
            }
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
    }
}
