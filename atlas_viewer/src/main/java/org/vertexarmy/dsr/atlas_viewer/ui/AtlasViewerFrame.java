package org.vertexarmy.dsr.atlas_viewer.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import lombok.Getter;
import org.vertexarmy.dsr.atlas_viewer.AtlasLoader;
import org.vertexarmy.dsr.atlas_viewer.TextureAtlas;

/**
 * created by Alex
 * on 3/14/2015.
 */
public class AtlasViewerFrame extends JFrame {

    @Getter
    private AtlasViewerModel model = new AtlasViewerModel();

    private AtlasPreviewPanel atlasPreviewPanel;

    private JList<String> regionList;

    private RegionDetailsPanel regionDetailsPanel;

    private JMenuBar menuBar;
    private JMenuItem openMenuItem;

    public AtlasViewerFrame() {
        setSize(800, 800);
        setTitle("Atlas Viewer");

        initComponents();
        initLayout();
        initListeners();
    }

    private void initListeners() {
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showOpenDialog(AtlasViewerFrame.this) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    model.setTextureAtlas(AtlasLoader.load(file));
                }
            }
        });

        model.registerListener(new AtlasViewerModel.Listener() {
            @Override
            public void modelChanged() {
                atlasPreviewPanel.setTextureAtlas(model.getTextureAtlas());
            }
        });

        regionList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && !regionList.isSelectionEmpty()) {
                    TextureAtlas.Region region = model.getTextureAtlas().getRegionMap().get(regionList.getSelectedValue());
                    regionDetailsPanel.setRegion(region);
                    atlasPreviewPanel.setHighlightedRegion(region);
                } else {
                    regionDetailsPanel.clearRegion();
                    atlasPreviewPanel.clearHighlightedRegion();
                }
            }
        });
    }

    private void initComponents() {
        regionList = new JList<>();
        regionList.setModel(model.getRegionListModel());
        regionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        atlasPreviewPanel = new AtlasPreviewPanel();

        menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        openMenuItem = new JMenuItem("Open");
        fileMenu.add(openMenuItem);

        menuBar.add(fileMenu);

        regionDetailsPanel = new RegionDetailsPanel();
    }

    private void initLayout() {
        Container content = getContentPane();

        content.setLayout(new BorderLayout(2, 2));

        JPanel sidePanel = LayoutHelper.createVerticalPanel(
                LayoutHelper.createNamedPanel("Regions", new JScrollPane(regionList)),
                LayoutHelper.createNamedPanel("Region Details", regionDetailsPanel));
        sidePanel.setPreferredSize(new Dimension(150, 0));

        JPanel previewPanel = LayoutHelper.createNamedPanel("Preview", atlasPreviewPanel);

        content.add(previewPanel, BorderLayout.CENTER);
        content.add(sidePanel, BorderLayout.EAST);

        setJMenuBar(menuBar);
    }
}
