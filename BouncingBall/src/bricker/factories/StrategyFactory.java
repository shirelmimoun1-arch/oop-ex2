package bricker.factories;

import bricker.brick_strategies.*;
import bricker.main.BrickerGameManager;

import java.util.Random;

/**
 * Responsible to create a brick strategy.
 */
public class StrategyFactory {
    public static final int NUM_OF_SPECIAL_STRATEGY = 5;
    public static final int LOTTERY_PARAMETER = 10;

    private BrickerGameManager brickerGameManager;
    private Random rand;


    public StrategyFactory(BrickerGameManager brickerGameManager) {
        this.rand = new Random();
        this.brickerGameManager = brickerGameManager;
    }


    public CollisionStrategy createStrategy() {
        int randomValue = rand.nextInt(LOTTERY_PARAMETER);
        BasicCollisionStrategy basicCollisionStrategy = new BasicCollisionStrategy(brickerGameManager);
        return switch (randomValue) {
            case 0, 1, 2, 3, 4 -> basicCollisionStrategy;
            case 5 -> new ExtraBallCollisionStrategy(basicCollisionStrategy);
            case 6 -> new ExtraPaddleCollisionStrategy(basicCollisionStrategy);
            case 7 -> new TurboCollisionStrategy(basicCollisionStrategy);
            case 8 -> new ExtraLifeCollisionStrategy(basicCollisionStrategy);
            case 9 -> new DoubleBehaviorCollisionStrategy(basicCollisionStrategy);
            default -> null;
        };
    }

    /**
     * Creates a single strategy from the list of possible strategies.
     *
     * @return A random single CollisionStrategy.
     */
    public CollisionStrategy createSingleStrategy(BasicCollisionStrategy basicCollisionStrategy) {
        int randomValue = rand.nextInt(NUM_OF_SPECIAL_STRATEGY - 1);
        return switch (randomValue) {
            case 0 -> new ExtraBallCollisionStrategy(basicCollisionStrategy);
            case 1 -> new ExtraPaddleCollisionStrategy(basicCollisionStrategy);
            case 2 -> new ExtraLifeCollisionStrategy(basicCollisionStrategy);
            case 3 -> new TurboCollisionStrategy(basicCollisionStrategy);
            default -> null;
        };
    }
}
