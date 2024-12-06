package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * This class is responsible for the Brick game object.
 */
public class Brick extends GameObject {
    public static final String BRICK_NAME = "Brick";
    public static final String BASIC_BRICK_NAME = "Brick Basic";
    public static final String EXTRA_BALL_BRICK_NAME = "Brick Extra Ball";
    public static final String EXTRA_PADDLE_BRICK_NAME = "Brick Extra Paddle";
    public static final String TURBO_BRICK_NAME = "Brick Turbo";
    public static final String EXTRA_LIFE_BRICK_NAME = "Brick Extra Life";
    public static final String DUBBLE_BRICK_NAME = "Brick Dubble Behavior";
    private CollisionStrategy collisionStrategy;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
    }

    /**
     * Defines the behavior of the brick when it collides with another game object.
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
