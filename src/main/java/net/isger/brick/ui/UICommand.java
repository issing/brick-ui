package net.isger.brick.ui;

import net.isger.brick.core.BaseCommand;
import net.isger.brick.core.Command;
import net.isger.brick.plugin.PluginCommand;

public class UICommand extends PluginCommand {

    public static final String KEY_SCREEN = "ui-screen";

    public UICommand() {
    }

    public UICommand(Command cmd) {
        super(cmd);
    }

    public UICommand(boolean hasShell) {
        super(hasShell);
    }

    public static UICommand getAction() {
        return cast(BaseCommand.getAction());
    }

    public static UICommand newAction() {
        return cast(BaseCommand.newAction());
    }

    public static UICommand mockAction() {
        return cast(BaseCommand.mockAction());
    }

    public static UICommand realAction() {
        return cast(BaseCommand.realAction());
    }

    public static UICommand cast(BaseCommand cmd) {
        return cmd == null || cmd.getClass() == UICommand.class ? (UICommand) cmd
                : cmd.infect(new UICommand(false));
    }

    public String getScreen() {
        return getHeader(KEY_SCREEN);
    }

    public void setScreen(String screen) {
        setHeader(KEY_SCREEN, screen);
    }

}
