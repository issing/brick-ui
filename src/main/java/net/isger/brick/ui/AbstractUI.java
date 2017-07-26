package net.isger.brick.ui;

import net.isger.brick.core.BaseGate;
import net.isger.brick.core.GateCommand;
import net.isger.brick.plugin.PluginCommand;
import net.isger.util.Asserts;
import net.isger.util.Strings;

public abstract class AbstractUI extends BaseGate implements UI {

    protected abstract Screen getScreen(String name);

    public void operate(GateCommand cmd) {
        PluginCommand pcmd = (PluginCommand) cmd;
        if (Strings.isEmpty(pcmd.getName())) {
            super.operate(cmd);
        } else {
            screen((UICommand) pcmd);
        }
    }

    public void screen(UICommand cmd) {
        String name = cmd.getName();
        Screen screen = getScreen(name);
        Asserts.isNotNull(
                screen,
                "Unfound the specified screen [%s] in the Plugin [%s], Check whether it is configured in the brick configuration file",
                name, this.getClass().getName());
        (screen = screen.clone()).screen(cmd);
        cmd.setResult(screen);
    }

}
