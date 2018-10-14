package jokrey.utilities.animation.implementations.java_fx.pipeline;

import javafx.geometry.Bounds;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import jokrey.utilities.animation.implementations.java_fx.display.AnimationCanvas;
import jokrey.utilities.animation.util.AEColor;
import jokrey.utilities.animation.util.AERect;
import jokrey.utilities.animation.pipeline.AnimationDrawer;
import jokrey.utilities.animation.util.AEImage;
import jokrey.utilities.animation.util.AEPoint;

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


    @Override public void drawImage(AEImage param, AERect drawB) {
        //TODO: I assume this is slow as -------(professionally bleeped by the Association for Standards, Censorship and Propaganda)
        int w = param.getData().length;
        int h = param.getData()[0].length;
        WritableImage image = new WritableImage(w, h);
        PixelWriter pw = image.getPixelWriter();
        for(int x=0;x<w;x++)
            for(int y=0;y<h;y++)
                pw.setArgb(x,y,param.getData()[x][y]);


        GraphicsContext gc = p.getGraphicsContext2D();
        gc.drawImage(image, drawB.x, drawB.y, drawB.getWidth(), drawB.getHeight());//scaled drawing not tested...
    }

    @Override public void drawLine(AEColor param, AEPoint p1, AEPoint p2, float size) {
        GraphicsContext gc = p.getGraphicsContext2D();
	    Color colorToDraw = param==null? Color.AQUA:Color.rgb(param.getRed(), param.getGreen(), param.getBlue());
		gc.setStroke(colorToDraw);
		gc.strokeLine(p1.x, p1.y, p2.x, p2.y);
		if(size>1) {//a really stupid way to do stroke width == 2
            size--;
		    for(;size>0;size--)
                gc.strokeLine(p1.x+size, p1.y+size, p2.x+size, p2.y+size);
        }
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