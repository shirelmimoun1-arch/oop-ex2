package bricker.brick_strategies;

import bricker.factories.StrategyFactory;
import bricker.main.BrickerGameManager;
import danogl.GameManager;
import danogl.GameObject;

import java.util.Random;

/**
 * Strategy of double behavior of the brick.
 */
public class DubbleBehaviorCollisionStrategy implements CollisionStrategy {
    public static final int NUM_OF_STRATEGIES_TO_ACTIVE = 2;
    private BrickerGameManager brickerGameManager;
    private StrategyFactory randStrategy;
    private boolean activeDubbleBehaviorFlag = false;
    private DubbleBehaviorCollisionStrategy dubbleBehaviorCollisionStrategy;
    private ExtraBallCollisionStrategy extraBallCollisionStrategy;
    private ExtraLifeCollisionStrategy extraLifeCollisionStrategy;
    private ExtraPaddleCollisionStrategy extraPaddleCollisionStrategy;
    private TurboCollisionStrategy turboCollisionStrategy;

    private Random random;

    /**
     * Constructor of DubbleBehaviorCollisionStrategy.
     * @param brickerGameManager A BrickerGameManager instance.
     */
    public DubbleBehaviorCollisionStrategy(GameManager brickerGameManager){
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
        int randomInt1;
        CollisionStrategy chosenStrategy1 = null;
        if (!activeDubbleBehaviorFlag) {
            randomInt1 = random.nextInt(5);
            chosenStrategy1 = randStrategy.createStrategy(randomInt1);
        }
        else {
            randomInt1 = random.nextInt(4);
            chosenStrategy1 = randStrategy.createStrategy(randomInt1);
        }
        CollisionStrategy chosenStrategy2 = null;
        int randomInt2;
        // if chosenStrategy1 is dubble behavior
        if (randomInt1 == 4){
            activeDubbleBehaviorFlag = true;
            randomInt2 = random.nextInt(4);
            chosenStrategy2 = randStrategy.createStrategy(randomInt2);
        }
        else if (!activeDubbleBehaviorFlag){
            randomInt2 = random.nextInt(5);
            if(randomInt2 == 4){
                activeDubbleBehaviorFlag = true;
            }
            chosenStrategy2 = randStrategy.createStrategy(randomInt2);
        }
        chosenStrategy1.onCollision(object1, object2);
        chosenStrategy2.onCollision(object1, object2);
    }

}
