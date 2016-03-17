package net.isger.brick.plugin;

import net.isger.brick.core.BaseCommand;
import net.isger.brick.ui.Screen;
import net.isger.brick.ui.Screens;
import net.isger.util.Strings;

public class UIPlugin extends BasePlugin {

    protected Screens screens;

    protected Screen getScreen(String name) {
        return screens.get(name);
    }

    public void operate() {
        if (Strings.isEmpty(PluginCommand.getAction().getName())) {
            super.operate();
        } else {
            screen();
        }
    }

    public void screen() {
        screen(getName());
    }

    protected void screen(String name) {
        Screen screen = getScreen(name).clone();
        BaseCommand cmd = BaseCommand.getAction();
        screen.operate();
        cmd.setResult(screen);
    }
}