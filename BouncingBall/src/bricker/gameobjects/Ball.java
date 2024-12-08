package bricker.gameobjects;
import bricker.brick_strategies.TurboCollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * This class is responsible for the ball game object.
 */
public class Ball extends GameObject {
    /**
     * Path to the image used for the main ball of the game.
     */
    public static final String BALL_PICTURE_PATH = "assets/ball.png";

    /**
     * Path to the sound used when the ball collides with other objects in the game.
     */
    public static final String CLASH_SOUND_PATH = "assets/blop.wav";

    /**
     * The radius of the main ball of the game.
     */
    public static final float BALL_RADIUS = 35;

    /**
     * The speed of the main ball of the game.
     */
    public static final int BALL_SPEED = 200;

    /**
     * The name assigned to the ball for identification purposes.
     */
    public static final String BALL_NAME = "Ball";

    /**
     * Flag indicating whether the ball is currently in turbo mode.
     */
    public boolean inTurboMode;

    private Renderable renderable;
    private Sound collisionSound;
    private int collisionCounter;
    private int turboCollisions;

    /**
     * Constructor for the Ball.
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *      *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions Width and height in window coordinates.
     * @param renderable The renderable representing the object. Can be null, in which case
     *      *                      the GameObject will not be rendered.
     * @param collisionSound The sound of the collision of the ball with other game objects.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.renderable = renderable;
        this.collisionSound = collisionSound;
        this.collisionCounter = 0;
        this.inTurboMode = false;
        this.turboCollisions = 0;
    }

    /**
     * Handles the behavior of the ball when it collides with another game object.
     * This includes reflecting the ball's velocity and playing the collision sound.
     * If the ball is in turbo mode, it counts the collision and may reset the turbo mode
     * after exceeding a limit of turbo collisions.
     *
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals(GraphicHeart.GRAPHIC_HEART_NAME)) {
            return;
        }
        Vector2 newVelocity = getVelocity().flipped(collision.getNormal()); // the ball collision with paddle
        setVelocity(newVelocity);
        collisionSound.play();
        this.collisionCounter++;
        if (this.getTurboMode()){
            this.turboCollisions++;
            if (this.turboCollisions > TurboCollisionStrategy.MAX_NUM_OF_BALL_COLLISIONS_IN_TURBO_MODE) {
                resetBall();
            }
        }
    }

    /**
     * Returns whether the ball is currently in turbo mode.
     * @return true if the ball is in turbo mode, false otherwise.
     */
    public boolean getTurboMode() {
        return inTurboMode;
    }

    /**
     * Activates turbo mode for the ball, which increases its velocity.
     * The ball's appearance is also changed to indicate that it is in turbo mode.
     * @param turboBallVelocityMultiplier The multiplier of the velocity of the turbo ball.
     * @param redBallPath The turbo ball picture path.
     */
    public void activeTurbo(float turboBallVelocityMultiplier, Renderable redBallPath) {
        if (!this.inTurboMode) {
            this.inTurboMode = true;
            this.setVelocity(this.getVelocity().mult(turboBallVelocityMultiplier));
            this.renderer().setRenderable(redBallPath);
        }
    }

    private void resetBall() {
        this.inTurboMode = false;
        turboCollisions = 0;
        setVelocity(getVelocity().mult(1/TurboCollisionStrategy.TURBO_MODE_VELOCITY_MULTIPLICATION_FACTOR));
        renderer().setRenderable(renderable);
    }

    /**
     * Returns the number of collisions of the ball with other objects.
     * @return The number of collisions of the ball.
     */
    public int getCollisionCounter() {
        return this.collisionCounter;
    }

}
