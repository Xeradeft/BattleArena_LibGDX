//MAN STUDIOS   
//2017-01-18
//A class designed to be able to access actors
package com.manstudios.tween;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 *
 * @author Nathan
 */
public class ActorAccessor implements TweenAccessor<Actor>{
    public static final int Y = 0, RGB = 1,ALPHA = 2;
    
    public ActorAccessor() {
    }
    /**
     * 
     * @param target actor being selected
     * @param tweenType number representing the tween type
     * @param returnValues array of returned balues
     * @return 
     */
    @Override
    public int getValues(Actor target, int tweenType, float[] returnValues) {
        switch(tweenType){
            case Y:
                returnValues[0] = target.getY();//returns value equals to actors y value
                return 1;//return 1
            case RGB:
                returnValues[0] = target.getColor().r;//accesses actors r color
                returnValues[1] = target.getColor().g;//accesses actors g color
                returnValues[2] = target.getColor().b;//accesses actors b color
                return 3;//return 3
            case ALPHA:
                returnValues[0] = target.getColor().a;//access targets alpha color
                return 1;//return 1
            default:
                assert false;
                return -1;//return -1
        }
    }
    /**
     * 
     * @param target actor being selected
     * @param tweenType number representing tween type
     * @param newValues array of new values
     */
    @Override
    public void setValues(Actor target, int tweenType, float[] newValues) {
        switch(tweenType){
            case Y:
                target.setY(newValues[0]);//sets actors y values to new values at 0
                break;
            case RGB:
                target.setColor(newValues[0], newValues[1], newValues[2], target.getColor().a);//sets r g b colors of actor and accesses actors alpha value
                break;
            case ALPHA:
                target.setColor(target.getColor().r,target.getColor().g,target.getColor().b,newValues[0]);//gets r g b colors of target
                break;
            default:
                assert false;
    }
    }   
}
