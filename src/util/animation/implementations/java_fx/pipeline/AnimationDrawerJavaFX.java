package util.animation.implementations.java_fx.pipeline;

import javafx.geometry.Bounds;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import util.animation.implementations.java_fx.display.AnimationCanvas;
import util.animation.pipeline.AnimationDrawer;
import util.animation.util.AEColor;
import util.animation.util.AEPoint;
import util.animation.util.AERect;

public class AnimationDrawerJavaFX extends AnimationDrawer {
	public AnimationCanvas p=null;

    @Override protected AERect getPanelBoundsOnScreen() {
		Bounds panelBoundsOnScene = p.getBoundsInLocal();
		Bounds panelBoundsOnScreen = p.localToScreen(panelBoundsOnScene);
		if(panelBoundsOnScreen==null) {
			if(panelBoundsOnScene==null)
				return new AERect(0, 0, 1920, 1080);
			else
				return new AERect(panelBoundsOnScene.getMinX(), panelBoundsOnScene.getMinY(), panelBoundsOnScene.getWidth(), panelBoundsOnScene.getHeight());
		} else
			return new AERect(panelBoundsOnScreen.getMinX(), panelBoundsOnScreen.getMinY(), panelBoundsOnScreen.getWidth(), panelBoundsOnScreen.getHeight());
	}


    @Override public void drawImage(Object param, AERect drawB) {
        GraphicsContext gc = p.getGraphicsContext2D();
		Image ip_img = (Image) param;
        gc.drawImage(ip_img, drawB.x, drawB.y, drawB.getWidth(), drawB.getHeight());//scaled drawing not tested...
    }

    @Override public void drawLine(AEColor param, AEPoint p1, AEPoint p2) {
        GraphicsContext gc = p.getGraphicsContext2D();
	    Color colorToDraw = param==null? Color.AQUA:Color.rgb(param.getRed(), param.getGreen(), param.getBlue());
		gc.setStroke(colorToDraw);
		gc.strokeLine(p1.x, p1.y, p2.x, p2.y);
		gc.strokeLine(p1.x+1, p1.y+1, p2.x+1, p2.y+1);//a really stupid way to do stroke width == 2
    }

    @Override public void fillOval(AEColor param, AERect drawB) {
        GraphicsContext gc = p.getGraphicsContext2D();
        Color colorToDraw = param==null? Color.AQUA:Color.rgb(param.getRed(), param.getGreen(), param.getBlue());
        gc.setFill(colorToDraw);
		gc.fillOval(drawB.x, drawB.y, drawB.getWidth(), drawB.getHeight());
    }

    @Override public void fillRect(AEColor param, AERect drawB) {
        GraphicsContext gc = p.getGraphicsContext2D();
        Color colorToDraw = param==null? Color.AQUA:Color.rgb(param.getRed(), param.getGreen(), param.getBlue());
        gc.setFill(colorToDraw);
		gc.fillRect(drawB.x, drawB.y, drawB.getWidth(), drawB.getHeight());
    }

    @Override public void drawRect(AEColor param, AERect drawB) {
        GraphicsContext gc = p.getGraphicsContext2D();
        Color colorToDraw = param==null? Color.AQUA:Color.rgb(param.getRed(), param.getGreen(), param.getBlue());
        gc.setStroke(colorToDraw);
        gc.strokeRect(drawB.x, drawB.y, drawB.getWidth(), drawB.getHeight());
    }

    @Override public double drawString(AEColor param, double font_size, String str, double mid_x, double mid_y) {
        GraphicsContext gc = p.getGraphicsContext2D();
        Color colorToDraw = param==null? Color.AQUA:Color.rgb(param.getRed(), param.getGreen(), param.getBlue());
        gc.setFill(colorToDraw);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(new Font("Arial", font_size));
        gc.fillText(str, mid_x, mid_y);

        return font_size;//TODO this may technically be bullshit
    }

    @Override public void drawString(AEColor param, String str, AERect drawB) {
        GraphicsContext gc = p.getGraphicsContext2D();
        Color colorToDraw = param==null? Color.AQUA:Color.rgb(param.getRed(), param.getGreen(), param.getBlue());
        gc.setFill(colorToDraw);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(new Font("Arial", 100));
        gc.fillText(str, drawB.x + drawB.getWidth()/2, drawB.y + drawB.getHeight()/2, drawB.getWidth());
    }

    @Override public void drawOval(AEColor param, AERect drawB) {
        GraphicsContext gc = p.getGraphicsContext2D();
        Color colorToDraw = param==null? Color.AQUA:Color.rgb(param.getRed(), param.getGreen(), param.getBlue());
        gc.setStroke(colorToDraw);
        gc.strokeOval(drawB.x, drawB.y, drawB.getWidth(), drawB.getHeight());
    }

    @Override public void drawHalfOval(AEColor param, AERect aeRect, int openDirection) {
        GraphicsContext gc = p.getGraphicsContext2D();
        Color colorToDraw = param==null? Color.AQUA:Color.rgb(param.getRed(), param.getGreen(), param.getBlue());
        gc.setStroke(colorToDraw);
        if(openDirection==0) {
            gc.strokeArc(aeRect.x, aeRect.y, aeRect.w, aeRect.h, 180, 180, ArcType.OPEN);
        } else if(openDirection==1) {
            gc.strokeArc(aeRect.x, aeRect.y, aeRect.w, aeRect.h, 0, 180, ArcType.OPEN);
        } else if(openDirection==2) {
            gc.strokeArc(aeRect.x, aeRect.y, aeRect.w, aeRect.h, 90, 180, ArcType.OPEN);
        } else if(openDirection==3) {
            gc.strokeArc(aeRect.x, aeRect.y, aeRect.w, aeRect.h, 90, -180, ArcType.OPEN);
        }
    }

    @Override public void fillHalfOval(AEColor param, AERect aeRect, int openDirection) {
        GraphicsContext gc = p.getGraphicsContext2D();
        Color colorToDraw = param==null? Color.AQUA:Color.rgb(param.getRed(), param.getGreen(), param.getBlue());
        gc.setFill(colorToDraw);
        if(openDirection==0) {
            gc.fillArc(aeRect.x, aeRect.y, aeRect.w, aeRect.h, 180, 180, ArcType.OPEN);
        } else if(openDirection==1) {
            gc.fillArc(aeRect.x, aeRect.y, aeRect.w, aeRect.h, 0, 180, ArcType.OPEN);
        } else if(openDirection==2) {
            gc.fillArc(aeRect.x, aeRect.y, aeRect.w, aeRect.h, 90, 180, ArcType.OPEN);
        } else if(openDirection==3) {
            gc.fillArc(aeRect.x, aeRect.y, aeRect.w, aeRect.h, 90, -180, ArcType.OPEN);
        }
    }

    @Override public void fillTriangle(AEColor param, AERect aeRect) {
        GraphicsContext gc = p.getGraphicsContext2D();
        Color colorToDraw = param==null? Color.AQUA:Color.rgb(param.getRed(), param.getGreen(), param.getBlue());
        gc.setFill(colorToDraw);

        double[] xp = { aeRect.x, aeRect.x + aeRect.w,  aeRect.x + aeRect.w/2};
        double[] yp = { aeRect.y + aeRect.h/4, aeRect.y + aeRect.h/4,  aeRect.y + aeRect.h};
        gc.fillPolygon(xp, yp, xp.length);
    }
}