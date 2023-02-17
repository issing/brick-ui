package net.isger.brick.ui;

import net.isger.brick.StandardConstants;
import net.isger.brick.core.Module;
import net.isger.brick.inject.ContainerBuilder;
import net.isger.brick.inject.ContainerProvider;
import net.isger.brick.plugin.UIPluginModule;

public class UIProvider implements ContainerProvider {

    public boolean isReload() {
        return false;
    }

    public void register(ContainerBuilder builder) {
        builder.factory(Module.class, StandardConstants.MOD_PLUGIN, UIPluginModule.class);
        builder.factory(UIDesigner.class, StandardConstants.MOD_PLUGIN);
    }

}
