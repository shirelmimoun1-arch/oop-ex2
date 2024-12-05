package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameManager;
import danogl.GameObject;

public class DubbleBehaviorCollisionStrategy implements CollisionStrategy {
    private BrickerGameManager brickerGameManager;
    public DubbleBehaviorCollisionStrategy(GameManager brickerGameManager){
        this.brickerGameManager = (BrickerGameManager)brickerGameManager;
    }

    @Override
    public void onCollision(GameObject object1, GameObject object2) {

    }
}
