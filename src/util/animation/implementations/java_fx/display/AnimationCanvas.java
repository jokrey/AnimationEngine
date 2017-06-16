package util.animation.implementations.java_fx.display;


import com.sun.javafx.geom.Point2D;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.*;
import util.animation.AnimationHandler;
import util.animation.engine.AnimationEngine;
import util.animation.implementations.java_fx.pipeline.AnimationDrawerJavaFX;
import util.animation.pipeline.AnimationPipeline;
import util.animation.util.AEPoint;
import util.animation.util.AERect;

import java.awt.*;

public class AnimationCanvas extends Canvas {
	public AnimationHandler handler;
	public void start() {
		handler.start();
	}
	public AnimationCanvas(AnimationEngine engineToRun, AnimationPipeline pipeline) {
		this.handler = new AnimationHandler(engineToRun, pipeline) {
			@Override public void draw() {
                redraw();
			}
		};
		((AnimationDrawerJavaFX)handler.getPipeline().getDrawer()).p = this;
        new Thread(handler).start();

        final KeyCombination keyComb_strg_x = new KeyCodeCombination(KeyCode.X,
                KeyCombination.CONTROL_DOWN);
        final KeyCombination keyComb_strg_y = new KeyCodeCombination(KeyCode.Y,
                KeyCombination.CONTROL_DOWN);
        final KeyCombination keyComb_strg_a = new KeyCodeCombination(KeyCode.A,
                KeyCombination.CONTROL_DOWN);
        setOnMouseDragged(mouseDragEventHandler);
        setOnMousePressed(mousePressedEventHandler);
        setOnMouseClicked(mouseClickedEventHandler);
        setOnScroll(mouseScrollEventHandler);


        setFocusTraversable(true);
        setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent event) {
                if(event.getText().length()>=1)
                    handler.getEngine().keyPressed(event.getText().charAt(0));

                if (keyComb_strg_x.match(event)) {
                    handler.pause();
                } else if (keyComb_strg_y.match(event)) {
                    handler.on();
                } else if (keyComb_strg_a.match(event)) {
                    handler.getEngine().initiate();
                }
            }
        });
        setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent event) {
                if(event.getText().length()>=1)
                    handler.getEngine().keyReleased(event.getText().charAt(0));
            }
        });
	}

    Point2D oldXY_OnScreen = new Point2D();
    EventHandler<MouseEvent> mouseDragEventHandler = new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent event) {
            if(event.isPrimaryButtonDown()) {
                if (handler.getPipeline().userDrawBoundsMidOverride == null) {
                    AERect drawBnds = handler.getPipeline().getDrawBounds(handler.getEngine());
                    handler.getPipeline().userDrawBoundsMidOverride = handler.getPipeline().convertFromPixelPoint(new AEPoint(drawBnds.x + drawBnds.getWidth() / 2, drawBnds.y + drawBnds.getHeight() / 2));
                }
                double xOnScreen_scaled = event.getScreenX();
                double yOnScreen_scaled = event.getScreenY();
                Point2D convert = new Point2D((float) (xOnScreen_scaled - oldXY_OnScreen.x), (float) (yOnScreen_scaled - oldXY_OnScreen.y));
                oldXY_OnScreen.setLocation((float)xOnScreen_scaled, (float) yOnScreen_scaled);
                handler.getPipeline().userDrawBoundsMidOverride.setLocation(
                        handler.getPipeline().userDrawBoundsMidOverride.x - convert.x/handler.getPipeline().squareEqualsPixels,
                        handler.getPipeline().userDrawBoundsMidOverride.y - convert.y/handler.getPipeline().squareEqualsPixels
                );
            }
        }
    };
    EventHandler<MouseEvent> mousePressedEventHandler = new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent event) {
            oldXY_OnScreen.setLocation((float)event.getScreenX(), (float) event.getScreenY());
            if(!event.isPrimaryButtonDown()) {
                handler.getPipeline().resetDrawBounds(handler.getEngine());
                handler.getPipeline().userDrawBoundsMidOverride = null;
            }
        }
    };
    EventHandler<MouseEvent> mouseClickedEventHandler = new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent event) {
            handler.getEngine().mouseClicked(event.getButton().ordinal());
        }
    };
    EventHandler<ScrollEvent> mouseScrollEventHandler = new EventHandler<ScrollEvent>() {
        @Override public void handle(ScrollEvent event) {
            if(event.getDeltaY()>0) {
                handler.zoomIn();
            } else if(event.getDeltaY()<0) {
                handler.zoomOut();
            }
        }
    };



	private void redraw() {
		if(handler.getPipeline()==null || (!handler.getPipeline().canDraw() && !handler.getEngine().isPaused()))return;

        AEPoint mouseP = handler.getPipeline().convertFromScreenPoint(new AEPoint(MouseInfo.getPointerInfo().getLocation().x,MouseInfo.getPointerInfo().getLocation().y));
        handler.getEngine().locationInputChanged(mouseP);
		handler.getPipeline().draw(handler.getEngine().getAllObjectsToDraw(), handler.getEngine(), true);
	}
}