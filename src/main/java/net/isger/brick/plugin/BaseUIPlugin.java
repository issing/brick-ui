package net.isger.brick.plugin;

import net.isger.brick.core.BaseCommand;
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

    public void operate() {
        if (Strings.isEmpty(getScreen())) {
            super.operate();
        } else {
            screen();
        }
    }

    public void screen() {
        screen(getScreen());
    }

    protected void screen(String name) {
        Screen screen = getScreen(name).clone();
        BaseCommand cmd = BaseCommand.getAction();
        screen.operate();
        cmd.setResult(screen);
    }

    protected String getScreen() {
        return UICommand.getAction().getScreen();
    }
}