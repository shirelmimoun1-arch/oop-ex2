package bricker.factories;

import bricker.gameobjects.Paddle;
import bricker.main.BrickerGameManager;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * This class is responsible for the Paddle game object.
 */
public class PaddleFactory {
    private BrickerGameManager brickerGameManager;

    /**
     * Constructs a new PaddleFactory instance.
     * @param brickerGameManager The BrickerGameManager instance.
     */
    public PaddleFactory(GameManager brickerGameManager){
        this.brickerGameManager = (BrickerGameManager)brickerGameManager;
    }

    /**
     * Creates a paddle object and adds it to the game
     * @param paddlePicturePath The path to the picture of the paddle
     * @param startingPlace The starting place of the paddle
     * @param paddleHeight Paddle height
     * @param paddleWidth  Paddle width
     * @param paddleName The name of the paddle
     */
    public void createPaddle(
                                   String paddlePicturePath,
                                   Vector2 startingPlace,int paddleHeight,int paddleWidth,String paddleName){
        Renderable paddleImage = brickerGameManager.imageReader.readImage(paddlePicturePath, true);
        GameObject paddle = new Paddle(Vector2.ZERO, new Vector2(paddleWidth, paddleHeight),
                paddleImage,brickerGameManager);
        paddle.setCenter(startingPlace);
        paddle.setTag(paddleName);
        brickerGameManager.addObjectsToTheList(paddle, Layer.DEFAULT);
    }
}
