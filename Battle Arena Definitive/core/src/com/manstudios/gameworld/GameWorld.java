/*
 * MAN Studios
 * 03/01/2017
 * Final Project ICS4U -- Battle Arena -- 
-*- GameWorld      --> UPDATING helper class for GameScreen
 */
package com.manstudios.gameworld;

// imports
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Intersector;
import com.manstudios.gameobjects.Avatar;
import com.manstudios.gameobjects.Ranged;
import com.manstudios.helpers.AssetLoader;
import java.util.ArrayList;
/**
 *
 * @author MAN Studios
 */
public class GameWorld {
        // ATTRIBUTES
    private Avatar p1, p2;  //player #1, player #2
    
        // CONSTRUCTORS
    /**
     * Primary Constructor - instantiates a new GameWorld (to update the game)
     * @param gameWidth the width of the game when drawn
     * @param gameHeight the height of the game when drawn
     */
    public GameWorld(int gameWidth, int gameHeight) {
        p1 = new Avatar(50, gameHeight/4, 5, Color.BLUE);
        p2 = new Avatar(540, gameHeight/4, 5, Color.RED);
    }
        // BEHAVIOURS
    /**
     * The looping method that updates all the game objects as a helper to the GameWorld
     * @param delta the number of seconds (usually a small fraction) that has passed since the last time that the render method(from GameWorld) was called
     */
    public void update(float delta) {
        //Gdx.app.log("GameWorld", "update");
        p1.update(delta);
        p2.update(delta);
        
        // update Ranged objects and check screen collision
        ArrayList<Ranged> p1RangedAttacks = p1.getRangedAttackList();
        ArrayList<Ranged> p2RangedAttacks = p2.getRangedAttackList();
        
        // loop though each player's list of ranged attacks and update each one
        for (int i = 0; i < p1RangedAttacks.size(); i++) {
            p1RangedAttacks.get(i).update(delta);
        }
        for (int i = 0; i < p2RangedAttacks.size(); i++) {
            p2RangedAttacks.get(i).update(delta);
        }
        
        // First should check if any projectiles collide w/ opponent for both player's
        for (int i = 0; i < p1RangedAttacks.size(); i++) {
            if(p1RangedAttacks.get(i).hits(p2)) {
                p2.subtractHealth(1);
                p1.incrementHitCount();
                p1RangedAttacks.remove(i);
                AssetLoader.hitmarker.play();
            }
        }
        for (int i = 0; i < p2RangedAttacks.size(); i++) {
            if(p2RangedAttacks.get(i).hits(p1)) {
                p1.subtractHealth(1);
                p2.incrementHitCount();
                p2RangedAttacks.remove(i);
                AssetLoader.hitmarker.play();
            }
        }
        
        // Checks if player's make contact with the edge of the screen and resets position in response
        p1.checkScreenEdgeCollision();
        p2.checkScreenEdgeCollision();
        
        // loops through each player's list of ranged attacks to see if they have completely moved off screen
            // if they have, then remove the ranged attack from the Avatar's list
        for (int i = 0; i < p1RangedAttacks.size(); i++) {
            if(p1RangedAttacks.get(i).isOffScreen()) {
                p1RangedAttacks.remove(i);
            }
        }
        for (int i = 0; i < p2RangedAttacks.size(); i++) {
            if(p2RangedAttacks.get(i).isOffScreen()) {
                p2RangedAttacks.remove(i);
            }
        }
        
        // Check for Avatar body collision and negate movement accordingly
        if(avatarBodyCollision()) {
            
            AssetLoader.dead.play();    //play grappling sound effect
            
            // ============================ Player 1 ===========================
            float a = 4.16f;                //the value of the Avatar's position in relation to the collision point
                                            //eg. hits and moves back (in opposite direction) by that much
                                            
            // determine change in x-vector of Avatar's position
            float newPosChangeX = 0;
            if(p1.getVelocity().x > 0) {
                newPosChangeX = a*-1f;
            } else if(p1.getVelocity().x < 0) {
                newPosChangeX = a*1f;
            }
            
            // determine change in y-vector of Avatar's position
            float newPosChangeY = 0;
            if(p1.getVelocity().y > 0) {
                newPosChangeY = a*-1f;
            } else if(p1.getVelocity().y < 0) {
                newPosChangeY = a*1f;
            }
            
            // sets the Avatar's new position after collision
            p1.setFullPosition(p1.getPosition().add(newPosChangeX, newPosChangeY)); 
            
            // ============================ Player 2 ===========================
            float b = 4.16f;                //the value of the Avatar's position in relation to the collision point
                                            //eg. hits and moves back (in opposite direction) by that much
                                            
            // determine change in x-vector of Avatar's position
            float newPosChangeX2 = 0;
            if(p2.getVelocity().x > 0) {
                newPosChangeX2 = b*-1f;
            } else if(p2.getVelocity().x < 0) {
                newPosChangeX2 = b*1f;
            }
            
            // determine change in y-vector of Avatar's position
            float newPosChangeY2 = 0;
            if(p2.getVelocity().y > 0) {
                newPosChangeY2 = b*-1f;
            } else if(p2.getVelocity().y < 0) {
                newPosChangeY2 = b*1f;
            }
            
            // sets the Avatar's new position after collision
            p2.setFullPosition(p2.getPosition().add(newPosChangeX2, newPosChangeY2));
            // =================================================================
        } // end if
    }
    
    /**
     * Checks if the two Avatar's have body collision
     * @return true if the Avatars' bodies collide
     */
    public boolean avatarBodyCollision() {
        return (Intersector.overlaps(p1.getBoundingCircle(), p2.getBoundingCircle()));
    }
    // ================================ Getters ================================
    /**
     * Retrieves the first player's Avatar
     * @return the first player's Avatar
     */
    public Avatar getP1() {
        return p1;
    }
    /**
     * Retrieves the second player's Avatar
     * @return the second player's Avatar
     */
    public Avatar getP2() {
        return p2;
    }
    // =========================================================================
} // end GameWorld class
