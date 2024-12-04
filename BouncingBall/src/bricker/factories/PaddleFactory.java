package bricker.factories;

import bricker.gameobjects.Paddle;
import bricker.main.BrickerGameManager;
import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.gui.ImageReader;


public class PaddleFactory {
    public static final int PADDLE_HEIGHT = 20;
    public static final int PADDLE_WIDTH = 100;
    public static final String PADDLE_PICTURE_PATH = "assets/paddle.png";
    private BrickerGameManager brickerGameManager;

    public PaddleFactory(GameManager brickerGameManager){
        this.brickerGameManager = (BrickerGameManager)brickerGameManager;
    }

    public void createPaddle(UserInputListener inputListener, Vector2 windowDimensions, ImageReader imageReader){
        Renderable paddleImage = imageReader.readImage(PADDLE_PICTURE_PATH, true);
        GameObject paddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage, inputListener, windowDimensions);
        paddle.setCenter(new Vector2(windowDimensions.x() / 2, (int)windowDimensions.y() - 30));
        brickerGameManager.addObjectsToTheList(paddle);
    }
}
