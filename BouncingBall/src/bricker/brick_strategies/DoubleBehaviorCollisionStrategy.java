package bricker.brick_strategies;

import bricker.factories.StrategyFactory;
import bricker.main.BrickerGameManager;
import danogl.GameManager;
import danogl.GameObject;

import java.util.Random;

/**
 * Strategy of double behavior of the brick.
 */
public class DoubleBehaviorCollisionStrategy implements CollisionStrategy {
    public static final int NUM_OF_STRATEGIES_TO_ACTIVE = 2;
    private BrickerGameManager brickerGameManager;
    private StrategyFactory randStrategy;
    private DoubleBehaviorCollisionStrategy dubbleBehaviorCollisionStrategy;
    private ExtraBallCollisionStrategy extraBallCollisionStrategy;
    private ExtraLifeCollisionStrategy extraLifeCollisionStrategy;
    private ExtraPaddleCollisionStrategy extraPaddleCollisionStrategy;
    private TurboCollisionStrategy turboCollisionStrategy;

    private Random random;

    /**
     * Constructor of DubbleBehaviorCollisionStrategy.
     * @param brickerGameManager A BrickerGameManager instance.
     */
    public DoubleBehaviorCollisionStrategy(GameManager brickerGameManager){
        this.brickerGameManager = (BrickerGameManager)brickerGameManager;
        this.random = new Random();
        this.randStrategy = new StrategyFactory((BrickerGameManager)brickerGameManager);
    }

    /**
     * Handles the collision between a brick using this strategy and the ball.
     * @param object1 brick GameObject.
     * @param object2 ball GameObject.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        boolean activeDubbleBehaviorFlag1 = false;
        boolean activeDubbleBehaviorFlag2 = false;
        int randomInt1 = random.nextInt(5);
        if (randomInt1 == 4) {
            activeDubbleBehaviorFlag1 = true;
        }
        CollisionStrategy chosenStrategy1 = randStrategy.createStrategy(randomInt1);
        CollisionStrategy chosenStrategy2 = null;
        int randomInt2;
        // if chosenStrategy1 is dubble behavior
        if (activeDubbleBehaviorFlag1){
            randomInt2 = random.nextInt(4);
            chosenStrategy2 = randStrategy.createStrategy(randomInt2);
        }
        else {
            randomInt2 = random.nextInt(5);
            if(randomInt2 == 4){
                activeDubbleBehaviorFlag2 = true;
            }
            chosenStrategy2 = randStrategy.createStrategy(randomInt2);
        }
        if (activeDubbleBehaviorFlag1 || activeDubbleBehaviorFlag2){
            int randomInt3 = random.nextInt(4);
            CollisionStrategy chosenStrategy3 = randStrategy.createStrategy(randomInt3);
            int randomInt4 = random.nextInt(4);
            CollisionStrategy chosenStrategy4 = randStrategy.createStrategy(randomInt4);
            chosenStrategy3.onCollision(object1, object2);
            chosenStrategy4.onCollision(object1, object2);
            if (activeDubbleBehaviorFlag1){
                chosenStrategy2.onCollision(object1, object2);
            }
            else {
                chosenStrategy1.onCollision(object1, object2);
            }
        }
        else{
            chosenStrategy1.onCollision(object1, object2);
            chosenStrategy2.onCollision(object1, object2);
        }
    }

}
