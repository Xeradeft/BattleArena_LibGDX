/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manstudios.screens;

import aurelienribon.tweenengine.Timeline;
import com.manstudios.tween.ActorAccessor;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.manstudios.battlearena.BAGame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 *
 * @author Nathan
 */
public class MainMenu implements Screen {

    private Stage stage;
    private Actor actor;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton buttonPlay, buttonExit, buttonCredits, buttonHighscore, buttonH2P;
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
        stage = new Stage();//new stage
        Gdx.input.setInputProcessor(stage);//creates input processor for stage
        black = new BitmapFont(Gdx.files.internal("black.fnt"), false);//loads black bitmap font
        white = new BitmapFont(Gdx.files.internal("white.fnt"), false);//loads white bitmap font
        atlas = new TextureAtlas("button.pack");//creates texture atlas from button pack
        skin = new Skin(atlas);//create skin from atlas
        table = new Table(skin);//creates table from skin
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());//sets boundary equal to window
        //creating buttons
        TextButtonStyle textButtonStyle = new TextButtonStyle();//creates new text button style
        textButtonStyle.up = skin.getDrawable("button.up.9");//import button up image
        textButtonStyle.down = skin.getDrawable("button.down.9");//import button down image
        textButtonStyle.pressedOffsetX = 1;//moves up x axis
        textButtonStyle.pressedOffsetY = -1;//moves left y axis
        textButtonStyle.font = black;//make black font
        buttonExit = new TextButton("EXIT", textButtonStyle);//make exit button
        buttonExit.addListener(new ClickListener() {//onclick listener
            @Override
            public void clicked(InputEvent event, float x, float y) {//when clicked
                Gdx.app.exit();//exit app

            }
        });
        buttonExit.pad(15);//add 15px of space
        buttonPlay = new TextButton("PLAY", textButtonStyle);//make play button
        buttonPlay.addListener(new ClickListener() {//onclick listener
            @Override
            public void clicked(InputEvent event, float x, float y) {//when clicked
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());//start game
            }
        });
        buttonPlay.pad(15);//add 15 px space

        buttonCredits = new TextButton("CREDITS", textButtonStyle);//make credits button
        buttonCredits.addListener(new ClickListener() {//onclick listener
            @Override
            public void clicked(InputEvent event, float x, float y) {//when clicked
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Credits());//display credits screen
            }
        });
        buttonCredits.pad(15);//add 15px space

        buttonHighscore = new TextButton("HIGHSCORES", textButtonStyle);//creates highscores button
        buttonHighscore.addListener(new ClickListener() {//onclick listener
            @Override
            public void clicked(InputEvent event, float x, float y) {//when clicked
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Highscore());//display highscores
            }
        });
        buttonHighscore.pad(15);//add 15px space

        buttonH2P = new TextButton("CONTROLS", textButtonStyle);//add controls button
        buttonH2P.addListener(new ClickListener() {//onclick listener
            @Override
            public void clicked(InputEvent event, float x, float y) {//when clicked
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Controls());//display game controls
            }
        });
        buttonH2P.pad(15);//add 15px space

        LabelStyle headingStyle = new LabelStyle(white, Color.WHITE);//creating heading style

        heading = new Label(BAGame.TITLE, headingStyle);//sets heading to title
        heading.setFontScale(2);//200% font scale

        //putting stuff together
        table.add(heading);//add heading to table

        table.getCell(heading).spaceBottom(30);//add space below
        table.row();//add heading to tow
        table.add(buttonPlay);//add play button
        table.getCell(buttonPlay).spaceRight(10);//add space between
        table.row();//add play button to row
        table.add(buttonH2P);//add how to play button to table
        table.getCell(buttonH2P).spaceBottom(10);//add space between
        table.row();//add how to play button to row
        table.add(buttonHighscore);//add highscore button to table
        table.getCell(buttonHighscore).spaceBottom(10);//add space between
        table.row();//add button to row
        table.add(buttonCredits);//add credits button to table
        table.getCell(buttonCredits).spaceBottom(10);//add space between
        table.row();//add button to row
        table.add(buttonExit);//add exit button
        stage.addActor(table);//add table to stage

        //creating animations
        tweenManager = new TweenManager();//creates new tween manager
        Tween.registerAccessor(Actor.class, new ActorAccessor());//registers actor class to actor accessor

        //begins sequence of changing the title to different random colors infinitely
        Timeline.createSequence().beginSequence()
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 0, 1))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 1, 0))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 0, 0))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 1, 0))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 1, 1))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 0, 1))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 1, 1))
                .end().repeat(Tween.INFINITY, 0).start(tweenManager);
        //turns all buttons to black and fades them in one by one after 0.25 seconds
        Timeline.createSequence().beginSequence()
                .push(Tween.set(buttonPlay, ActorAccessor.ALPHA).target(0))
                .push(Tween.set(buttonHighscore, ActorAccessor.ALPHA).target(0))
                .push(Tween.set(buttonCredits, ActorAccessor.ALPHA).target(0))
                .push(Tween.set(buttonExit, ActorAccessor.ALPHA).target(0))
                .push(Tween.from(heading, ActorAccessor.ALPHA, .25f).target(0))
                .push(Tween.to(buttonPlay, ActorAccessor.ALPHA, .25f).target(1))
                .push(Tween.to(buttonHighscore, ActorAccessor.ALPHA, .25f).target(1))
                .push(Tween.to(buttonCredits, ActorAccessor.ALPHA, .25f).target(1))
                .push(Tween.to(buttonExit, ActorAccessor.ALPHA, .25f).target(1))
                .end().start(tweenManager);

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
