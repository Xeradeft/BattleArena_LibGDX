/*
 * MAN Studios
 * 03/01/2017
 * Final Project ICS4U -- Battle Arena -- 
-*- GameRenderer    --> RENDERING helper class for GameScreen
 */
package com.manstudios.gameworld;

// imports
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.manstudios.gameobjects.Avatar;
import com.manstudios.gameobjects.Ranged;
import com.manstudios.helpers.AssetLoader;
import java.text.DecimalFormat;
import java.util.ArrayList;
/**
 *
 * @author MAN Studios
 */
public class GameRenderer {
        // DECLARE FORMATS
    private DecimalFormat pf = new DecimalFormat("##0.00%");
    
        // ATTRIBUTES
    private GameWorld myWorld;  //GameRenderer needs to have a reference to the GameWorld that it will be drawing,
                                //whenever we want to refer to an object inside our GameWorld, we can retrieve it
    private OrthographicCamera cam;         //camera that views 3D as 2D
    
    private ShapeRenderer shapeRenderer;    //draws shapes and lines for us
    
    private SpriteBatch batcher;            //draws images for us using the indices provided (x, y, width and height, typically)
    private int gameWidth, gameHeight;
    
        // CONSTRUCTORS
    /**
     * Primary Constructor - instantiates a new GameRenderer (to render the game) as a helper to the GameScreen
     * @param world the GameWorld that will be rendered
     * @param gameWidth the width of the game when drawn
     * @param gameHeight the height of the game when drawn
     */
    public GameRenderer(GameWorld world, int gameWidth, int gameHeight) {
        myWorld = world;                //links the GameWorld
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        
        cam = new OrthographicCamera();                 //init cam
        cam.setToOrtho(true, gameWidth/2, gameHeight/2);//true = we want an orthographic projection || width || height
                            //(true, 600, 400);         //since GameScreen res = 272,408 ; game scaled x2 when drawn
        batcher = new SpriteBatch();                    //init batcher
        batcher.setProjectionMatrix(cam.combined);      //attach it to cam
        
        shapeRenderer = new ShapeRenderer();            //init shapeRenderer
        shapeRenderer.setProjectionMatrix(cam.combined);//attach it to cam
    }
        // BEHAVIOURS
    /**
     * The looping method that renders the game
     * @param runTime uses this value (and the previously determined frame duration) to determine which TextureRegion to display
     */
    public void render(float runTime) {
        // We will move these outside of the loop for performance later************************
        Avatar p1 = myWorld.getP1();
        Avatar p2 = myWorld.getP2();
         
        // Fill the entire screen with black, to prevent potential flickering
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
            // Begin ShapeRenderer
        shapeRenderer.begin(ShapeType.Filled);
        
        // Draw Background color
        shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 600, 400);
        
        shapeRenderer.end();    // End ShapeRenderer
        
            // Begin SpriteBatch
        batcher.begin();
        
        // draw title
        AssetLoader.shadow.draw(batcher, "Battle Arena", 250, 5);
        AssetLoader.font.draw(batcher, "Battle Arena", 250, 5);
        
        // draw each player's current health
        AssetLoader.shadow.draw(batcher, "P1 HEALTH: " + p1.getHealth(), 20, 350);
        AssetLoader.font.draw(batcher, "P1 HEALTH: " + p1.getHealth(), 20, 350);
        AssetLoader.shadow.draw(batcher, "P2 HEALTH: " + p2.getHealth(), 410, 350);
        AssetLoader.font.draw(batcher, "P2 HEALTH: " + p2.getHealth(), 410, 350);
        
        // draw each player's current accuracy
        AssetLoader.shadow.draw(batcher, "P1 Accuracy: " + pf.format(p1.getAccuracy()), 20, 300);
        AssetLoader.font.draw(batcher, "P1 Accuracy: " + pf.format(p1.getAccuracy()), 20, 300);
        AssetLoader.shadow.draw(batcher, "P2 Accuracy: " + pf.format(p2.getAccuracy()), 410, 300);
        AssetLoader.font.draw(batcher, "P2 Accuracy: " + pf.format(p2.getAccuracy()), 410, 300);
        
        batcher.end();  // End SpriteBatch
        
        // set each player's colour
        Color p1Colour = p1.getColour();
        Color p2Colour = p2.getColour();
        
            // Begin ShapeRenderer
        shapeRenderer.begin(ShapeType.Filled);
        
        // draw player #1
        shapeRenderer.setColor(p1Colour);
        shapeRenderer.circle(p1.getX(), p1.getY(), p1.getRadius()*1f);
        
        // draw player #2
        shapeRenderer.setColor(p2Colour);
        shapeRenderer.circle(p2.getX(), p2.getY(), p2.getRadius()*1f);
        
            // draw projectiles for both players
        // get each Avatar's list of ranged attacks
        ArrayList<Ranged> p1RangedAttacks = p1.getRangedAttackList();
        ArrayList<Ranged> p2RangedAttacks = p2.getRangedAttackList();
        
        // loop though each player's list of projectiles and draw each one
        for (int i = 0; i < p1RangedAttacks.size(); i++) {
            Ranged p1Attack = p1RangedAttacks.get(i);
            shapeRenderer.setColor(p1Colour);
            shapeRenderer.circle(p1Attack.getX(), p1Attack.getY(), p1Attack.getRadius());
        }
        for (int i = 0; i < p2RangedAttacks.size(); i++) {
            Ranged p2Attack = p2RangedAttacks.get(i);
            shapeRenderer.setColor(p2Colour);
            shapeRenderer.circle(p2Attack.getX(), p2Attack.getY(), p2Attack.getRadius());
        }
        
        shapeRenderer.end();    // End ShapeRenderer
    }
} // end GameRenderer class
