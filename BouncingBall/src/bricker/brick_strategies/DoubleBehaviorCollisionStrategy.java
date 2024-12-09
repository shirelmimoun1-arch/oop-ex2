package bricker.brick_strategies;

import bricker.factories.StrategyFactory;
import danogl.GameObject;

import java.util.Random;

/**
 * A collision strategy that combines two other collision strategies, including itself if necessary.
 * This allows a combination of at most three behavior when colliding with a brick.
 */
public class DoubleBehaviorCollisionStrategy implements CollisionStrategy {
    /**
     * The index of the last strategy in the factory, designated as the double behavior strategy.
     */
    public static final int LAST_STRATEGY = StrategyFactory.NUM_OF_SPECIAL_STRATEGY - 1;

    private CollisionStrategy strategy1;
    private CollisionStrategy strategy2;
    private BasicCollisionStrategy basicCollisionStrategy;
    private StrategyFactory strategyFactory;
    private boolean prevWasDouble;
    private Random rand;

    /**
     * Constructor for DoubleBehaviorCollisionStrategy.
     * @param basicCollisionStrategy The base collision strategy to extend with this behavior.
     */
    public DoubleBehaviorCollisionStrategy(BasicCollisionStrategy basicCollisionStrategy) {
        this.rand = new Random();
        this.basicCollisionStrategy = basicCollisionStrategy;
        this.strategyFactory = new StrategyFactory(basicCollisionStrategy.brickerGameManager);
        this.strategy1 = createStrategy();
        this.strategy2 = createStrategy();

    }

    /**
     * Constructor for DoubleBehaviorCollisionStrategy.
     * @param strategy1 The first collision strategy.
     * @param strategy2 The second collision strategy.
     */
    public DoubleBehaviorCollisionStrategy(CollisionStrategy strategy1, CollisionStrategy strategy2) {
        this.strategy1 = strategy1;
        this.strategy2 = strategy2;
    }

    /**
     * Handles the collision between a brick and the ball by applying both sub-strategies.
     * @param object1 The brick GameObject involved in the collision.
     * @param object2 The ball GameObject involved in the collision.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        strategy1.onCollision(object1, object2);
        strategy2.onCollision(object1, object2);
    }



    /**
     * Creates a new collision strategy. Ensures that two nested `DoubleBehaviorCollisionStrategy`
     * instances are not created consecutively. Thereby limiting the maximum depth of composite
     * behaviors to three.
     * @return A `CollisionStrategy` instance, either single or double behavior, based on the
     * random selection.
     */
    public CollisionStrategy createStrategy() {
        if (prevWasDouble) {
            int randomValue = rand.nextInt(StrategyFactory.NUM_OF_SPECIAL_STRATEGY - 1);
            return strategyFactory.createSingleBehavior(basicCollisionStrategy,
                    randomValue);
        }

        int randomValue = rand.nextInt(StrategyFactory.NUM_OF_SPECIAL_STRATEGY);

        if (randomValue == LAST_STRATEGY) { // The last strategy is always the double strategy
            prevWasDouble = true;
            CollisionStrategy strategy1 = createStrategy();
            CollisionStrategy strategy2 = createStrategy();
            return new DoubleBehaviorCollisionStrategy(strategy1, strategy2);
        }

        return strategyFactory.createSingleBehavior(basicCollisionStrategy, randomValue);
    }
}
