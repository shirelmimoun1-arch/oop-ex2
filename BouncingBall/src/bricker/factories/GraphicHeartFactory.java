package bricker.factories;

import bricker.gameobjects.GraphicHeart;
import bricker.main.BrickerGameManager;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class GraphicHeartFactory {
    private static final String HEART_PICTURE_PATH = "assets/heart.png";
    private final BrickerGameManager brickerGameManager;

    public GraphicHeartFactory(GameManager brickerGameManager) {
        this.brickerGameManager = (BrickerGameManager)brickerGameManager;
    }

    public void createGraphicHearts(ImageReader imageReader, Vector2 windowDimensions, int numOfHeartsRemain){
        Renderable heartImage = imageReader.readImage(HEART_PICTURE_PATH, true);
        for (int i = 0; i < numOfHeartsRemain; i++) {
            GameObject heart = new GraphicHeart(Vector2.ZERO, new Vector2(30, 30), heartImage);
            heart.setCenter(new Vector2(30 * (i + 1), windowDimensions.y() - 20));
            brickerGameManager.addObjectsToTheList(heart, Layer.UI);
        }
    }
}
