package net.isger.brick.plugin;

import net.isger.brick.core.BaseCommand;
import net.isger.brick.core.Command;

public class UIPluginCommand extends PluginCommand {

    public UIPluginCommand() {
    }

    public UIPluginCommand(Command cmd) {
        super(cmd);
    }

    public UIPluginCommand(boolean hasShell) {
        super(hasShell);
    }

    public static UIPluginCommand getAction() {
        return cast(BaseCommand.getAction());
    }

    public static UIPluginCommand newAction() {
        return cast(BaseCommand.newAction());
    }

    public static UIPluginCommand mockAction() {
        return cast(BaseCommand.mockAction());
    }

    public static UIPluginCommand realAction() {
        return cast(BaseCommand.realAction());
    }

    public static UIPluginCommand cast(BaseCommand cmd) {
        return cmd == null || cmd.getClass() == UIPluginCommand.class ? (UIPluginCommand) cmd : cmd.infect(new UIPluginCommand(false));
    }

}
