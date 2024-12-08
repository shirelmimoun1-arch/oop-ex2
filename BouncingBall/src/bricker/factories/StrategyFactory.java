package bricker.factories;

import bricker.brick_strategies.*;
import bricker.main.BrickerGameManager;

import java.util.Random;

/**
 * A factory responsible for creating various brick collision strategies.
 */
public class StrategyFactory {
    /**
     * The number of special strategies available for selection.
     */
    public static final int NUM_OF_SPECIAL_STRATEGY = 5;

    /**
     * The range of random values used for selecting a collision strategy.
     */
    public static final int LOTTERY_PARAMETER = 10;

    private BrickerGameManager brickerGameManager;
    private Random rand;


    /**
     * Constructor for the StrategyFactory.
     * @param brickerGameManager The game manager responsible for managing game objects
     *                           and facilitating the creation of new objects.
     */
    public StrategyFactory(BrickerGameManager brickerGameManager) {
        this.rand = new Random();
        this.brickerGameManager = brickerGameManager;
    }


    /**
     * Creates a collision strategy based on a random lottery system.
     * @return A `CollisionStrategy` instance, which may be basic, special, or composite.
     */
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
     * Creates a single behavior strategy from the available special strategies.
     * @return A random single `CollisionStrategy` instance, excluding composite strategies.
     */
    public CollisionStrategy createSingleBehavior(BasicCollisionStrategy basicCollisionStrategy) {
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
