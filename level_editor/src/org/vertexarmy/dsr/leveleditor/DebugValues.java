package org.vertexarmy.dsr.leveleditor;

import com.beust.jcommander.internal.Lists;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * created by Alex
 * on 3/23/2015.
 */
public class DebugValues {
    private static final DebugValues INSTANCE = new DebugValues();

    private final Map<String, String> values = new TreeMap<>();

    private final List<Listener> listeners = Lists.newArrayList();

    private DebugValues() {
    }

    public static DebugValues instance() {
        return INSTANCE;
    }

    public void setValue(String key, Object value) {
        values.put(key, String.valueOf(value));
        notifyListeners();
    }

    public String getValue(String key) {
        return values.get(key);
    }

    public void clearValue(String key) {
        values.remove(key);
        notifyListeners();
    }

    public Set<String> keySet() {
        return values.keySet();
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (Listener listener : listeners) {
            listener.valuesChanged();
        }
    }

    public interface Listener {
        void valuesChanged();
    }
}
