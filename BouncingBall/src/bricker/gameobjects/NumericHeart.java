package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import java.awt.*;

/**
 * The NumericHeart class represents a visual indicator of the player's remaining lives in the Bricker game.
 */
public class NumericHeart extends GameObject {
    /**
     * The name assigned to the Numeric Heart for identification purposes.
     */
    public static final String NUMERICAL_HEART_NAME = "Numerical Heart";

    /**
     * The format used to display the text for the number of lives.
     */
    public static final String NUMERICAL_HEART_TEXT_FORMAT = "Lives: ";

    /**
     * The initial color of the text when the game starts (green for full lives).
     */
    public static final Color INITIAL_COLOR = Color.GREEN;

    /**
     * The vertical gap (in pixels) from the bottom of the window to the numerical heart.
     */
    public static final int NUMERIC_HEART_GAP_FROM_BOTTOM_WINDOW = 80;

    /**
     * Constants representing the number of lives remaining in the game.
     * These constants are used to manage and display the player's lives.
     */
    public static final int ONE_LIFE_REMAIN = 1;
    public static final int TWO_LIFE_REMAIN = 2;
    public static final int THREE_LIFE_REMAIN = 3;

    private final TextRenderable renderable;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public NumericHeart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        this.renderable = (TextRenderable) renderable;

    }

    /**
     * Updates the display of the NumericalHeart when the player loses a life.
     * @param numOfLives The number of lives remaining.
     */
    public void UpdateNumericalHeart(int numOfLives){
        changeNumOfLives(numOfLives);
        setNumericalHeartColor(numOfLives);
    }

    private void changeNumOfLives(int numOfLives) {
        String newName = NUMERICAL_HEART_TEXT_FORMAT + numOfLives;
        renderable.setString(newName);
    }

    private void setNumericalHeartColor(int numOfLives){
        switch (numOfLives){
            case ONE_LIFE_REMAIN:
                renderable.setColor(Color.red);
                break;
            case TWO_LIFE_REMAIN:
                renderable.setColor(Color.yellow);
                break;
            case THREE_LIFE_REMAIN:
                renderable.setColor(Color.green);
                break;
        }
    }
}