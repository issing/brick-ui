package net.isger.brick.ui;

import net.isger.brick.core.BaseGate;
import net.isger.util.Asserts;
import net.isger.util.Strings;

public abstract class AbstractUI extends BaseGate implements UI {

    protected abstract Screen getScreen(String name);

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
        Screen screen = getScreen(name);
        Asserts.isNotNull(screen, "Not found the screen " + name);
        screen = screen.clone();
        UICommand cmd = UICommand.getAction();
        screen.operate();
        cmd.setResult(screen);
    }

    protected String getScreen() {
        return UICommand.getAction().getScreen();
    }

}
