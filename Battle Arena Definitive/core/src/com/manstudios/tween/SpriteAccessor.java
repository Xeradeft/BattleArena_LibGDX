//MAN STUDIOS
//2017-01-18
//Class designed to access sprites
package com.manstudios.tween;
import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 *
 * @author Nathan
 */
public class SpriteAccessor implements TweenAccessor<Sprite>{
    public static final int ALPHA = 0;
    /**
     * 
     * @param target actor being selected
     * @param tweenType number representing tween type
     * @param returnValues array of returned values
     * @return 
     */
    @Override
    public int getValues(Sprite target, int tweenType, float[] returnValues){
        switch(tweenType){
            case ALPHA:
                returnValues[0] = target.getColor().a; //return values at 0 is the same as actos a value
                return 1;//return 1
            default:
                assert false;
                return -1;//return false
        }
    }
    /**
     * 
     * @param target actor being selected
     * @param tweenType number representing tween type
     * @param newValues  array of new values
     */
    public void setValues(Sprite target, int tweenType, float [] newValues){
        switch(tweenType){
            case ALPHA:
                target.setColor(target.getColor().r,target.getColor().g,target.getColor().b,newValues[0]);//gets r g b colors and makes new alpha
                break;
            default:
                assert false;
        }
    }
    
}
