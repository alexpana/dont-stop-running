package org.vertexarmy.dsr.atlas_viewer.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import org.vertexarmy.dsr.atlas_viewer.TextureAtlas;

/**
 * created by Alex
 * on 3/14/2015.
 */
public class RegionDetailsPanel extends JPanel {
    private final JLabel positionLabel;
    private final JLabel sizeLabel;

    public RegionDetailsPanel() {
        setPreferredSize(new Dimension(100, 100));

        positionLabel = new JLabel("N/A");
        sizeLabel = new JLabel("N/A");

        initLayout();
    }

    private void initLayout() {
        setLayout(new MigLayout("wrap 2"));

        addKeyLabel("Offset");
        add(positionLabel);
        addKeyLabel("Size");
        add(sizeLabel);
    }

    private void addKeyLabel(String keyName) {
        JLabel label = new JLabel(keyName);
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        add(label, "width 60:60:60");
    }

    public void clearRegion() {
        positionLabel.setText("N/A");
        sizeLabel.setText("N/A");
    }

    public void setRegion(TextureAtlas.Region region) {
        positionLabel.setText((int)region.x + ", " + (int)region.y);
        sizeLabel.setText((int)region.w + ", " + (int)region.h);
    }
}
