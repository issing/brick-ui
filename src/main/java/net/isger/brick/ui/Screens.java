package net.isger.brick.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.isger.util.Helpers;
import net.isger.util.Strings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Screens {

    private static final Logger LOG;

    private Map<String, Screen> screens;

    static {
        LOG = LoggerFactory.getLogger(Screens.class);
    }

    public Screens() {
        this(null);
    }

    @SuppressWarnings("unchecked")
    public Screens(List<Object> screens) {
        this.screens = new HashMap<String, Screen>();
        if (screens != null) {
            for (Object instance : screens) {
                if (instance instanceof Screen) {
                    add((Screen) instance);
                } else if (instance instanceof Map) {
                    for (Entry<String, Object> entry : ((Map<String, Object>) instance)
                            .entrySet()) {
                        instance = entry.getValue();
                        if (instance instanceof Screen) {
                            put(entry.getKey(), (Screen) instance);
                        }
                    }
                }
            }
        }
    }

    public void add(Screen screen) {
        put("", screen);
    }

    public void put(String name, Screen screen) {
        int index = name.lastIndexOf(".");
        String key;
        if (index++ > 0) {
            key = name.substring(0, index);
            name = name.substring(index);
        } else {
            key = "";
        }
        key += getName(screen.getClass(), name);
        if (LOG.isDebugEnabled()) {
            LOG.info("Binding [{}] screen [{}]", key, screen);
        }
        screen = screens.put(key, screen);
        if (screen != null) {
            LOG.warn("(!) Discard [{}] screen [{}]", key, screen);
        }
    }

    public Screen get(String name) {
        return screens.get(name);
    }

    public static final String getName(Class<? extends Screen> clazz) {
        return getName(clazz, "");
    }

    public static final String getName(Class<? extends Screen> clazz,
            String name) {
        return Helpers.getAliasName(clazz, "Screen$", Strings.toLower(name));
    }

}
