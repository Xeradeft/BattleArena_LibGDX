/*
 * MAN Studios
 * 03/01/2017
 * Final Project ICS4U -- Battle Arena --
    -> sets up the main game and ends it
 */
package com.manstudios.battlearena;

// imports
import com.manstudios.screens.Splash;
import com.badlogic.gdx.Game;
import com.manstudios.helpers.AssetLoader;
/**
 * 
 * @author MAN Studios
 */
public class BAGame extends Game {
        // CONSTANTS
    public static final String TITLE = "Battle Arena", VERSION = "1.0";
    public static final String CREDITS = "Credits\nProject Manager: Anish Madan\nLead Front End Programmer: Nathan Chan\nLead Back End Programmer: Mitchell Van Braeckel";
    public static final String H2P = "The objective of the game is\nto lower your oponents health to 0.\nPlayer 1: \nUse WASD to move\nUse FCVB to shoot\nPlayer 2:\nUse Arrow Keys to move\n Use L,./ to shoot";
    public static final String p1Win = "Player 2 has been slain. Player 1 is victorious!";
    public static final String p2Win = "Player 1 has been slain. Player 2 is victorious!";
        
        // no contructor(s)
        // BEHAVIOURS
    /**
     * Called when it's first created (loads all assets and creates the startup [Splash] intro screen).
     */
     @Override
    public void create() {
        AssetLoader.load();
        setScreen(new Splash());
    }
    /**
     * Called when it's destroyed (disposes of all loaded assets).
     */
     @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
    /**
     * Updates and renders the Game.
     */
     @Override
    public void render(){
        super.render();
    }
    /**
     * Called when it's resized  (can happen at any point during a non-paused state, but will never happen before create() call).
     * @param width the new width in pixels
     * @param height the new height in pixels
     */
     @Override
    public void resize(int width, int height){
        super.resize(width, height);
    }
    /**
     * Called when it's paused (usually when it's not active or visible on screen) (also paused before it's destroyed).
     */
     @Override
    public void pause(){
        super.pause();
    }
    /**
     * Called when it's resumed from a paused state (usually when it regains focus).
     */
     @Override
    public void resume(){
        super.resume();
    }
} // end BAGame
