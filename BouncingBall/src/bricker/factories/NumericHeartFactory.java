package bricker.factories;

import bricker.gameobjects.NumericalHeart;
import bricker.main.BrickerGameManager;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class NumericHeartFactory {
    public static final int DEFAULT_NUM_OF_LIVES = 3;
    public static final Color INITIAL_COLOR = Color.GREEN;
    private final BrickerGameManager brickerGameManager;

    public NumericHeartFactory(GameManager brickerGameManager) {
        this.brickerGameManager = (BrickerGameManager)brickerGameManager;
    }

    public void createNumericalHearts(Vector2 windowDimensions) {
        TextRenderable numericalHeartText = new TextRenderable(NumericalHeart.NUMERICAL_HEART_TEXT +
                DEFAULT_NUM_OF_LIVES);
        numericalHeartText.setColor(INITIAL_COLOR);
        GameObject numericalHeart = new NumericalHeart(new Vector2(0, windowDimensions.y() - 80),
                new Vector2(30, 30), numericalHeartText);
        brickerGameManager.addObjectsToTheList(numericalHeart, Layer.UI);
    }
}
