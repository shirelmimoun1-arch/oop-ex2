package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import java.awt.*;

/**
 * This class is responsible for the NumericalHeart game object.
 */
public class NumericalHeart extends GameObject {
    public static final String NUMERICAL_HEART_STRING = "Numerical Heart";
    public static final String NUMERICAL_HEART_TEXT_FORMAT = "Lives: ";
    public static final Color INITIAL_COLOR = Color.GREEN;
    public static final int GAP_FROM_BUTTOM_WINDOW = 80;
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
    public NumericalHeart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        this.renderable = (TextRenderable) renderable;

    }



    /**
     * Updates the numerical hearts color and text renderer when the player looses a life in the game.
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
            case 1:
                renderable.setColor(Color.red);
                break;
            case 2:
                renderable.setColor(Color.yellow);
                break;
            case 3:
                renderable.setColor(Color.green);
                break;
        }
    }
}