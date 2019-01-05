/*
 * MAN Studios
 * 03/01/2017
 * Final Project ICS4U -- Battle Arena --
 */
package com.manstudios.battlearena.desktop;

// imports
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.manstudios.battlearena.BAGame;

/**
 * Launches a desktop version of the game
 * @author MAN Studios
 */
public class DesktopLauncher {  
    public static void main (String[] arg) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        
        // set up config -> set title and size
        cfg.title = BAGame.TITLE + " v" + BAGame.VERSION;
        cfg.vSyncEnabled = true;
        cfg.useGL30 = true;
        cfg.width = 1200;
        cfg.height = 800;
        
        new LwjglApplication(new BAGame(), cfg);
    } // end main
} // end DesktopLauncher
