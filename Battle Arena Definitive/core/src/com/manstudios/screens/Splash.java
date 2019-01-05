//MAN STUDIOS
//2017-01-18
//Initial splash screen the user sees upon starting the program. 
package com.manstudios.screens;

/**
 *
 * @author Nathan
 */
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.manstudios.tween.SpriteAccessor;

public class Splash implements Screen {

    private SpriteBatch batch;
    private Sprite splash;
    private TweenManager tweenManager;

    /**
     * Show method contains the elements that are in the screen. Applies only if
     * it is the current screen of your game.
     */
    @Override
    public void show() {
        batch = new SpriteBatch();//creates a new sprite batch
        tweenManager = new TweenManager();//creates a new tween manager
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());//registers an accessor with a class object
        Texture splashTexture = new Texture("splash.jpg");//file path for the splash image
        splash = new Sprite(splashTexture);//creates a new sprite based off the found image
        splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());//sets the size equal to that of the window
        Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);
        Tween.to(splash, SpriteAccessor.ALPHA, 2).target(1).repeatYoyo(1, 2).setCallback(new TweenCallback() {//makes splash screen fade in and out
            public void onEvent(int type, BaseTween<?> source) {//after an event happens, code below triggers
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());//Sets screen to main menu
            }
        }).start(tweenManager);
    }
    /**
     * 
     * @param delta time between last rendered frame and current one
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);//clears the canvas
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//decides the color bit
        tweenManager.update(delta);//updates the tween manager based off of delta
        batch.begin();//begin batch
        splash.draw(batch);//draws batch
        batch.end();//ends batch
    }

    @Override
    public void resize(int width, int height) {
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
     * disposes resources when done
     */
    @Override
    public void dispose() {
        batch.dispose();
        splash.getTexture().dispose();
    }
}
