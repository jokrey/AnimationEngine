package util.animation;

import util.animation.engine.AnimationEngine;
import util.animation.pipeline.AnimationPipeline;

import java.util.ConcurrentModificationException;

/**
 * Main Controller interface for the drawer implementations..
 *
 *
 * TODO:
 * - SOUND
 * - DROP TICKS IF TOO FAR BEHIND
 */
public abstract class AnimationHandler implements Runnable {
	private AnimationEngine engine;
	private AnimationPipeline pipeline;
    public AnimationPipeline getPipeline() {
		return pipeline;
	}
    public AnimationEngine getEngine() {
		return engine;
	}

	public abstract void draw();
	public void start() {
		engine.initiate();
		engine.start();
	}
	public void on() {
		engine.start();
	}
	public void pause() {
		engine.pause();
	}
	public void zoomIn() {
		getPipeline().squareEqualsPixels+= getPipeline().squareEqualsPixels*0.02;

		//TODO zoom in on mouse
//		drawBounds=getDrawBounds(possibleDrawAreaSize);
//		keepInRatioP.setLocation(keepInRatioP.x-drawBounds.x, keepInRatioP.y-drawBounds.y);
//		int size = getSize(possibleDrawAreaSize);
//		size = size+sizeChange;
//    	if (size<100) size=100;
//    	if(!new Rectangle(drawBounds.width, drawBounds.height).contains(keepInRatioP))
//    		keepInRatioP=new Point(getSize(possibleDrawAreaSize)/2, getSize(possibleDrawAreaSize)/2);
//        double oldRelativeLoc_x = keepInRatioP.getX()/(getSize(possibleDrawAreaSize)/10000000.0);
//        double oldRelativeLoc_y = keepInRatioP.getY()/(getSize(possibleDrawAreaSize)/10000000.0);
//        double newMouse_x = oldRelativeLoc_x*(size/10000000.0);
//        double newMouse_y = oldRelativeLoc_y*(size /10000000.0);
//        drawBounds.setLocation((int)(getDrawX(possibleDrawAreaSize)+(keepInRatioP.getX()-newMouse_x)), (int) (getDrawY(possibleDrawAreaSize)+(keepInRatioP.getY()-newMouse_y)));
//        drawBounds.setSize(size,size);
	}
	public void zoomOut() {
		getPipeline().squareEqualsPixels-= getPipeline().squareEqualsPixels*0.02;
	}

	public AnimationHandler(AnimationEngine engineToRun, AnimationPipeline pipeline) {
		engine = engineToRun;
		this.pipeline = pipeline;

		Thread engine_p = new Thread(new Runnable() {
			@Override public void run() {
				getPipeline().squareEqualsPixels=engine.getInitialPixelsPerBox();
				while(engine!=null) {
					try {
						engine.calculate();
                        sleep(1);
					} catch(ConcurrentModificationException ex) {
						System.err.println("concs are boring");
					} catch(Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		engine_p.start();
	}

    @Override public void run() {
        while(pipeline !=null) {
            if(getPipeline().canDraw()) {
                draw();
            }
            sleep(1);
        }
    }

    static void sleep(long ms) {
            try {
                Thread.sleep(ms);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    public void kill() {
		engine=null;
		pipeline =null;
	}
}