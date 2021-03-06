package com.oreilly.demo.android.pa.uidemo.state;

import android.os.Bundle;

import com.oreilly.demo.android.pa.uidemo.draw.Circle;
import com.oreilly.demo.android.pa.uidemo.draw.CircleF;
import com.oreilly.demo.android.pa.uidemo.draw.DrawableObj;
import com.oreilly.demo.android.pa.uidemo.draw.Line;
import com.oreilly.demo.android.pa.uidemo.draw.Text;

import java.util.Vector;

/**
 * The end state is the end of the game.
 *
 * Created by Salomon on 4/22/15.
 */
public class End implements State {

    public End(final StateView sm) {
        this.sm = sm;
    }
    private int width;
    private int height;
    private int difficulty;
    private int remainingFrames = 0;

    private DrawableObj[] circles;
    private Text text;

    private Vector<DrawableObj> drawMe = new Vector<DrawableObj>();

    private final StateView sm;

    /**
     *
     * @param width width of screen
     * @param height height of screen
     * @return null
     */
    public Vector<DrawableObj> start(int width, int height)  {
        this.width = width;
        this.height = height;
        float percBorder = .1f;
        int gridSize = 0;

        circles = new DrawableObj[difficulty+1];

        if (width > height){
            gridSize = (int)(height - height * percBorder);
            int gridLocation = (int)(width/2.0 - gridSize/2.0);

            int circleSize = (int)(gridSize/5/4);
            int circlePosY = (int)(height/2.0);
            int circlePosX = gridLocation + gridSize + gridLocation/2;

            text = new Text("CLEARED", width/2, height/2, new int[]{255,184,125,143},height/8);

            if (circles.length == 1){
                circles[0] = new CircleF(circleSize,circlePosX,circlePosY,new int[]{255,250,172,152});
            }
            if (circles.length == 2){
                circles[0] = new CircleF(circleSize,circlePosX,circlePosY-gridSize/10,new int[]{255,184,125,143});
                circles[1] = new CircleF(circleSize,circlePosX,circlePosY+gridSize/10,new int[]{255,208,247,244});
            }
            if (circles.length == 3){
                circles[0] = new CircleF(circleSize,circlePosX,circlePosY-gridSize/5,new int[]{255,184,125,143});
                circles[1] = new Circle(circleSize,circlePosX,circlePosY+gridSize/5,new int[]{255,255,255,255});
                circles[2] = new CircleF(circleSize,circlePosX,circlePosY,new int[]{255,250,172,152});
            }
        } else {
            gridSize = (int)(width - width * percBorder);
            int gridLocation = (int)(height/2.0 - gridSize/2.0);

            int circleSize = (int)(width * percBorder/2);
            int circlePosY = (int)(width/2.0);
            int circlePosX = gridLocation + gridSize + gridLocation/2;

            text = new Text("CLEARED", width/2, height/2, new int[]{255,184,125,143},width/8);

            if (circles.length == 1){
                circles[0] = new CircleF(circleSize,circlePosY,circlePosX,new int[]{255,250,172,152});
            }
            if (circles.length == 2){
                circles[0] = new CircleF(circleSize,circlePosY-gridSize/10,circlePosX,new int[]{255,184,125,143});
                circles[1] = new CircleF(circleSize,circlePosY+gridSize/10,circlePosX,new int[]{255,208,247,244});
            }
            if (circles.length == 3){
                circles[0] = new CircleF(circleSize,circlePosY-gridSize/5,circlePosX,new int[]{255,184,125,143});
                circles[1] = new Circle(circleSize,circlePosY+gridSize/5,circlePosX,new int[]{255,255,255,255});
                circles[2] = new CircleF(circleSize,circlePosY,circlePosX,new int[]{255,250,172,152});
            }
        }
        for (int i = 0; i<circles.length; i++){
            drawMe.add(circles[i]);
        }

        int gridLocation = 0;
        int minLengthLine = 0;
        int maxLengthLine = 0;
        int linePos = 0;
        Line line;
        int maxCycle = 50;
        maxCycle = maxCycle - difficulty*10;

        if (width > height){
            gridSize = (int)(height - height * percBorder);
            gridLocation = (int)(width/2.0 - gridSize/2.0);
            minLengthLine = (int)(height * percBorder / 2);
            maxLengthLine = (int)(height - height * percBorder / 2);
            linePos = gridLocation/3;
            line = new Line(linePos,linePos,minLengthLine,maxLengthLine,new int[]{255, 243, 142, 150});
        } else {
            gridSize = (int)(width - width * percBorder);
            gridLocation = (int)(height/2.0 - gridSize/2.0);
            minLengthLine = (int)(width * percBorder / 2);
            maxLengthLine = (int)(width - width * percBorder / 2);
            linePos = gridLocation/3;
            line = new Line(minLengthLine,maxLengthLine,linePos,linePos,new int[]{255, 243, 142, 150});
        }
        if (line.getX1() == line.getX2()){
            line.setY1(line.getY1() + ((maxLengthLine / maxCycle) / 2)*remainingFrames);
            line.setY2(line.getY2() - ((maxLengthLine / maxCycle) / 2)*remainingFrames);
        } else {
            line.setX1(line.getX1() + ((maxLengthLine / maxCycle) / 2)*remainingFrames);
            line.setX2(line.getX2() - ((maxLengthLine / maxCycle) / 2)*remainingFrames);
        }

        drawMe.add(line);
        drawMe.add(text);
        return drawMe;
    }

    /**
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return null
     */
    public Vector<DrawableObj> tap( int x, int y)  { sm.toMenu(); return drawMe; }

    /**
     *
     * @return null
     */
    public Vector<DrawableObj> tick()  { return drawMe; }

    /**
     * used for saving the state
     *
     * @param bundle THE Bundle bundle
     * @return bundle
     */
    public Bundle save(Bundle bundle) { return bundle; }

    /**
     *
     * @param width width of screen
     * @param height height of screen
     * @param bundle THE Bundle bundle
     * @return null
     */
    public Vector<DrawableObj> load(int width, int height, Bundle bundle) { return null; }

    /**
     *
     * @param x x coordinate
     */
    public void setVar(int []x){
        difficulty = x[0];
        remainingFrames = x[1]/50;


    }
}
