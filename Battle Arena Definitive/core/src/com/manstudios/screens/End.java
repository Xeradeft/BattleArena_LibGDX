//MAN STUDIOS
//2017-01-18
//Screen used to output the end of the game
package com.manstudios.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.manstudios.battlearena.BAGame;
import com.manstudios.gameobjects.Avatar;
import com.manstudios.helpers.AssetLoader;
import com.manstudios.tween.ActorAccessor;
import java.text.DecimalFormat;

/**
 *
 * @author Nathan
 */
public class End implements Screen {
    // declare formats

    private static DecimalFormat pf = new DecimalFormat("##0.00%");//creates decimal format

    // ATTRIBUTES
    private Avatar p1, p2;

    private Stage stage;
    private Actor actor;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton buttonMenu, buttonExit;
    private BitmapFont white, black;
    private Label heading;
    private TweenManager tweenManager;
    //carry over the two avatars
    public End(Avatar p1, Avatar p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     * renders the screen
     *
     * @param delta time elapsed since the last frame was rendered
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);//clears the screen with an alpha value of 1
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tweenManager.update(delta);//updates the tween manager
        stage.act(delta);//calls all the actors on stage to age
        stage.draw();//draws the stage

    }

    /**
     * Used to resize the window
     *
     * @param width width of the screen
     * @param height height of the screen
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);//makes it so that all proportions are the same regardless of screen size


    }

    /**
     * used to display on screen
     */
    @Override
    public void show() {
        stage = new Stage();//creates new stage
        Gdx.input.setInputProcessor(stage);//gets input from clicks and mouse
        black = new BitmapFont(Gdx.files.internal("black.fnt"), false);//import black font
        white = new BitmapFont(Gdx.files.internal("white.fnt"), false);//import white font
        atlas = new TextureAtlas("button.pack");//creates texture atlas from button pack
        skin = new Skin(atlas);//creates skin from atlas
        table = new Table(skin);//creates table from skin
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());//sets boundary of the window
        //creating buttons
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();//makes new text button style
        textButtonStyle.up = skin.getDrawable("button.up.9");//gets button up image
        textButtonStyle.down = skin.getDrawable("button.down.9");//gets button down image
        textButtonStyle.pressedOffsetX = 1;//moves 1 up x axis
        textButtonStyle.pressedOffsetY = -1;//moves 1 down x axis
        textButtonStyle.font = black;//creates black font
        buttonMenu = new TextButton("MAIN MENU", textButtonStyle);//creates main menu button
        buttonMenu.addListener(new ClickListener() {//on click listener
            @Override
            public void clicked(InputEvent event, float x, float y) {//when clicked
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());//display main menu

            }
        });

        buttonExit = new TextButton("EXIT", textButtonStyle);//creates exit button
        buttonExit.addListener(new ClickListener() {//creates on click listener
            @Override
            public void clicked(InputEvent event, float x, float y) {//when clicked
                Gdx.app.exit();//exit app
            }
        });

        if (p1.getHealth() == 0) {//if player one health is 0
            Label.LabelStyle headingStyle = new Label.LabelStyle(white, Color.RED);//create red label
            heading = new Label(BAGame.p2Win, headingStyle);//display victory message
        } else if (p2.getHealth() == 0) {//if player two health is 0
            Label.LabelStyle headingStyle = new Label.LabelStyle(white, Color.BLUE);//create blue label
            heading = new Label(BAGame.p1Win, headingStyle);//displau victory message
        }

        heading.setFontScale(1);//sets font scale to 100%
        //putting stuff together
        table.add(heading);//adds heading to table
        table.getCell(heading).spaceBottom(100);//adds space beneath heading
        table.row();//adds heading to row
        table.add(buttonMenu);//adds button menu to table
        stage.addActor(table);//adds table to stage
        table.row();//adds table to row
        table.add(buttonExit);//adds exit button to table
        stage.addActor(table);//adds table to stage
        //creating animations
        tweenManager = new TweenManager();//new tween manager
        Tween.registerAccessor(Actor.class, new ActorAccessor());//register actor class to actor accessor

        //heading color animation
        //table fade in
        Tween.from(table, ActorAccessor.ALPHA, .5f).target(0).start(tweenManager);
        Tween.from(table, ActorAccessor.Y, .5f).target(Gdx.graphics.getHeight() / 4).start(tweenManager);

        AssetLoader.addHighScores(p1, p2);//adds highscores to asset loader
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
    /**
     * disposes resources when finished
     */
    @Override
    public void dispose() {
        stage.dispose();
        atlas.dispose();
        skin.dispose();
        white.dispose();
        black.dispose();
    }

}
