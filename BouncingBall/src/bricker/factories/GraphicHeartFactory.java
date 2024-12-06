package bricker.factories;

import bricker.gameobjects.GraphicHeart;
import bricker.main.BrickerGameManager;
import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class GraphicHeartFactory {
    private final BrickerGameManager brickerGameManager;

    /**
     * Constructs a new GraphicHeartFactory instance.
     * @param brickerGameManager The BrickerGameManager instance.
     */
    public GraphicHeartFactory(GameManager brickerGameManager) {
        this.brickerGameManager = (BrickerGameManager) brickerGameManager;
    }

    /**
     * Creates a new heart object with the specified image, position, layer, and tag.
     * @param pathImage The path to the image file for the heart.
     * @param setPlace The position of the heart object.
     * @param layer The layer on which the heart object will be rendered.
     * @param graphicHeartName The tag for the heart object.
     * @return The created heart object.
     */

    public GameObject createGraphicHearts(String pathImage,
                                          Vector2 setPlace,
                                          int layer,
                                          String graphicHeartName) {
        Renderable heartImage = brickerGameManager.imageReader.readImage(pathImage, true);
        GameObject heart = new GraphicHeart(
                Vector2.ZERO,
                new Vector2(GraphicHeart.HEART_SIZE, GraphicHeart.HEART_SIZE),
                heartImage, brickerGameManager);
        heart.setCenter(setPlace);
        heart.setTag(graphicHeartName);
        brickerGameManager.addObjectsToTheList(heart, layer);
        return heart;
    }
}
