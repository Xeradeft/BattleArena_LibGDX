/*
 * MAN Studios
 * 03/01/2017
 * Final Project ICS4U -- Battle Arena -- 
-*- AssetLoader     --> helper class for loading images, sounds, animations, etc
 */
package com.manstudios.helpers;

// imports
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.manstudios.gameobjects.Avatar;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
/**
 *
 * @author MAN Studios
 */
public class AssetLoader {
        // declare formats
    private static DecimalFormat pf = new DecimalFormat("##0.00%");
    
        // ATTRIBUTES
    public static Sound dead, hitmarker, song;
    public static BitmapFont font, shadow;
    public static Preferences prefs;
    
    // Stores the high score data of names and scores separately, but same index
    public static ArrayList<String> highScoreNames = new ArrayList<String>();
    public static ArrayList<Double> highScoreScores = new ArrayList<Double>();
    
    // B array lists necessary as placeholders for merge sort
    public static ArrayList<String> highScoreNamesB = new ArrayList<String>();
    public static ArrayList<Double> highScoreScoresB = new ArrayList<Double>();
    
        // CONSTRUCTORS (no constructors, only one copy is created)
        // BEHAVIOURS
    /**
     * Called when the game starts up(via BAGame) to load all assets.
     */
    public static void load() {
        // load fonts and respective png files
        font = new BitmapFont(Gdx.files.internal("text.fnt"));
        font.getData().setScale(0.25f, -0.25f);
        shadow = new BitmapFont(Gdx.files.internal("shadow.fnt"));
        shadow.getData().setScale(0.25f, -0.25f);
        
        // load sounds and songs
        dead = Gdx.audio.newSound(Gdx.files.internal("dead.wav"));
        hitmarker = Gdx.audio.newSound(Gdx.files.internal("Hitmarker Sound.wav"));
        song = Gdx.audio.newSound(Gdx.files.internal("narutomusic.wav"));
        
        // Create (or retrieve existing) preferences file
        prefs = Gdx.app.getPreferences("BattleArena");
        
        // Provide default high score of Name: "GOD", Score: "100"
        if (!prefs.contains("highScores")) {
            prefs.putString("highScores", "4M3R1CAN_5N1P3R|100");
        } // end if
        
        getHighScores();        //loads in the data about high scores
    }
    
    /**
     * Receives a big string and maps it to the String highScores in prefs.
     * @param bigString the very large string containing all high score data separated by '|'
     */
    public static void setHighScores(String bigString) {
        prefs.putString("highScores", bigString);   //put a large string with all scores and names seperated by '|'
        prefs.flush();  //saves it
    }

    /**
     * Retrieves the current high score and breaks it up by tokens.
     */
    public static void getHighScores() {
        // declare needed variables
        String bigString = prefs.getString("highScores");
        StringTokenizer st = new StringTokenizer(bigString, "|");
        
        // first, must empty array lists of all contents
        highScoreNames.clear();
        highScoreScores.clear();
        // as long as there are more tokens, continue extracting data
        while(st.hasMoreTokens()) {
            highScoreNames.add(st.nextToken());                         //gets the name
            highScoreScores.add(Double.parseDouble(st.nextToken()));    //and matching score
        }
    }
    
