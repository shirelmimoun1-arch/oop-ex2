package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * The GraphicHeart class represents a graphic heart object in the Bricker game.
 */
public class GraphicHeart extends GameObject {
    /**
     * The name assigned to the Graphic Heart for identification purposes.
     */
    public static final String GRAPHIC_HEART_NAME = "Graphic Heart";

    /**
     * The name assigned to the Graphic Heart when its falling for identification purposes.
     */
    public static final String FALLING_HEART_NAME = "fallingHeart";

    /**
     * Path to the image used for rendering the heart.
     */
    public static final String GRAPHIC_HEART_PICTURE_PATH = "assets/heart.png";

    /**
     * The size of the heart (in pixels).
     */
    public static final int HEART_SIZE = 30;

    /**
     * The vertical gap (in pixels) from the bottom of the window to the heart.
     */
    public static final int GRAPHIC_HEART_GAP_FROM_BOTTOM_WINDOW = 20;

    private BrickerGameManager brickerGameManager;


    /**
     * Constructor for the GraphicHeart class.
     * @param topLeftCorner The position of the heart's top-left corner in window coordinates (pixels).
     *                      The top-left corner of the window is (0, 0).
     * @param dimensions The width and height of the heart (in pixels).
     * @param renderable The renderable (image) used to display the heart.
     * @param brickerGameManager The instance of the BrickerGameManager that will manage the game state,
     *                           including lives.
     */
    public GraphicHeart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                        BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Handles the collision event between the GraphicHeart and another game object,
     * specifically the paddle.
     * When the heart collides with the paddle, the player's lives are increased and the heart
     * is removed from the game.
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


