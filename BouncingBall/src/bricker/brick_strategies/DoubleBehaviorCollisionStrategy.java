package bricker.brick_strategies;

import bricker.factories.StrategyFactory;
import danogl.GameObject;
import bricker.main.BrickerGameManager;

import java.util.Random;

public class DoubleBehaviorCollisionStrategy implements CollisionStrategy {
    private CollisionStrategy strategy1;
    private CollisionStrategy strategy2;
    private BasicCollisionStrategy basicCollisionStrategy;
    private StrategyFactory strategyFactory;
    private boolean prevWasDouble;
    private Random rand = new Random();

    /**
     * Constructor for DoubleBehaviorCollisionStrategy.
     * This will create two strategies recursively and limit the depth.
     *
     * @param basicCollisionStrategy The basic collision strategy.
     */
    public DoubleBehaviorCollisionStrategy(BasicCollisionStrategy basicCollisionStrategy) {
        this.basicCollisionStrategy = basicCollisionStrategy;
        this.strategyFactory = new StrategyFactory(basicCollisionStrategy.brickerGameManager);
        this.strategy1 = createStrategy(basicCollisionStrategy.brickerGameManager);
        this.strategy2 = createStrategy(basicCollisionStrategy.brickerGameManager);
    }

    /**
     * Constructor for DoubleBehaviorCollisionStrategy.
     * @param strategy1 The first strategy to wrap.
     * @param strategy2 The second strategy to wrap.
     */
    public DoubleBehaviorCollisionStrategy(CollisionStrategy strategy1, CollisionStrategy strategy2,
                                           BrickerGameManager brickerGameManager) {
        this.strategyFactory = new StrategyFactory(brickerGameManager);
        this.strategy1 = strategy1;
        this.strategy2 = strategy2;
        this.prevWasDouble = false;
    }

    /**
     * Handles the collision between a brick and the ball.
     * @param object1 brick GameObject.
     * @param object2 ball GameObject.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        strategy1.onCollision(object1, object2);
        strategy2.onCollision(object1, object2);
    }

    /**
     * Creates a strategy recursively with a limit on the number of behaviors.
     *
     * @param brickerGameManager The Bricker game manager instance.
     * @return A CollisionStrategy based on the random selection and depth.
     */
    private CollisionStrategy createStrategy(BrickerGameManager brickerGameManager) {
        if (prevWasDouble) {
            return strategyFactory.createSingleStrategy(basicCollisionStrategy);
        }

        int randomValue = rand.nextInt(5);

        if (randomValue == 4) { // If double behavior is chosen
            prevWasDouble = true;
            CollisionStrategy strategy1 = createStrategy(brickerGameManager);
            CollisionStrategy strategy2 = createStrategy(brickerGameManager);
            return new DoubleBehaviorCollisionStrategy(strategy1, strategy2, brickerGameManager);
        }

        return strategyFactory.createSingleStrategy(basicCollisionStrategy);
    }
}
