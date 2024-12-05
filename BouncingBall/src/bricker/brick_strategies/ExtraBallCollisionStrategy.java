package bricker.brick_strategies;


import bricker.main.BrickerGameManager;
import danogl.GameManager;
import danogl.GameObject;

public class ExtraBallCollisionStrategy implements CollisionStrategy {
    private BrickerGameManager brickerGameManager;

    public ExtraBallCollisionStrategy(GameManager brickerGameManager) {
        this.brickerGameManager = (BrickerGameManager)brickerGameManager;
    }

    @Override
    public void onCollision(GameObject object1, GameObject object2) {

    }
}
