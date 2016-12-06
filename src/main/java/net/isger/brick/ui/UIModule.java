package net.isger.brick.ui;

import net.isger.brick.StandardConstants;
import net.isger.brick.core.Gate;
import net.isger.brick.core.GateModule;
import net.isger.brick.inject.Scope;
import net.isger.brick.plugin.PluginModule;
import net.isger.brick.util.anno.Scoped;

/**
 * 交互模块
 * 
 * @author issing
 *
 */
@Scoped(Scope.SINGLETON)
public class UIModule extends GateModule {

    private static final String UI = "ui";

    /**
     * 获取交互目标类型
     */
    public Class<? extends UI> getTargetClass() {
        return UI.class;
    }

    /**
     * 获取交互实现类型
     */
    @SuppressWarnings("unchecked")
    public Class<? extends Gate> getImplementClass() {
        Class<? extends Gate> implClass = (Class<? extends Gate>) getImplementClass(
                UI, null);
        if (implClass == null) {
            implClass = super.getImplementClass();
        }
        return implClass;
    }

    /**
     * 获取交互默认类型
     */
    public Class<? extends Gate> getBaseClass() {
        return BaseUI.class;
    }

    public Gate getGate(String name) {
        Gate gate = super.getGate(name);
        if (gate == null) {
            PluginModule module = (PluginModule) console
                    .getModule(StandardConstants.MOD_PLUGIN);
            /* 查找交互插件（互通） */
            gate = module.getGate(name);
            if (!(gate instanceof UI)) {
                gate = null;
            }
        }
        return gate;
    }
}
