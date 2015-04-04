package org.vertexarmy.dsr.atlas_viewer.ui;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * created by Alex
 * on 3/14/2015.
 */
public class LayoutHelper {
    public static JLabel createHeaderLabel(String name) {
        JLabel previewLabel = new JLabel(name);
        previewLabel.setFont(previewLabel.getFont().deriveFont(Font.BOLD));
        previewLabel.setPreferredSize(new Dimension(0, 20));
        previewLabel.setBackground(new Color(192, 199, 207));
        previewLabel.setOpaque(true);
        return previewLabel;
    }

    public static JPanel createNamedPanel(final String name, final Component component) {
        JPanel namedPanel = new JPanel(new BorderLayout());
        namedPanel.add(createHeaderLabel(name), BorderLayout.NORTH);
        namedPanel.add(component, BorderLayout.CENTER);
        return namedPanel;
    }

    public static JPanel createVerticalPanel(Component... components) {
        JPanel result = new JPanel(new MigLayout("wrap 1, ins 0"));
        int i = 0;
        for (Component component : components) {
            String layoutConstraint = "";
            if (i == 0) {
                layoutConstraint = "grow, push";
            } else {
                layoutConstraint = "growx";
            }

            result.add(component, layoutConstraint);
            i += 1;
        }
        return result;
    }
}
