package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * This class is responsible for the Paddle game object.
 */
public class Paddle extends GameObject {
    private static final String PADDLE_STRING = "Paddle";
    private static final float MOVEMENT_SPEED = 300;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;

    /**
     * Construct a new Paddle instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param inputListener
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    /**
     * A getter for the tag name of the Paddle.
     * @return A string that represents the name tag of the Paddle.
     */
    @Override
    public String getTag() {
        return super.getTag() + PADDLE_STRING;
    }

    /**
     * Updates the behavior of the paddle during each frame. This method allows
     * the user to control the paddle's movement left or right using the arrow keys.
     * It also ensures that the paddle does not move outside the boundaries of the window.
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            if (getTopLeftCorner().x() <= 0) {
                setTopLeftCorner(new Vector2(0, getTopLeftCorner().y()));
            }
            movementDir = movementDir.add(Vector2.LEFT);
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            float topRightCorner = getTopLeftCorner().x() + this.getDimensions().x();
            if (topRightCorner >= windowDimensions.x()) {
                setTopLeftCorner(new Vector2(windowDimensions.x() - this.getDimensions().x(),
                        getTopLeftCorner().y()));
            }
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
    }
}
