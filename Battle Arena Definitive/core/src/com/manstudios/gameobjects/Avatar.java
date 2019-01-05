/*
 * MAN Studios
 * 04/01/2017
 * Final Project ICS4U -- Battle Arena -- 
 -*- Avatar      --> the player's controlled avatar
 */
package com.manstudios.gameobjects;

// imports
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
/**
 *
 * @author MAN Studios
 */
public class Avatar {
        // ATTRIBUTES
    private Vector2 position, velocity;
    
    private int radius, health;
    private double shotCount, hitCount, accuracy;
    private Circle boundingCircle;  //for collision intersector
    private Color colour;
    
    private ArrayList<Ranged> rangedAttackList = new ArrayList<Ranged>();   //list of all the players shot projectiles
    
    private float movementSpeed = 200;
    
    // constants for actual game size (edge of game screen)
    public static final int ACTUAL_GAME_WIDTH = 600;
    public static final int ACTUAL_GAME_HEIGHT = 400;
    
        // CONSTRUCTORS
    /**
     * Primary Constructor - instantiates a new Avatar
     * @param x the x-coordinate of the Avatar
     * @param y the y-coordinate of the Avatar
     * @param r the radius of the Avatar
     * @param c the colour of the Avatar
     */
    public Avatar(float x, float y, int r, Color c) {
        health = 20;
        shotCount = 0;
        hitCount = 0;
        accuracy = 0;
        colour = c;
        radius = r;
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        boundingCircle = new Circle();
    }
        // BEHAVIOURS
    /**
     * Updates the Avatar's position using acceleration and velocity (called when GameWorld updates)
     * @param delta the number of seconds (usually a small fraction) that has passed since the last time that the render method (from GameWorld) was called
     */
    public void update(float delta) {
        // ================= Check speed caps (all directions) =================
                // By doing this, the Avatar will only ever move at the specified movement speed,
                // because when it is moving continuously, it moves at the specified speed.
                // When the button is released it will continously move in the opposite direction at the specified speed,
                // but only for an instant; however, it's enough to negate all the initial speed because of the speed caps
                        // The speed caps make sure all movement increments are always at the specified movement speed
        if(velocity.y < -1*movementSpeed) { //up
            velocity.y = -1*movementSpeed;
        }
        if(velocity.y > movementSpeed) {    //down
            velocity.y = movementSpeed;
        }
        if(velocity.x < -1*movementSpeed) { //left
            velocity.x = -1*movementSpeed;
        }
        if(velocity.x > movementSpeed) {    //right
            velocity.x = movementSpeed;
        }
        // =====================================================================
        position.add(velocity.cpy().scl(delta));        //add the updated scaled velocity to the Avatar's position (gives us our new position)
        
        /* NOTE: scaled means multiply the acceleration and velocity vectors by the delta,
                 which is the amount of time that has passed since the update method was previously called (this has a normalizing effect)
                 By scaling our Vectors with delta, we can achieve frame-rate independent movement
                 eg. If the update method took twice as long to execute, then we just move our character by 2x the original velocity, and so on*/
        boundingCircle.set(position.x, position.y, radius);     //moves bounds with the avatar
        
        // updates the shot accuracy (check if shot(s) have been fired yet)
        accuracy = hitCount / shotCount;
        if(shotCount == 0) {
            accuracy = 0.0;
        }
    }
    
    /**
     * Creates a projectile from the Avatar in the corresponding input's direction
     * @param a the Avatar that is shooting
     * @param xv the horizontal velocity of the projectile
     * @param yv the vertical velocity of the projectile
     */
    public void doRangedAttack(Avatar a, float xv, float yv) {
        shotCount++;    //accumulate shot count
        // Create a new projectile in the corresponding direction and add it to the list
        Ranged rangedAttack = new Ranged(position.x, position.y, 3, a.getColour(), xv, yv);
        rangedAttackList.add(rangedAttack);
    }
    
