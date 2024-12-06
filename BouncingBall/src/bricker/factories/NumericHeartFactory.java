package bricker.factories;

import bricker.gameobjects.GraphicHeart;
import bricker.gameobjects.NumericalHeart;
import bricker.main.BrickerGameManager;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * This class is responsible for creating the Numerical Heart game object.
 */
public class NumericHeartFactory {
    public static final Color INITIAL_COLOR = Color.GREEN;

    private final BrickerGameManager brickerGameManager;

    /**
     * Constructs a new NumericHeartFactory instance.
     * @param brickerGameManager The BrickerGameManager instance.
     */
    public NumericHeartFactory(GameManager brickerGameManager) {
        this.brickerGameManager = (BrickerGameManager)brickerGameManager;
    }

    /**
     * Creates the numerical hearts in the game.
     * @param windowDimensions The dimensions of the game window.
     * @param numOfLives The number of lives remaining.
     * @param numericalHeartName The name of the numerical heart object.
     */
    public void createNumericalHearts(Vector2 windowDimensions,int numOfLives,String numericalHeartName) {
        TextRenderable numericalHeartText = new TextRenderable(NumericalHeart.NUMERICAL_HEART_TEXT +
                numOfLives);
        numericalHeartText.setColor(INITIAL_COLOR);
        GameObject numericalHeart = new NumericalHeart(
                new Vector2(0, windowDimensions.y() - NumericalHeart.GAP_FROM_BUTTOM_WINDOW),
                new Vector2(GraphicHeart.HEART_SIZE, GraphicHeart.HEART_SIZE), numericalHeartText);
        numericalHeart.setTag(numericalHeartName);
        brickerGameManager.addObjectsToTheList(numericalHeart, Layer.UI);
    }
}
