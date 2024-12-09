package bricker.gameobjects;

import bricker.brick_strategies.ExtraBallCollisionStrategy;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;

/**
 * The Paddle class represents the paddle controlled by the player in the Bricker game.
 */
public class Paddle extends GameObject {
    /**
     * The tag used to identify the paddle.
     */
    public static final String PADDLE_NAME = "Paddle";

    /**
     * The tag used to identify the extra paddle.
     */
    public static final String EXTRA_PADDLE_NAME = "ExtraPaddle";

    /**
     * The file path to the image used for the paddle.
     */
    public static final String PADDLE_PICTURE_PATH = "assets/paddle.png";

    /**
     * The height (in pixels) of the paddle.
     */
    public static final int PADDLE_HEIGHT = 20;

    /**
     * The width (in pixels) of the paddle.
     */
    public static final int PADDLE_WIDTH = 150;

    /**
     * The vertical gap (in pixels) from the bottom of the window to the paddle.
     */
    public static final int PADDLE_GAP_FROM_BOTTOM_WINDOW = 30;

    /**
     * The horizontal movement speed (in pixels per second) of the paddle.
     */
    public static final float MOVEMENT_SPEED = 600;

    /**
     * The maximum number of hits the extra paddle can take before being removed.
     */
    public static final int MAX_NUM_OF_HIT = 4;

    private int extraPaddleNumOfHit;
    private BrickerGameManager brickerGameManager;


    /**
     * Constructs a new Paddle instance.
     * @param topLeftCorner The top-left corner position of the paddle, in window coordinates.
     *                     (0, 0) represents the top-left corner of the window.
     * @param dimensions The dimensions (width and height) of the paddle, in pixels.
     * @param renderable The renderable object used to render the paddle.
     * @param brickerGameManager The BrickerGameManager instance that manages the game's state.
     */
    public Paddle(Vector2 topLeftCorner,
                  Vector2 dimensions,
                  Renderable renderable,
                  BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.brickerGameManager = brickerGameManager;
        this.extraPaddleNumOfHit = 0;
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
//        if ((other.getTag().equals(Ball.BALL_NAME)||other.getTag().
//                equals(ExtraBallCollisionStrategy.PUCK_BALL_NAME))
//                && this.getTag().equals(EXTRA_PADDLE_NAME)){
        if(this.getTag().equals(EXTRA_PADDLE_NAME)){
            extraPaddleNumOfHit++;
            if (extraPaddleNumOfHit >= MAX_NUM_OF_HIT) {
                extraPaddleNumOfHit = 0;
                removeExtraPaddle();
            }
        }
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other.getTag().equals(Ball.BALL_NAME) || other.getTag().
                equals(ExtraBallCollisionStrategy.PUCK_BALL_NAME) ;
    }



    private void removeExtraPaddle() {

        brickerGameManager.removeGameObject(this);
    }

    /**
     * Updates the behavior of the paddle during each frame. Handles movement based on player input.
     * The paddle can move left or right within the bounds of the game window.
     * @param deltaTime The time elapsed since the last update.
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
