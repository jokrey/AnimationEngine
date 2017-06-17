package util.animation.implementations.swing.display;

import util.animation.AnimationHandler;
import util.animation.engine.AnimationEngine;
import util.animation.implementations.swing.pipeline.AnimationDrawerSwing;
import util.animation.pipeline.AnimationPipeline;
import util.animation.util.AEPoint;
import util.animation.util.AERect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

public class AnimationJPanel extends JPanel {
	public AnimationHandler handler;
	public void start() {
		handler.start();
	}
	public AnimationJPanel(final AnimationEngine engineToRun, AnimationPipeline pipeline) {
		this.handler = new AnimationHandler(engineToRun, pipeline) {
			@Override public void draw() {
				repaint();
			}
		};
		((AnimationDrawerSwing)handler.getPipeline().getDrawer()).p = this;
		new Thread(handler).start();

        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK),"ctrl x");
        getActionMap().put("ctrl x",new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
            	handler.pause();
            }
        });
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK),"ctrl y");
        getActionMap().put("ctrl y",new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
            	handler.on();
            }
        });
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK),"ctrl a");
        getActionMap().put("ctrl a",new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
        		handler.getEngine().initiate();
        	}
        });
        MouseAdapter ma = new MouseAdapter() {
			Point oldXY_OnScreen = new Point();
			@Override public void mouseDragged(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)) {
					if(handler.getPipeline().userDrawBoundsMidOverride==null) {
						AERect drawBnds = handler.getPipeline().getDrawBounds(handler.getEngine());
						if(engineToRun.getDrawerMidOverride()==null)
    						handler.getPipeline().userDrawBoundsMidOverride=handler.getPipeline().convertFromPixelPoint(new AEPoint(drawBnds.x+drawBnds.getWidth()/2, drawBnds.y+drawBnds.getHeight()/2));
						else
                            handler.getPipeline().userDrawBoundsMidOverride=engineToRun.getDrawerMidOverride();
					} else {
                        double xOnScreen_scaled = e.getXOnScreen();
                        double yOnScreen_scaled = e.getYOnScreen();
                        Point2D.Double convert = new Point2D.Double(xOnScreen_scaled - oldXY_OnScreen.x, yOnScreen_scaled - oldXY_OnScreen.y);
                        oldXY_OnScreen.setLocation(xOnScreen_scaled, yOnScreen_scaled);
                        handler.getPipeline().userDrawBoundsMidOverride.setLocation(
                                handler.getPipeline().userDrawBoundsMidOverride.x - convert.x / handler.getPipeline().squareEqualsPixels,
                                handler.getPipeline().userDrawBoundsMidOverride.y - convert.y / handler.getPipeline().squareEqualsPixels
                        );
                    }
	                //System.out.println(handler.getPipeline().userDrawBoundsMidOverride);
				}
			}
			@Override public void mousePressed(MouseEvent e) {
				oldXY_OnScreen.setLocation(e.getXOnScreen(), e.getYOnScreen());
				if(!SwingUtilities.isLeftMouseButton(e))
					handler.getPipeline().userDrawBoundsMidOverride=null;
			}

			@Override public void mouseClicked(MouseEvent me) {
				handler.getEngine().mouseClicked(me.getButton());
				requestFocus();
			}
			@Override public void mouseWheelMoved(MouseWheelEvent mwe) {
				if(mwe.getWheelRotation()<0) {
					handler.zoomIn();
				} else if(mwe.getWheelRotation()>0) {
					handler.zoomOut();
				}
			}
		};
		addMouseListener(ma);
		addMouseMotionListener(ma);
		addMouseWheelListener(ma);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override public boolean dispatchKeyEvent(KeyEvent ke) {
                synchronized (AnimationJPanel.class) {
                    switch (ke.getID()) {
	                    case KeyEvent.KEY_PRESSED:
	                    	handler.getEngine().keyPressed(ke.getKeyChar());
	                        break;
	                    case KeyEvent.KEY_RELEASED:
	                    	try {
		                    	handler.getEngine().keyReleased(ke.getKeyChar());
	                    	} catch(Exception ex) {}
	                        break;
                    }
                    return false;
                }
            }
        });
//		addKeyListener(new KeyAdapter() {
//			@Override public void keyPressed(KeyEvent ke) {
//				handler.keyPressed(ke.getKeyCode());
//			}
//			@Override public void keyReleased(KeyEvent ke) {
//				handler.keyReleased(ke.getKeyCode());
//			}
//		});
	}

	private Graphics2D g;
	@Override protected void paintComponent(Graphics gg) {
		if(handler.getPipeline()==null || (!handler.getPipeline().canDraw() && !handler.getEngine().isPaused()))return;
		super.paintComponent(gg);

        AEPoint mouseP = handler.getPipeline().convertFromScreenPoint(new AEPoint(MouseInfo.getPointerInfo().getLocation().x,MouseInfo.getPointerInfo().getLocation().y), handler.getPipeline().getDrawBounds(handler.getEngine()));
		handler.getEngine().locationInputChanged(mouseP);
		g = (Graphics2D)gg;
		handler.getPipeline().draw(handler.getEngine().getAllObjectsToDraw(), handler.getEngine(), true);
		g = null;
	}
	public Graphics2D getLastGraphics() {
		return g;
	}
}