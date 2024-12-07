//package bricker.factories;
//
//import bricker.main.BrickerGameManager;
//import danogl.GameManager;
//import danogl.GameObject;
//import danogl.collisions.Layer;
//import danogl.gui.ImageReader;
//import danogl.gui.rendering.Renderable;
//import danogl.util.Vector2;
//
///**
// * This class is responsible for creating the Wallpaper game object.
// */
//public class WallPaperFactory {
//    public static final String WALLPAPER_PICTURE_PATH = "assets/DARK_BG2_small.jpeg";
//    public static final float FACTOR_OF_HALF = 0.5f;
//    private BrickerGameManager brickerGameManager;
//
//    /**
//     * Constructs a WallPaperFactory instance
//     * @param brickerGameManager The BrickerGameManager instance.
//     */
//    public WallPaperFactory(GameManager brickerGameManager) {
//        this.brickerGameManager = (BrickerGameManager)brickerGameManager;
//    }
//
//    /**
//     * Creates a wall paper and adds it to the game.
//     * @param imageReader The ImageReader instance used to read brick images.
//     * @param windowDimensions The dimensions of the game window.
//     */
//    public void createWallPaper(ImageReader imageReader, Vector2 windowDimensions){
//        Renderable wallPaperImage = imageReader.readImage(WALLPAPER_PICTURE_PATH, false);
//        GameObject wallPaper = new GameObject(Vector2.ZERO, windowDimensions, wallPaperImage);
//        wallPaper.setCenter(windowDimensions.mult(FACTOR_OF_HALF));
//        brickerGameManager.addObjectsToTheList(wallPaper, Layer.BACKGROUND);
//    }
//}
