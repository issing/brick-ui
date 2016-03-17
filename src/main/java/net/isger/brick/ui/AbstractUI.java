package net.isger.brick.ui;

import net.isger.brick.core.BaseGate;
import net.isger.util.Asserts;
import net.isger.util.Strings;

public abstract class AbstractUI extends BaseGate implements UI {

    protected abstract Screen getScreen(String name);

    public void operate() {
        if (Strings.isEmpty(UICommand.getAction().getName())) {
            super.operate();
        } else {
            screen();
        }
    }

    public void screen() {
        screen(getName());
    }

    protected void screen(String name) {
        Screen screen = getScreen(name);
        Asserts.isNotNull(screen, "Not found the screen " + name);
        screen = screen.clone();
        UICommand cmd = UICommand.getAction();
        screen.operate();
        cmd.setResult(screen);
    }

    protected String getName() {
        UICommand cmd = UICommand.getAction();
        String name = cmd.getName();
        if (Strings.isEmpty(name)) {
            throw new IllegalArgumentException(
                    "The UI name can not be null or empty");
        }
        return name.toLowerCase();
    }

}