    /**
     * Check screen boundaries and limit Avatar from moving offscreen.
     */
    public void checkScreenEdgeCollision() {
        if(position.y - radius < 0) {                   //top side of screen
            position.y = radius;
        }
        if(position.y + radius > ACTUAL_GAME_HEIGHT) {  //bottom side of screen
            position.y = ACTUAL_GAME_HEIGHT - radius;
        }
        if(position.x - radius < 0) {                   //left side of screen
            position.x = radius;
        }
        if(position.x + radius > ACTUAL_GAME_WIDTH) {   //right side of screen
            position.x = ACTUAL_GAME_WIDTH - radius;
        }
    }
    
    // =============================== Movement ================================
        // moveDirectionOn() continues to add the movement speed of that character as a
            // correct vector to its velocity
        // moveDirectionOff() does the same opposite of a the related moveDirectionOn()
    // changes a vector of the Avatar's velocity by an increment or decrement of its movement speed
    public void moveUpOn() {
        velocity.y -= movementSpeed;
    }
    public void moveUpOff() {
        velocity.y += movementSpeed;
    }
    
    public void moveDownOn() {
        velocity.y += movementSpeed;
    }
    public void moveDownOff() {
        velocity.y -= movementSpeed;
    }
    
    public void moveLeftOn() {
        velocity.x -= movementSpeed;
    }
    public void moveLeftOff() {
        velocity.x += movementSpeed;
    }
    
    public void moveRightOn() {
        velocity.x += movementSpeed;
    }
    public void moveRightOff() {
        velocity.x -= movementSpeed;
    }
    // ================================ Getters ================================
    /**
     * Retrieves the x-position of an Avatar
     * @return the x-position of the Avatar
     */
    public float getX() {
        return position.x;
    }
    /**
     * Retrieves the y-position of an Avatar
     * @return the y-position of the Avatar
     */
    public float getY() {
        return position.y;
    }
    /**
     * Retrieves the radius of an Avatar
     * @return the radius of the Avatar
     */
    public float getRadius() {
        return radius;
    }
    /**
     * Retrieves an Avatar's body hurtbox
     * @return the Avatar's body hurtbox
     */
    public Circle getBoundingCircle() {
        return boundingCircle;
    }
    /**
     * Retrieves an Avatar's position
     * @return the Avatar's position
     */
    public Vector2 getPosition() {
        return position;
    }
    /**
     * Retrieves an Avatar's velocity
     * @return the Avatar's velocity
     */
    public Vector2 getVelocity() {
        return velocity;
    }
    /**
     * Retrieves an Avatar's colour
     * @return the Avatar's colour
     */
    public Color getColour() {
        return colour;
    }
    /**
     * Retrieves an Avatar's health
     * @return the Avatar's health
     */
    public int getHealth() {
        return health;
    }
    /**
     * Retrieves an Avatar's shot count
     * @return the Avatar's shot count
     */
    public double getShotCount() {
        return shotCount;
    }
    /**
     * Retrieves an Avatar's hit count
     * @return the Avatar's hit count
     */
    public double getHitCount() {
        return hitCount;
    }
    /**
     * Retrieves an Avatar's shot accuracy
     * @return the Avatar's shot accuracy
     */
    public double getAccuracy() {
        return accuracy;
    }
    /**
     * Retrieves an Avatar's list of projectiles
     * @return the Avatar's list of projectiles
     */
    public ArrayList<Ranged> getRangedAttackList() {
        return rangedAttackList;
    }
    // ================================ Setters ================================
    /**
     * Changes an Avatar's health
     * @param hp the new health of the Avatar
     */
    public void setHealth(int hp) {
        health = hp;
    }
    /**
     * Decreases an Avatar's health
     * @param hp the amount of health the Avatar lost
     */
    public void subtractHealth(int hp) {
        health -= hp;
        // make sure health doesn't go below zero
        if(health < 0) {
            health = 0;
        }
    }
    /**
     * Increments an Avatar's hit count
     */
    public void incrementHitCount() {
        hitCount++;
    }
    /**
     * Changes an Avatar's position
     * @param newPos the new position of the Avatar
     */
    public void setFullPosition(Vector2 newPos) {
        position = newPos;
    }
    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
    }
    /**
     * Changes an Avatar's velocity
     * @param x the x-vector of the Avatar's new velocity
     * @param y the y-vector of the Avatar's new velocity
     */
    public void setVelocity(float x, float y) {
        velocity.x = x;
        velocity.y = y;
    }
    // =========================================================================
} // end Avatar class
