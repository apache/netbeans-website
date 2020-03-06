package org.netbeans.paint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

/**
 *
 * @author Tim Boudreau
 */
public class PaintCanvas extends JComponent {
    private int brushDiameter = 10;
    private final MouseL mouseListener = new MouseL();
    private BufferedImage backingImage = null;
    private final BrushSizeView brushView = new BrushSizeView();
    private Color color = Color.BLUE;

    public PaintCanvas() {
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
        setBackground(Color.WHITE);
        setFocusable(true);
    }

    public void setBrush(int diam) {
        this.brushDiameter = diam;
    }

    public void setBrushDiameter(int val) {
        this.brushDiameter = val;
        brushView.repaint();
    }

    public int getBrushDiameter() {
        return brushDiameter;
    }

    public void setColor(Color c) {
        this.color = c;
        brushView.repaint();
    }

    public Color getColor() {
        return color;
    }

    public void clear() {
        backingImage = null;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawRenderedImage(getImage(), AffineTransform.getTranslateInstance(0, 0));
    }

    JComponent getBrushSizeView() {
        return brushView;
    }

    public BufferedImage getImage() {
        int width = Math.min(getWidth(), 1600);
        int height = Math.min(getHeight(), 1200);
        if (backingImage == null || backingImage.getWidth() != width || backingImage.getHeight() != height) {
            int newWidth = backingImage == null ? width : Math.max(width, backingImage.getWidth());
            int newHeight = backingImage == null ? height : Math.max(height, backingImage.getHeight());
            if (newHeight > height && newWidth > width && backingImage != null) {
                return backingImage;
            }
            BufferedImage old = backingImage;
            backingImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB_PRE);
            Graphics2D g = backingImage.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);
            if (old != null) {
                g.drawRenderedImage(old,
                        AffineTransform.getTranslateInstance(0, 0));
            }
            g.dispose();
            setPreferredSize(new Dimension (newWidth, newHeight));
        }
        return backingImage;
    }

    private class BrushSizeView extends JComponent {
        @Override
        public void paint(Graphics g) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            Point p = new Point(getWidth() / 2, getHeight() / 2);
            int half = getBrushDiameter() / 2;
            int diam = getBrushDiameter();
            g.setColor(getColor());
            g.fillOval(p.x - half, p.y - half, diam, diam);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension (24, 24);
        }
    }

    private final class MouseL extends MouseAdapter implements MouseMotionListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            Point p = e.getPoint();
            int half = brushDiameter / 2;
            Graphics2D g = getImage().createGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                               RenderingHints.VALUE_ANTIALIAS_ON);
            g.setPaint(getColor());
            g.fillOval(p.x - half, p.y - half, brushDiameter, brushDiameter);
            g.dispose();
            repaint(p.x - half, p.y - half, brushDiameter, brushDiameter);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            mouseClicked(e);
        }
    }
}
