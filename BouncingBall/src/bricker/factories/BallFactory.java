package bricker.factories;

import bricker.gameobjects.Ball;
import danogl.GameObject;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.main.BrickerGameManager;

/**
 *
 */
public class BallFactory {
    private final BrickerGameManager brickerGameManager;

    /**
     *
     */
    public BallFactory(BrickerGameManager brickerGameManager){
        this.brickerGameManager = brickerGameManager;
    }

    public void createBall(Vector2 ballPosition, float ballRadius, Vector2 ballSpeed,
                                 String ballPicturePath, String clashSound, String ballName){
        Renderable ballImage = brickerGameManager.imageReader.readImage(ballPicturePath, true);
        Sound collisionSound = brickerGameManager.soundReader.readSound(clashSound);
        Vector2 ballDimensions = new Vector2(ballRadius, ballRadius);
        GameObject ball = new Ball(Vector2.ZERO, ballDimensions, ballImage, collisionSound);
        ball.setCenter(ballPosition);
        ball.setTag(ballName);
//        ball.setVelocity(Vector2.DOWN.mult(BrickerGameManager.BALL_SPEED));
        ball.setVelocity(ballSpeed);
        brickerGameManager.addObjectsToTheList(ball);
    }
}
//
//package bricker.factories;
//
//import bricker.gameobjects.Ball;
//import danogl.GameManager;
//import danogl.GameObject;
//import danogl.gui.ImageReader;
//import danogl.gui.Sound;
//import danogl.gui.SoundReader;
//import danogl.gui.rendering.Renderable;
//import danogl.util.Vector2;
//import bricker.main.BrickerGameManager;
//
//import java.util.Random;
//
//
//public class BallFactory {
//    public static final String BALL_PICTURE_PATH = "assets/ball.png";
//    public static final String CLASH_SOUND_PATH = "assets/blop.wav";
//    public static final int BALL_RADIUS = 35;
//    public static final int BALL_SPEED = 250;
//    public static final float FACTOR_OF_HALF = 0.5f;
//    private BrickerGameManager brickerGameManager;
//
//
//    public BallFactory(GameManager brickerGameManager){
//        this.brickerGameManager = (BrickerGameManager)brickerGameManager;
//    }
//
//    public void createBall(Vector2 windowDimensions, ImageReader imageReader, SoundReader soundReader){
//        Renderable ballImage = imageReader.readImage(BALL_PICTURE_PATH, true);
//        Sound collisionSound = soundReader.readSound(CLASH_SOUND_PATH);
//        GameObject ball = new Ball(Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage, collisionSound);
//        ball.setCenter(windowDimensions.mult(FACTOR_OF_HALF));
//        ball.setVelocity(Vector2.DOWN.mult(BALL_SPEED)); // Vector2(0, 100)
//        brickerGameManager.addObjectsToTheList(ball); // gets a private field of the father class that holds all the game objects
//        ball.setVelocity(brickerGameManager.createRandomVelocity());
//    }
//}
