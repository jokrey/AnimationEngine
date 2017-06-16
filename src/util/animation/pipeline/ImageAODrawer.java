package util.animation.pipeline;

import util.animation.util.AEColor;
import util.animation.util.AERect;

/**
 * TODO: This entire class is useless as is. But may be interesting in the future.
 */
public class ImageAODrawer extends AnimationObjectDrawer {
	@Override public boolean canDraw(AnimationObject o, Object param) {
		return false;
	}
	@Override public void draw(AnimationObject o, AnimationPipeline pipeline, Object param) {
		AERect drawB = pipeline.getDrawBoundsFor(o);
		if(param!=null) {
			pipeline.getDrawer().drawImage(param, drawB);
		} else {
			pipeline.getDrawer().fillRect(new AEColor(255,0,0,0), drawB);
		}
	}
}