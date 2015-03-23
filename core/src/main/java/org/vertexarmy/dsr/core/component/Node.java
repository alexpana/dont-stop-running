package org.vertexarmy.dsr.core.component;

import com.beust.jcommander.internal.Maps;
import java.util.Map;

/**
 * created by Alex
 * on 3/21/2015.
 */
public class Node {
    private final Map<ComponentType, Object> components = Maps.newHashMap();

    public Node() {
    }

    public Node(ComponentType componentType, Object component) {
        addComponent(componentType, component);
    }

    public boolean hasComponent(ComponentType componentType) {
        return components.containsKey(componentType);
    }

    public Object getComponent(ComponentType componentType) {
        return components.get(componentType);
    }

    public void addComponent(ComponentType componentType, Object component) {
        components.put(componentType, component);
    }

    public void removeComponent(ComponentType componentType) {
        components.remove(componentType);
    }
}