    /**
     * Adds the players' accuracy scores and names to the lists
     * @param p1 the first player's Avatar
     * @param p2 the second player's Avatar
     */
    public static void addHighScores(Avatar p1, Avatar p2) {
        String name1, name2;                                    //declare names
        double p1Score = p1.getAccuracy();  //retrieve player accuracy scores
        double p2Score = p2.getAccuracy();
        
        // runs until a proper name is given for both names (one at a time)
            // names cannot be blank or contain '|'
        while(true) {
            name1 = (String)JOptionPane.showInputDialog(null, "Player 1 (BLUE), enter your name:\nYour accuracy was " + 
                    pf.format(p1Score) + "\n(NOTE: Do not use '|' in your name)",
                    "Battle Arena - Accuracy High Scores", JOptionPane.QUESTION_MESSAGE, null, null, "");
            // checks if name is valid
            if(name1.equals("")) {
                JOptionPane.showMessageDialog(null, "Your name cannot be blank.\nPlease try again.", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else if(name1.contains("|") || name1.contains(" ")) {
                JOptionPane.showMessageDialog(null, "Your name cannot contain '|' or spaces.\nPlease try again.", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                break;  //valid name, so stop loop
            } // end if
        } // end while loop - p1 name
        while(true) {
            name2 = (String)JOptionPane.showInputDialog(null, "Player 2 (RED), enter your name:\nYour accuracy was "
                    + pf.format(p2Score) + "\n(NOTE: Do not use '|' in your name)",
                    "Battle Arena - Accuracy High Scores", JOptionPane.QUESTION_MESSAGE, null, null, "");
            // checks if name is valid
            if(name2.equals("")) {
                JOptionPane.showMessageDialog(null, "Your name cannot be blank.\nPlease try again.", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else if(name2.contains("|") || name2.contains(" ")) {
                JOptionPane.showMessageDialog(null, "Your name cannot contain '|' or spaces.\nPlease try again.", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                break;  //valid name, so stop loop
            } // end if
        } // end while loop - p1 name
        
        // add these to the array lists
        highScoreNames.add(name1);
        highScoreNames.add(name2);
        highScoreScores.add(p1Score);
        highScoreScores.add(p2Score);
        
        //sorts the highscore list after more data has been entered
        sortScores(highScoreScores, highScoreScoresB, 0, highScoreScores.size()-1, highScoreNames, highScoreNamesB);
        
        setHighScores(makeBigString());     // sets the sorted high scores back into prefs as a big string
    }
    
    /**
     * Sorts the high scores and the associated names using merge sort (descending order)
     * @param array the array list of scores to be sorted
     * @param arrayB the placeholder for the array list of scores
     * @param beg the starting index (of array) of the sort
     * @param end the ending index (of array) of the sort
     * @param list the associated array list of names
     * @param listB a placeholder for the associated array list of names
     */
    public static void sortScores(ArrayList array, ArrayList arrayB, int beg, int end, ArrayList list, ArrayList listB) {
        if (end + 1 - beg > 1) {//if the area to be sorted is longer than 1 list item (ie the list can be split in two)
        	//find the midpoint in the list
            int mid = (beg + end) / 2;
            //sort the first half of the arraylist (recursive call)
            sortScores(array, arrayB, beg, mid, list, listB);
            //sort the second half of the arraylist (recursive call)
            sortScores(array, arrayB, mid + 1, end, list, listB);
            //merge the two halves together
            merge(array, arrayB, beg, mid, end, list, listB);
        }
    } // end sortScores()

    /**
     * Merges two array list segments together (part of sortScores)
     * @param array the array list segment of scores to be sorted
     * @param arrayB the placeholder for the array list of scores
     * @param beg the starting index (of array) of the sort
     * @param mid the middle of the array list segment
     * @param end the ending index (of array) of the sort
     * @param list the associated array list of names
     * @param listB a placeholder for the associated array list of names
     */
    public static void merge(ArrayList array, ArrayList arrayB, int beg, int mid, int end, ArrayList list, ArrayList listB) {
        //declare two counter variables one starting in each half of the area to be merged
    	int left = beg;
        int right = mid + 1;
        int counter = 0;//declare another counter variable to use to copy sorted arraylist into the arraylist we're working with
        arrayB.clear();//clear the extra arraylist
        listB.clear(); //and the associated names extra list
        while (left <= mid && right <= end) {//while both the left and right counters are still in bounds (in their respective halved of the arraylist)
        	//if the value of the score that the left counter is at is equal to the value of the score the right counter is at
            if ((Double)array.get(left) == (Double)array.get(right)) {
                //add both scores to the second arraylist
            	arrayB.add(array.get(left));
                arrayB.add(array.get(right));
                    //and associated names
                listB.add(list.get(left));
                listB.add(list.get(right));
                //increment both counters
                left++;
                right++;
            //if the value of the score on the left counter is larger than the value of the score on the right counter
            } else if ((Double)array.get(left) > (Double)array.get(right)) {
                //add the score on the left counter to the second arraylist
            	arrayB.add(array.get(left));
                listB.add(list.get(left));
            	//increment left counter
                left++;
            //if the value of the score on the right counter is larger than the value of the score on the left counter
            } else if ((Double)array.get(right) > (Double)array.get(left)) {
            	//add the score on the right counter to the second arraylist
                arrayB.add(array.get(right));
                listB.add(list.get(right));
                //increment right counter
                right++;
            }
        } // end while loop
        //after the loop ends there will be part of one side of the area to be sorted that hasn't been copied to the second arraylist
        //if the left counter has reached the end of its half
        if (left == mid + 1) {
        	//loop adds the remainder of the right half of the area to be sorted to the second arraylist
            for (int i = right; i <= end; i++) {
                arrayB.add(array.get(i));
                listB.add(list.get(i));
            }
        //if the right counter has reached the end of it
        } else if (right == end + 1) {
        	//loop adds the remainder of the left half of the area to be sorted to the second arraylist
            for (int i = left; i <= mid; i++) {
                arrayB.add(array.get(i));
                listB.add(list.get(i));
            }
        }
        //loop copies the sorted array (in arrayB) to the area of the original array we were trying to sort
        for(int i = beg; i <= end; i++){
        	array.set(i, arrayB.get(counter));
                list.set(i, listB.get(counter));
        	//increment counter to loop through arrayB
        	counter++;
        }
    } // end merge()
    
    /**
     * Creates a big string of all high scores with names and scores separated only by '|'
     * @return a big string of all records with each field separated by a '|'
     */
    public static String makeBigString(){
        
        String bigString = "";      // stores the really long string for storage in preferences
        
        // makes sure there's a max of 100 high scores
        int size = highScoreScores.size();
        if(highScoreScores.size() > 100) {
            size = 100;
        }
        // loops through entire array list
        for (int i = 0; i < size; i++) {
            // add each record of a high score to the big string
            bigString += highScoreNames.get(i) + "|" + highScoreScores.get(i) + "|";
        }
        
        return bigString;   //to store in preferences
    }
    
    /**
     * Called when the game is being closed (to dispose of all loaded assets).
     */
    public static void dispose() {
        dead.dispose();
        hitmarker.dispose();
        song.dispose();
        font.dispose();
        shadow.dispose();
    }
    
} // end AssetLoader class
