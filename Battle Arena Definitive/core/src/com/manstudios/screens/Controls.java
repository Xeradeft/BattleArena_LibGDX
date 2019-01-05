//MAN STUDIOS
//2017-01-18
//Screen used to output the controls of the game
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
import com.manstudios.tween.ActorAccessor;

/**
 *
 * @author Nathan
 */
public class Controls implements Screen {

    private Stage stage;
    private Actor actor;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton buttonBack;
    private BitmapFont white, black;
    private Label heading;
    private TweenManager tweenManager;

    /**
     * Renders the screen
     *
     * @param delta time elapsed since the last frame was rendered
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);//clears the screen with an alpha value of 1
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//constant used to tell which buffer bit to clear
        tweenManager.update(delta);//updates the tween manager
        stage.act(delta);//calls actor.act method on each actor on stage
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
     * displays everything onto the screen
     */
    @Override
    public void show() {
        stage = new Stage();//creates a new stage
        Gdx.input.setInputProcessor(stage);//processors recieves and processes every key and mouse input
        black = new BitmapFont(Gdx.files.internal("black.fnt"), false);//loads in black bitmap font
        white = new BitmapFont(Gdx.files.internal("white.fnt"), false);//loads in white bitmap font
        atlas = new TextureAtlas("button.pack");//creates a texture atlas based on the button back
        skin = new Skin(atlas);//creates a new skin from the atlas
        table = new Table(skin);//makes a new table from the skin
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());//sets boundaries of window
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();//creates a new button style
        textButtonStyle.up = skin.getDrawable("button.up.9");//loads in button up image
        textButtonStyle.down = skin.getDrawable("button.down.9");//loads in button down image
        textButtonStyle.pressedOffsetX = 1;//moves the x axis up 1
        textButtonStyle.pressedOffsetY = -1;//moves the y axis left 1
        textButtonStyle.font = black;//creates black font
        buttonBack = new TextButton("BACK", textButtonStyle);//creates back button
        buttonBack.addListener(new ClickListener() {//adds click listener
            @Override
            public void clicked(InputEvent event, float x, float y) {//if the button is clicked
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());//switch to main menu

            }
        });

        Label.LabelStyle headingStyle = new Label.LabelStyle(white, Color.WHITE);//creating labell

        heading = new Label(BAGame.H2P, headingStyle);//creates how to play label
        heading.setFontScale(1);//sets font scale to 100%
        table.add(heading);//adds heading to the table
        table.getCell(heading).spaceBottom(100);//gets the heading cell and add 100 px of space below
        table.row();//adds heading to a row
        table.add(buttonBack);//adds back button to stage
        stage.addActor(table);//adds actors to stage

        //creating animations
        tweenManager = new TweenManager();//new tween manager
        Tween.registerAccessor(Actor.class, new ActorAccessor());//registers the actor class to the actor accessor

        //used to transition one screen to another
        Tween.from(table, ActorAccessor.ALPHA, .5f).target(0).start(tweenManager);
        Tween.from(table, ActorAccessor.Y, .5f).target(Gdx.graphics.getHeight() / 4).start(tweenManager);
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
     * Disposes all resources when finished
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
