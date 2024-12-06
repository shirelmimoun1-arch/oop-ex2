package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * This class is responsible for the GraphicHeart game object.
 */
public class GraphicHeart extends GameObject {
    public static final String GRAPHIC_HEART_STRING = "Graphic Heart";
    public static final String FALLING_HEART_STRING = "fallingHeart";
    public static final int HEART_SIZE = 30;
    public static final int GRAPHIC_HEART_GAP_FROM_BUTTOM_WINDOW = 20;
    private BrickerGameManager brickerGameManager;


    /**
     * Constructor for the GraphicHeart class.
     * @param topLeftCorner The top left corner of the GraphicHeart.
     * @param dimensions The dimensions of the GraphicHeart.
     * @param renderable The renderable object for the GraphicHeart.
     * @param brickerGameManager The BrickerGameManager instance.
     */
    public GraphicHeart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Handles the collision event between the GraphicHeart and  the Paddle.
     * @param other The other GameObject involved in the collision.
     * @param collision The Collision object representing the collision details.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        brickerGameManager.increaseLives();
        brickerGameManager.removeGameObject(this);
    }

    /**
     * Determines that the GraphicHeart should collide only with the main Paddle.
     * @param other The other GameObject.
     * @return true if the other GameObject is the main Paddle, false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other.getTag().equals(Paddle.PADDLE_NAME);
    }

    /**
     * Updates the behavior of the GraphicHeart during each frame.
     * @param deltaTime The time elapsed since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (getTopLeftCorner().y() > brickerGameManager.windowDimensions.y()) {
            brickerGameManager.removeGameObject(this);
        }
    }
}


