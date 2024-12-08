package bricker.brick_strategies;
import danogl.GameObject;

/**
 * Interface for collision strategies.
 */
public interface CollisionStrategy {
    void onCollision(GameObject object1, GameObject object2);
}
