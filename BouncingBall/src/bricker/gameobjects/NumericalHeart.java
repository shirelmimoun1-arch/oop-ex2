package bricker.gameobjects;


import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class NumericalHeart extends GameObject {

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

    @Override
    public String getTag() {
        return super.getTag() + "Numerical Heart";
    }

    public void UpdateNumericalHeart(int numOfLives){
        changeNumOfLives(numOfLives);
        setNumericalHeartColor(numOfLives);
    }

    private void changeNumOfLives(int numOfLives) {
        String newName = "Lives: " + String.valueOf(numOfLives);
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

//    private void updateTextAndColor() {
//        textRenderable.setString("Lives: " + lives);
//        if (lives >= 3) {
//            textRenderable.setColor(Color.GREEN);
//        } else if (lives == 2) {
//            textRenderable.setColor(Color.YELLOW);
//        } else if (lives == 1) {
//            textRenderable.setColor(Color.RED);
//        }
//    }
//}

