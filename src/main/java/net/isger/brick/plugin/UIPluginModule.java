package net.isger.brick.plugin;

import net.isger.brick.core.Gate;
import net.isger.brick.inject.Scope;
import net.isger.brick.ui.UI;
import net.isger.brick.util.anno.Scoped;
import net.isger.util.anno.Alias;

/**
 * 交互插件模块
 * 
 * @author issing
 *
 */
@Alias("ui-plugin")
@Scoped(Scope.SINGLETON)
public class UIPluginModule extends PluginModule {

    /**
     * 交互插件目标类型
     */
    public Class<? extends Gate> getTargetClass() {
        return UIPlugin.class;
    }

    /**
     * 交互插件实现类型
     */
    public Class<? extends Gate> getBaseClass() {
        return BaseUIPlugin.class;
    }

    public Gate getGate(String name) {
        Gate gate = super.getGate(name);
        if (gate == null) {
            /* 查找交互模块（互通） */
            gate = container.getInstance(UI.class, name);
            if (!(gate instanceof Plugin)) {
                gate = null;
            }
        }
        return gate;
    }
}
