//MAN STUDIOS
//2017-01-18
//Screen used to output the highscores of the game
package com.manstudios.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.manstudios.helpers.AssetLoader;
import com.manstudios.tween.ActorAccessor;
import java.text.DecimalFormat;

/**
 *
 * @author Nathan
 */
public class Highscore implements Screen {
    // declare formats

    private static DecimalFormat pf = new DecimalFormat("##0.00%");//creates decimal format

    // ATTRIBUTES
    private Stage stage;
    private Actor actor;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton buttonBack;
    private BitmapFont white, black;
    private Label heading, scores;
    private TweenManager tweenManager;

    private OrthographicCamera cam;         //camera that views 3D as 2D
    private SpriteBatch batcher;            //draws images for us using the indices provided (x, y, width and height, typically)

    public Highscore() {
        cam = new OrthographicCamera();                 //init cam
        cam.setToOrtho(true, 600, 400);//true = we want an orthographic projection || width || height
        //(true, 600, 400);         //since GameScreen res = 272,408 ; game scaled x2 when drawn
        batcher = new SpriteBatch();                    //init batcher
        batcher.setProjectionMatrix(cam.combined);      //attach it to cam
    }

    /**
     * Renders the screen
     *
     * @param delta time elapsed since the last frame was rendered
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);//clears the screen with an alpha value of 1
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//constant used to tell which buffer bit to clear
        tweenManager.update(delta);//updates tween manager
        stage.act(delta);//starts acting on stage based on delta
        stage.draw();//draws the stage
        batcher.begin();//begins the batcher
        int size = 10;//sets size to 10
        if (AssetLoader.highScoreScores.size() < 10) {//if less than ten
            size = AssetLoader.highScoreScores.size();//size becomes asset loader size
        }
        for (int i = 0; i < size; i++) {//loops used to access and display high scores
            AssetLoader.shadow.draw(batcher, (i + 1) + ". " + AssetLoader.highScoreNames.get(i), 50, 100 + i * 20);
            AssetLoader.font.draw(batcher, (i + 1) + ". " + AssetLoader.highScoreNames.get(i), 50, 100 + i * 20);
            AssetLoader.shadow.draw(batcher, pf.format(AssetLoader.highScoreScores.get(i)), 400, 100 + i * 20);
            AssetLoader.font.draw(batcher, pf.format(AssetLoader.highScoreScores.get(i)), 400, 100 + i * 20);
        }

        batcher.end();  // End SpriteBatch
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
     * displays elements onto the screen
     */
    @Override
    public void show() {
        stage = new Stage();//creates new stage
        Gdx.input.setInputProcessor(stage);//makes input processor for the stage for clicks and key presses
        black = new BitmapFont(Gdx.files.internal("black.fnt"), false);//loads black font bitmap
        white = new BitmapFont(Gdx.files.internal("white.fnt"), false);//loads white font bitmap
        atlas = new TextureAtlas("button.pack");//creates texture atlas from button packs
        skin = new Skin(atlas);//creates skin from atlas
        table = new Table(skin);//creates table fron skin
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());//sets boundary to window
        //creating buttons
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();//creates new button style
        textButtonStyle.up = skin.getDrawable("button.up.9");//imports button up image
        textButtonStyle.down = skin.getDrawable("button.down.9");//import button down image
        textButtonStyle.pressedOffsetX = 1;//moves up x axis by 1
        textButtonStyle.pressedOffsetY = -1;//moves left y axis by 1
        textButtonStyle.font = black;//creates black font
        buttonBack = new TextButton("BACK", textButtonStyle);//creates back button
        buttonBack.addListener(new ClickListener() {//onclick listener
            @Override
            public void clicked(InputEvent event, float x, float y) {//if clicked
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());//display main menu

            }
        });
        Label.LabelStyle headingStyle = new Label.LabelStyle(white, Color.WHITE);//creating heading style

        table.add(scores);//adds scores to table
        table.row();//added to row
        table.add(buttonBack);//adds back button to table
        stage.addActor(table);//adds table to stage

        //creating animations
        tweenManager = new TweenManager();//new tween manager
        Tween.registerAccessor(Actor.class, new ActorAccessor());//registers actor class to actor accessor

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
