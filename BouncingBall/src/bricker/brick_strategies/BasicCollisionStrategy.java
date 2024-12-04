package bricker.brick_strategies;

import bricker.gameobjects.Ball;

import bricker.gameobjects.Brick;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;

public class BasicCollisionStrategy implements CollisionStrategy {
    private BrickerGameManager brickerGameManager;

    public BasicCollisionStrategy(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager;
    }

    private static final String MESSAGE = "collision with brick detected";


    @Override
    public void onCollision(GameObject object1, GameObject object2) {
//        if (object2 instanceof Ball) {
//            System.out.println(MESSAGE);
//            objectCollection.removeGameObject(object1);
//        }
        if (object1.getTag().equals("Brick") && object2.getTag().equals("Ball")) {
            brickerGameManager.increasebricksHitCounter();
            brickerGameManager.removeBrick(object1);
        }
    }
}
