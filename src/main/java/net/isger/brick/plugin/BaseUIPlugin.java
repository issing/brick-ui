package net.isger.brick.plugin;

import net.isger.brick.core.GateCommand;
import net.isger.brick.ui.Screen;
import net.isger.brick.ui.Screens;
import net.isger.brick.ui.UICommand;
import net.isger.util.Asserts;
import net.isger.util.Strings;

/**
 * 交互插件
 * 
 * @author issing
 *
 */
public class BaseUIPlugin extends BasePlugin implements UIPlugin {

    private Screens screens;

    public BaseUIPlugin() {
        screens = new Screens();
    }

    protected final Screen getScreen(String name) {
        return screens.get(name);
    }

    public void initial(PluginCommand cmd) {
        super.initial(cmd);
        for (Screen screen : screens.gets().values()) {
            container.inject(screen);
        }
    }

    public void operate(GateCommand cmd) {
        PluginCommand pcmd = (PluginCommand) cmd;
        if (Strings.isEmpty(pcmd.getName()) || !(cmd instanceof UICommand)) {
            super.operate(cmd);
        } else {
            screen((UICommand) pcmd);
        }
    }

    public void screen(UICommand cmd) {
        String name = cmd.getName();
        Screen screen = getScreen(name);
        Asserts.isNotNull(screen, "Unfound the specified screen [%s] in the [%s] Plugin [%s], Check whether it is configured in the brick configuration file", name, cmd.getDomain(), this.getClass().getName());
        (screen = screen.clone()).screen(cmd);
        cmd.setResult(screen);
    }

}