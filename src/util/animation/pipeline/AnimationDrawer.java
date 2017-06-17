package util.animation.pipeline;

import util.animation.util.AEColor;
import util.animation.util.AEPoint;
import util.animation.util.AERect;

public abstract class AnimationDrawer {
    protected abstract AERect getPanelBoundsOnScreen();

    public abstract void drawLine(AEColor colorToDraw, AEPoint p1, AEPoint p2);
    public abstract void fillRect(AEColor colorToDraw, AERect drawB);
    public abstract void drawRect(AEColor colorToDraw, AERect aeRect);
    public abstract void fillOval(AEColor colorToDraw, AERect drawB);
	public abstract void drawOval(AEColor colorToDraw, AERect aeRect);
	//openDirection==0,up ; openDirection==1,down ; openDirection==2,left ; openDirection==3,right
    public abstract void drawHalfOval(AEColor colorToDraw, AERect aeRect, int openDirection);
    public abstract void fillHalfOval(AEColor colorToDraw, AERect aeRect, int openDirection);
    public abstract void fillTriangle(AEColor colorToDraw, AERect aeRect);
    public abstract void drawImage(Object param, AERect drawB);
    // returns height of drawn string..
    public abstract double drawString(AEColor clr, double font_size, String str, double mid_x, double mid_y);
    public abstract void drawString(AEColor clr, String str, AERect rect);


//	public int getVisibleW() {
//		int visW = (int) ((pixelSize.getW())/squareEqualsPixels);
//		return visW<=0?1:visW;
//	}
//	public int getVisibleH() {
//		int visH= (int) ((pixelSize.getH())/squareEqualsPixels);
//		return visH<=0?1:visH;
//	}
//	public AESize getVisibleS() {
//		return new AESize(getVisibleW(), getVisibleH());
//	}
}