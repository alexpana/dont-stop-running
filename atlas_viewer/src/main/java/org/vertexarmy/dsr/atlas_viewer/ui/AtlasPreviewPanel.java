package org.vertexarmy.dsr.atlas_viewer.ui;

import org.vertexarmy.dsr.atlas_viewer.TextureAtlas;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * created by Alex
 * on 3/14/2015.
 */
public class AtlasPreviewPanel extends JPanel {
    private static final Color BG_COLOR = new Color(0x623335);
    public static final Color HIGHLIGHT_COLOR_A = new Color(200, 200, 200);
    public static final Color HIGHLIGHT_COLOR_B = new Color(20, 20, 20);

    private TextureAtlas textureAtlas;

    private float zoom = 3.0f;
    private TextureAtlas.Region region;

    public void setTextureAtlas(TextureAtlas atlas) {
        textureAtlas = atlas;
        setPreferredSize(new Dimension((int) (atlas.getImage().getWidth() * zoom), (int) (atlas.getImage().getWidth() * zoom)));
        repaint();
    }

    public void clearHighlightedRegion() {
        this.region = null;
        repaint();
    }

    public void setHighlightedRegion(TextureAtlas.Region region) {
        this.region = region;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(BG_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (textureAtlas != null) {
            BufferedImage texture = textureAtlas.getImage();

            Graphics2D g2d = (Graphics2D) g;
            g2d.setTransform(AffineTransform.getScaleInstance(zoom, zoom));

            int xi = (int) ((getWidth() / zoom - texture.getWidth()) / 2);
            int yi = (int) ((getHeight() / zoom - texture.getHeight()) / 2);
            g2d.drawImage(texture, xi, yi, null);

            g2d.setTransform(AffineTransform.getScaleInstance(1, 1));

            if (region != null) {
                int xr = (int) (((getWidth() / zoom - texture.getWidth()) / 2 + region.x) * zoom) - 1;
                int yr = (int) (((getHeight() / zoom - texture.getHeight()) / 2 + region.y) * zoom) - 1;
                int wr = (int) ((region.w) * zoom) + 1;
                int hr = (int) ((region.h) * zoom) + 1;

                g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1, new float[]{4, 4}, 0));
                g2d.setColor(HIGHLIGHT_COLOR_A);
                g2d.drawRect(xr, yr, wr, hr);
                g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1, new float[]{4, 4}, 4));
                g2d.setColor(HIGHLIGHT_COLOR_B);
                g2d.drawRect(xr, yr, wr, hr);

                g2d.setColor(new Color(150, 210, 190, 80));
                g2d.fillRect(xr, yr, wr, hr);
            }
        }
    }
}
