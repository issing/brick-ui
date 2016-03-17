package net.isger.brick.plugin;

import net.isger.brick.inject.Scope;
import net.isger.brick.inject.anno.Scoped;
import net.isger.util.anno.Alias;

@Alias("ui-plugin")
@Scoped(Scope.SINGLETON)
public class UIPluginModule extends PluginModule {

    public Class<? extends UIPlugin> getImplementClass() {
        return UIPlugin.class;
    }

}
