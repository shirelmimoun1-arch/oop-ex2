//package bricker.factories;
//
//import bricker.gameobjects.Ball;
//import danogl.GameObject;
//import danogl.gui.Sound;
//import danogl.gui.rendering.Renderable;
//import danogl.util.Vector2;
//import bricker.main.BrickerGameManager;
//
///**
// * This class is responsible for creating the Ball game object.
// */
//public class BallFactory {
//    private final BrickerGameManager brickerGameManager;
//
//    /**
//     * Constructor for the ball factory.
//     * @param brickerGameManager the game manager to add the balls to.
//     */
//    public BallFactory(BrickerGameManager brickerGameManager){
//        this.brickerGameManager = brickerGameManager;
//    }
//
//    /**
//     * Creates a ball.
//     * @param ballPosition the position of the ball.
//     * @param ballRadius the radius of the ball.
//     * @param ballSpeed the speed of the ball.
//     * @param ballPicturePath the path to the ball's picture.
//     * @param clashSound the path to the ball's clash sound.
//     * @param ballName the name of the ball.
//     */
//    public void createBall(Vector2 ballPosition, float ballRadius, Vector2 ballSpeed,
//                                 String ballPicturePath, String clashSound, String ballName){
//        Renderable ballImage = brickerGameManager.imageReader.readImage(ballPicturePath, true);
//        Sound collisionSound = brickerGameManager.soundReader.readSound(clashSound);
//        Vector2 ballDimensions = new Vector2(ballRadius, ballRadius);
//        GameObject ball = new Ball(Vector2.ZERO, ballDimensions, ballImage, collisionSound);
//        ball.setCenter(ballPosition);
//        ball.setTag(ballName);
//        ball.setVelocity(ballSpeed);
//        brickerGameManager.addObjectsToTheList(ball);
//    }
//}
