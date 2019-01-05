//MAN STUDIOS
//2017-01-18
//Screen used to output the credits of the game
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
public class Credits implements Screen {

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

    @Override
    public void show() {
        stage = new Stage();//creates a new stage
        Gdx.input.setInputProcessor(stage);//processors recieves and processes every key and mouse input
        black = new BitmapFont(Gdx.files.internal("black.fnt"), false);//loads black bitmap font
        white = new BitmapFont(Gdx.files.internal("white.fnt"), false);//loads white bitmap font

        atlas = new TextureAtlas("button.pack");//creates texture atlas out of buttonpack
        skin = new Skin(atlas);//creates a new skin from the atlas
        table = new Table(skin);//creates a table from the skin
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());//sets the boundary of window
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();//creates a new button style
        textButtonStyle.up = skin.getDrawable("button.up.9");//gets up button image
        textButtonStyle.down = skin.getDrawable("button.down.9");//gets button down image
        textButtonStyle.pressedOffsetX = 1;//moves x axis up by 1
        textButtonStyle.pressedOffsetY = -1;//moves y axis down by 2
        textButtonStyle.font = black;//makes font black
        buttonBack = new TextButton("BACK", textButtonStyle);//makes a back button
        buttonBack.addListener(new ClickListener() {//listener for clicks
            @Override
            public void clicked(InputEvent event, float x, float y) {//when button is clicked
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());//go to main menu

            }
        });

        Label.LabelStyle headingStyle = new Label.LabelStyle(white, Color.WHITE);//creating heading style
        heading = new Label(BAGame.CREDITS, headingStyle);//accesses credits
        heading.setFontScale((float) 1);//font scale to 100%
        //putting stuff together
        table.add(heading);//adds heading to table
        table.getCell(heading).spaceBottom(100);//adds space below heading
        table.row();//adds heading to row
        table.add(buttonBack);//adds back button
        stage.addActor(table);//adds table to stage

        //creating animations
        tweenManager = new TweenManager();//new tween manager
        Tween.registerAccessor(Actor.class, new ActorAccessor());//registers actor class to accessor

        //heading color animation
        //table fade in
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
     * Disposes resources when done
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
