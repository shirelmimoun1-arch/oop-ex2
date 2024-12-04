package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * This class is responsible for the GraphicHeart game object.
 */
public class GraphicHeart extends GameObject {
    public static final String GRAPHIC_HEART_STRING = "Graphic Heart";
    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public GraphicHeart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
    }

    /**
     * A getter for the tag name of the GraphicHeart.
     * @return A string that represents the name tag of the GraphicHeart.
     */
    @Override
    public String getTag() {
        return super.getTag() + GRAPHIC_HEART_STRING;
    }

}


