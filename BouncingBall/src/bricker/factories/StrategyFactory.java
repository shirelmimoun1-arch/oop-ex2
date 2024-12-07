package bricker.factories;

import bricker.brick_strategies.*;
import bricker.main.BrickerGameManager;

import java.util.Random;

/**
 * Responsible to create a brick strategy.
 */
public class StrategyFactory {
    private BrickerGameManager brickerGameManager;
    private Random rand;

    public StrategyFactory(BrickerGameManager brickerGameManager) {
        this.rand = new Random();
        this.brickerGameManager = brickerGameManager;
    }

    public CollisionStrategy createStrategy() {
        int randomValue = rand.nextInt(10);
        return switch (randomValue) {
            case 0, 1, 2, 3, 4 -> new DubbleBehaviorCollisionStrategy(brickerGameManager);
            case 5 -> new DubbleBehaviorCollisionStrategy(brickerGameManager);
            case 6 -> new DubbleBehaviorCollisionStrategy(brickerGameManager);
            case 7 -> new DubbleBehaviorCollisionStrategy(brickerGameManager);
            case 8 -> new DubbleBehaviorCollisionStrategy(brickerGameManager);
            case 9 -> new DubbleBehaviorCollisionStrategy( brickerGameManager);
            default -> null;
        };
    }

    public CollisionStrategy createStrategy(int bound) {
        return switch (bound) {
            case 0 -> new ExtraBallCollisionStrategy(brickerGameManager);
            case 1 -> new ExtraPaddleCollisionStrategy(brickerGameManager);
            case 2 -> new ExtraLifeCollisionStrategy(brickerGameManager);
            case 3 -> new TurboCollisionStrategy(brickerGameManager);
            case 4 -> new DubbleBehaviorCollisionStrategy(brickerGameManager);
            default -> null;
        };
    }
}
