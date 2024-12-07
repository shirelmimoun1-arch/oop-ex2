//package bricker.factories;
//
//import bricker.main.BrickerGameManager;
//import danogl.GameManager;
//import danogl.GameObject;
//import danogl.collisions.Layer;
//import danogl.util.Vector2;
//
///**
// * This class is responsible for creating the Borders of the window.
// */
//public class BordersFactory {
//    public static final float WALL_FACTOR = 0.001f;
//    public static final String BORDER_NAME = "Border";
//    private final BrickerGameManager brickerGameManager;
//
//    /**
//     * Constructor for BordersFactory.
//     * @param brickerGameManager The BrickerGameManager instance.
//     */
//    public BordersFactory(GameManager brickerGameManager) {
//        this.brickerGameManager = (BrickerGameManager)brickerGameManager;
//    }
//
//    /**
//     * Creates borders for the game.
//     * @param windowDimensions The dimensions of the game window.
//     */
//
//    /**
//     * Creates borders for the game window.
//     * @param windowDimensions The dimensions of the game window.
//     * @param borderName The name of the border game object.
//     */
//    public void createBorder(Vector2 windowDimensions, String borderName) {
//        Vector2 topLeftOfUpperAndLeftWall = Vector2.ZERO;
//        Vector2 topLeftOfDownWall = new Vector2(0, windowDimensions.y());
//        Vector2 topLeftOfRightWall = new Vector2(windowDimensions.x(), 0);
//
//        Vector2 UpAndDownWallDimensions = new Vector2(windowDimensions.x(),windowDimensions.mult(WALL_FACTOR).y());
//        Vector2 RightAndLefWallDimensions = new Vector2(windowDimensions.mult(WALL_FACTOR).x(),windowDimensions.y());
//
//        GameObject wallUp = new GameObject(topLeftOfUpperAndLeftWall, UpAndDownWallDimensions, null);
//        GameObject wallRight = new GameObject(topLeftOfRightWall, RightAndLefWallDimensions,null);
//        GameObject wallLeft = new GameObject(topLeftOfUpperAndLeftWall, RightAndLefWallDimensions,null);
//        GameObject wallDown = new GameObject(topLeftOfDownWall, UpAndDownWallDimensions,null);
//
//        Vector2[] wallHeights = {new Vector2(windowDimensions.x()/2, 0),
//                new Vector2(windowDimensions.x(), windowDimensions.y()/2),
//                new Vector2(0,windowDimensions.y()/2),
//                new Vector2(windowDimensions.x()/2, windowDimensions.y())
//        };
//
//        GameObject[] walls = {wallUp, wallRight, wallLeft, wallDown};
//        for (int i = 0; i < 3; i++) {
//            walls[i].setCenter(wallHeights[i]);
//            walls[i].setTag(borderName);
//            brickerGameManager.addObjectsToTheList(walls[i], Layer.STATIC_OBJECTS); // add
//        }
//
//        walls[3].setCenter(wallHeights[3]);
//        walls[3].setTag(borderName);
//        brickerGameManager.addObjectsToTheList(walls[3], Layer.BACKGROUND); // add
//    }
//}
