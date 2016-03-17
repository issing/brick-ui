package net.isger.brick.ui;

public class BaseUI extends AbstractUI {

    protected Screens screens;

    protected Screen getScreen(String name) {
        return screens.get(name);
    }

}
