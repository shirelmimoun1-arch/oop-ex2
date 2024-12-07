package bricker.brick_strategies;

//import bricker.factories.GraphicHeartFactory;
import bricker.gameobjects.GraphicHeart;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.util.Vector2;

/**
 * Collision strategy for bricks that creates a falling heart when collided with.
 */
public class ExtraLifeCollisionStrategy implements CollisionStrategy {
    public static final int FALLING_HEART_Y_VELOCITY = 100;
    private BrickerGameManager brickerGameManager;
//    private GraphicHeartFactory graphicHeartFactory;

    /**
     * Constructor for ExtraLifeCollisionStrategy.
     * @param brickerGameManager An instance of the BrickerGameManager class.
     */
    public ExtraLifeCollisionStrategy(BrickerGameManager brickerGameManager){
        this.brickerGameManager = brickerGameManager;
//        this.graphicHeartFactory = new GraphicHeartFactory(brickerGameManager);
    }

    /**
     * Handles the collision between a brick using this strategy and the ball.
     * @param object1 brick GameObject.
     * @param object2 ball GameObject.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        Vector2 graphicHeartPosition = object1.getCenter();
        Vector2 graphicHeartVelocity = new Vector2(0, FALLING_HEART_Y_VELOCITY);
        brickerGameManager.createGraphicHearts(
                GraphicHeart.GRAPGIC_HEART_PICTURE_PATH,
                graphicHeartPosition,
                Layer.DEFAULT,GraphicHeart.FALLING_HEART_STRING,
                graphicHeartVelocity);
//        graphicHeartFactory.createGraphicHearts(
//                BrickerGameManager.HEART_PICTURE_PATH,
//                graphicHeartPosition,
//                Layer.DEFAULT,GraphicHeart.FALLING_HEART_STRING,
//                graphicHeartVelocity);
        brickerGameManager.removeGameObject(object1);
    }
}

