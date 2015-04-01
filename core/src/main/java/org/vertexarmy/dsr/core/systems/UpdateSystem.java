package org.vertexarmy.dsr.core.systems;

import com.beust.jcommander.internal.Lists;
import org.vertexarmy.dsr.core.Log;
import org.vertexarmy.dsr.core.component.UpdateComponent;

import java.util.List;

/**
 * Created by alex
 * on 01.04.2015.
 */
public class UpdateSystem {
    private static final UpdateSystem INSTANCE = new UpdateSystem();

    private final Log log = Log.create();

    private final List<UpdateComponent> components = Lists.newArrayList();

    public static UpdateSystem instance() {
        return INSTANCE;
    }

    private UpdateSystem() {
    }

    public void initialize() {
        log.info("Initialized ok");
    }

    public void addUpdateComponent(UpdateComponent component) {
        components.add(component);
    }

    public void removeUpdateComponent(UpdateComponent component) {
        components.remove(component);
    }

    public void update() {
        for (UpdateComponent component : components) {
            component.update();
        }
    }
}
