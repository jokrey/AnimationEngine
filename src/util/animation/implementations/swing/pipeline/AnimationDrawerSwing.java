package util.animation.implementations.swing.pipeline;

import util.animation.implementations.swing.display.AnimationJPanel;
import util.animation.pipeline.AnimationDrawer;
import util.animation.util.AEColor;
import util.animation.util.AEPoint;
import util.animation.util.AERect;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;

public class AnimationDrawerSwing extends AnimationDrawer {
	public AnimationJPanel p=null;

    @Override protected AERect getPanelBoundsOnScreen() {
        return new AERect(p.getLocationOnScreen().x, p.getLocationOnScreen().y, p.getWidth(), p.getHeight());
    }


    @Override public void drawImage(Object param, AERect drawB) {
        BufferedImage ip_img = (BufferedImage) param;
        p.getLastGraphics().drawImage(ip_img, (int)drawB.x, (int)drawB.y, (int)(drawB.x+drawB.getWidth()), (int)(drawB.y+drawB.getHeight()),
                0, 0, ip_img.getWidth(), ip_img.getHeight(), null);
    }

    @Override public void drawLine(AEColor param, AEPoint p1, AEPoint p2) {
        p.getLastGraphics().setColor(param==null?Color.black:new Color(param.getRed(), param.getGreen(), param.getBlue()));
        p.getLastGraphics().setStroke(new BasicStroke(2f));
        p.getLastGraphics().drawLine((int)p1.x, (int)p1.y, (int)p2.x, (int)p2.y);
    }

    @Override public void fillOval(AEColor param, AERect drawB) {
        p.getLastGraphics().setColor(param==null?Color.black:new Color(param.getRed(), param.getGreen(), param.getBlue()));
        p.getLastGraphics().fillOval((int)drawB.x, (int)drawB.y, (int)drawB.getWidth(), (int)drawB.getHeight());
    }

    @Override public void fillRect(AEColor param, AERect drawB) {
        p.getLastGraphics().setColor(param==null?Color.black:new Color(param.getRed(), param.getGreen(), param.getBlue()));
        p.getLastGraphics().fillRect((int)drawB.x, (int)drawB.y, (int)drawB.getWidth(), (int)drawB.getHeight());
    }

    @Override public double drawString(AEColor param, double font_size, String str, double mid_x, double mid_y) {
        p.getLastGraphics().setColor(param==null?Color.black:new Color(param.getRed(), param.getGreen(), param.getBlue()));
        p.getLastGraphics().setFont(new Font("Arial", Font.BOLD, (int) font_size));
        p.getLastGraphics().drawString(str, (int)(mid_x - p.getLastGraphics().getFontMetrics().stringWidth(str)/2), (int)mid_y+p.getLastGraphics().getFontMetrics().getHeight()/4);
        return p.getLastGraphics().getFontMetrics().getHeight();
    }

    @Override public void drawString(AEColor param, String str, AERect rect) {
        p.getLastGraphics().setColor(param==null?Color.black:new Color(param.getRed(), param.getGreen(), param.getBlue()));
        Graphics2D g2 = p.getLastGraphics();
        float fontSize = 20.0f;
        Font font = new Font("Arial", Font.BOLD, (int) 1000);
        font = g2.getFont().deriveFont(fontSize);
        int width = g2.getFontMetrics(font).stringWidth(str);
        fontSize = (float) ((rect.getWidth() / width ) * fontSize);
        font = g2.getFont().deriveFont(fontSize);
        FontRenderContext context = g2.getFontRenderContext();
        g2.setFont(font);//what is this?
        int textWidth = (int) font.getStringBounds(str, context).getWidth();
        LineMetrics ln = font.getLineMetrics(str, context);
        int textHeight = (int) (ln.getAscent() + ln.getDescent());
        int x1 = (int) (rect.x + (rect.getWidth() - textWidth)/2);
        int y1 = (int)(rect.y + (rect.getHeight() + textHeight)/2 - ln.getDescent());

        g2.drawString(str, (int) x1, (int) y1);
    }

    @Override public void drawOval(AEColor param, AERect drawB) {
        p.getLastGraphics().setColor(param==null?Color.black:new Color(param.getRed(), param.getGreen(), param.getBlue()));
        p.getLastGraphics().drawOval((int)drawB.x, (int)drawB.y, (int)drawB.getWidth(), (int)drawB.getHeight());
    }

    @Override public void drawRect(AEColor param, AERect drawB) {
        p.getLastGraphics().setColor(param==null?Color.black:new Color(param.getRed(), param.getGreen(), param.getBlue()));
        p.getLastGraphics().drawRect((int)drawB.x, (int)drawB.y, (int)drawB.getWidth(), (int)drawB.getHeight());
    }

    @Override public void drawHalfOval(AEColor param, AERect aeRect, int openDirection) {
        p.getLastGraphics().setColor(param==null?Color.black:new Color(param.getRed(), param.getGreen(), param.getBlue()));
        if(openDirection==0) {
            p.getLastGraphics().draw(new Arc2D.Double(aeRect.x, aeRect.y, aeRect.w, aeRect.h, 180, 180, Arc2D.OPEN));
        } else if(openDirection==1) {
            p.getLastGraphics().draw(new Arc2D.Double(aeRect.x, aeRect.y, aeRect.w, aeRect.h, 0, 180, Arc2D.OPEN));
        } else if(openDirection==2) {
            p.getLastGraphics().draw(new Arc2D.Double(aeRect.x, aeRect.y, aeRect.getWidth(), aeRect.getHeight(), 90, 180, Arc2D.OPEN));
        } else if(openDirection==3) {
            p.getLastGraphics().draw(new Arc2D.Double(aeRect.x, aeRect.y, aeRect.getWidth(), aeRect.getHeight(), 90, -180, Arc2D.OPEN));
        }
    }
    @Override public void fillHalfOval(AEColor param, AERect aeRect, int openDirection) {
        p.getLastGraphics().setColor(param==null?Color.black:new Color(param.getRed(), param.getGreen(), param.getBlue()));
        if(openDirection==0) {
            p.getLastGraphics().fill(new Arc2D.Double(aeRect.x, aeRect.y, aeRect.w, aeRect.h, 180, 180, Arc2D.OPEN));
        } else if(openDirection==1) {
            p.getLastGraphics().fill(new Arc2D.Double(aeRect.x, aeRect.y, aeRect.w, aeRect.h, 0, 180, Arc2D.OPEN));
        } else if(openDirection==2) {
            p.getLastGraphics().fill(new Arc2D.Double(aeRect.x, aeRect.y, aeRect.getWidth(), aeRect.getHeight(), 90, 180, Arc2D.OPEN));
        } else if(openDirection==3) {
            p.getLastGraphics().fill(new Arc2D.Double(aeRect.x, aeRect.y, aeRect.getWidth(), aeRect.getHeight(), 90, -180, Arc2D.OPEN));
        }
    }

    @Override public void fillTriangle(AEColor param, AERect aeRect) {
        p.getLastGraphics().setColor(param==null?Color.black:new Color(param.getRed(), param.getGreen(), param.getBlue()));
        int[] xp = { (int)aeRect.x, (int)(aeRect.x + aeRect.w), (int) (aeRect.x + aeRect.w/2)};
        int[] yp = { (int)(aeRect.y + aeRect.h/4), (int)(aeRect.y + aeRect.h/4), (int) (aeRect.y + aeRect.h)};
        p.getLastGraphics().fillPolygon(xp, yp, xp.length);
    }
}