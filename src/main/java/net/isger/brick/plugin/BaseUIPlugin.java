package net.isger.brick.plugin;

import net.isger.brick.core.GateCommand;
import net.isger.brick.ui.Screen;
import net.isger.brick.ui.Screens;
import net.isger.brick.ui.UICommand;
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

    public void operate(GateCommand cmd) {
        PluginCommand pcmd = (PluginCommand) cmd;
        if (Strings.isEmpty(pcmd.getName()) || !(cmd instanceof UICommand)) {
            super.operate(cmd);
        } else {
            screen((UICommand) pcmd);
        }
    }

    public void screen(UICommand cmd) {
        Screen screen = getScreen(cmd.getName()).clone();
        screen.screen(cmd);
        cmd.setResult(screen);
    }

}