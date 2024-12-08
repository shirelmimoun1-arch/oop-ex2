package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * The Brick class represents a brick object in the Bricker game.
 */
public class Brick extends GameObject {
    /**
     * The name assigned to the brick for identification purposes.
     */
    public static final String BRICK_NAME = "Brick";

    /**
     * The default height of the brick (in pixels).
     */
    public static final float BRICK_HEIGHT = 15;

    /**
     * Path to the image used for rendering the brick.
     */
    public static final String BRICK_PICTURE_PATH = "assets/brick.png";

    /**
     * A message used to notify when a collision with a brick is detected.
     */
    public static final String BRICK_COLLISION_MESSAGE = "Collision with brick detected";

    private CollisionStrategy collisionStrategy;

    /**
     * Construct a new GameObject instance.
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                             Note that (0,0) is the top-left corner of the window.
     * @param dimensions Width and height in window coordinates.
     * @param renderable The renderable representing the object. Can be null, in which case
     *                             the GameObject will not be rendered.
     * @param collisionStrategy The brick strategy.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
    }

    /**
     * Handles the behavior of the brick when it collides with another game object.
     * The method delegates the collision handling to the assigned collisionStrategy.
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionStrategy.onCollision(this, other);
    }
}
