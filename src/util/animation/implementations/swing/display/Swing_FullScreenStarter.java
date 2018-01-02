package util.animation.implementations.swing.display;

import util.animation.implementations.swing.pipeline.AnimationDrawerSwing;
import util.animation.engine.AnimationEngine;
import util.animation.pipeline.AnimationPipeline;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Swing_FullScreenStarter {
    public static JFrame start(AnimationEngine engineToRun_g) {
        return start(engineToRun_g, true);
    }
    public static JFrame start(AnimationEngine engineToRun_g, boolean immediatlysetvisibletrue) {
        return start(engineToRun_g, new AnimationPipeline(new AnimationDrawerSwing()), immediatlysetvisibletrue);
    }
    public static JFrame start(AnimationEngine engineToRun, AnimationPipeline pipe_g) {
        return start(engineToRun, pipe_g, true);
    }
	public static JFrame start(AnimationEngine engineToRun, AnimationPipeline pipe_g, boolean immediatlysetvisibletrue) {
        if(! (pipe_g.getDrawer() instanceof AnimationDrawerSwing))
            throw new IllegalArgumentException("Swing needs the swing drawer you f***");

		JFrame f = new JFrame();

		AnimationJPanel ap = new AnimationJPanel(engineToRun, pipe_g);
		ap.setBackground(Color.red);
		f.add(ap, BorderLayout.CENTER);

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setUndecorated(true);
		f.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		centerOnMouseScreen(f);
		if(immediatlysetvisibletrue)
    		f.setVisible(true);
		ap.start();

		return f;
	}

    public static Point centerOnMouseScreen(JFrame frame) {
    	frame.setLocation(getLocForACenteredInRect(getScreenBoundsAt(MouseInfo.getPointerInfo().getLocation()), frame.getSize()));
    	return frame.getLocation();
    }
    public static Rectangle getScreenBoundsAt(Point pos) {
        GraphicsDevice gd = getGraphicsDeviceAt(pos);
        Rectangle bounds = null;

        if (gd != null)
            bounds = gd.getDefaultConfiguration().getBounds();
        return bounds;
    }
    public static Point getLocForACenteredInRect(Rectangle r, Dimension size) {
    	return new Point(r.x + r.width /2 - size.width /2, 
    				     r.y + r.height/2 - size.height/2);
    }
    //FROM: http://stackoverflow.com/questions/11714215/detect-current-screen-bounds/11714302#11714302
    public static GraphicsDevice getGraphicsDeviceAt(Point pos) {
        GraphicsDevice device = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice lstGDs[] = ge.getScreenDevices();
        ArrayList<GraphicsDevice> lstDevices = new ArrayList<>(lstGDs.length);

        for (GraphicsDevice gd : lstGDs) {
            GraphicsConfiguration gc = gd.getDefaultConfiguration();
            Rectangle screenBounds = gc.getBounds();
            if (screenBounds.contains(pos)) {
                lstDevices.add(gd);
            }
        }

        if (lstDevices.size() == 1) {
            device = lstDevices.get(0);
        }
        return device;
    }
}
