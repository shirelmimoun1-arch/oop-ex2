package bricker.gameobjects;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * This class is responsible for the ball game object.
 */
public class Ball extends GameObject {
    public static final String BALL_STRING = "Ball";
    private Sound collisionSound;
    private int collisionCounter; //track the number of collision of the ball

    /**
     * Construct a new Ball instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.collisionCounter = 0;
    }

    /**
     * A getter for the tag name of the ball.
     * @return A string that represents the name tag of the ball.
     */
    @Override
    public String getTag() {
        return super.getTag() + BALL_STRING;
    }

    /**
     * Defines the behavior of the ball when it collides with another game object.
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVelocity = getVelocity().flipped(collision.getNormal()); // the ball collision with paddle
        setVelocity(newVelocity);
        collisionSound.play();
        collisionCounter++;
    }

    /**
     * Returns the number of collisions of the ball with other objects.
     * @return The number of collisions of the ball.
     */
    public int getCollisionCounter() {
        return collisionCounter;
    }
}
