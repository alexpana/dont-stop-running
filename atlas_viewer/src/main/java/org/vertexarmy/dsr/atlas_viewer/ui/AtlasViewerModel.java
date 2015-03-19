package org.vertexarmy.dsr.atlas_viewer.ui;

import com.beust.jcommander.internal.Lists;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;
import lombok.Getter;
import org.vertexarmy.dsr.atlas_viewer.TextureAtlas;

/**
 * created by Alex
 * on 3/14/2015.
 */
public class AtlasViewerModel {

    private final DefaultListModel<String> regionListModel = new DefaultListModel<>();

    private List<Listener> listeners = Lists.newArrayList();

    @Getter
    private TextureAtlas textureAtlas;

    DefaultListModel<String> getRegionListModel() {
        return regionListModel;
    }

    public void registerListener(final Listener listener) {
        listeners.add(listener);
    }

    public void unregisterListener(final Listener listener) {
        listeners.remove(listener);
    }

    public void setTextureAtlas(final TextureAtlas atlas) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                textureAtlas = atlas;
                updateModel();
                notifyModelChanged();
            }
        });
    }

    private void updateModel() {
        regionListModel.clear();
        List<String> regions = Lists.newArrayList(textureAtlas.getRegionMap().keySet());
        Collections.sort(regions);
        for (String regionName : regions) {
            regionListModel.addElement(regionName);
        }
    }

    private void notifyModelChanged() {
        for (Listener listener : listeners) {
            listener.modelChanged();
        }
    }

    public interface Listener {
        void modelChanged();
    }
}
