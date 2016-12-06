package net.isger.brick.auth;

import net.isger.brick.core.BaseCommand;
import net.isger.brick.plugin.PluginHandler;
import net.isger.brick.ui.UICommand;
import net.isger.brick.ui.UIConstants;

public class ScreenHandler extends PluginHandler {

    public ScreenHandler() {
        this.setOperate(UIConstants.OPERATE_SCREEN);
    }

    public BaseCommand toCommand(Object message) {
        AuthCommand cmd = (AuthCommand) message;
        Object token = cmd.getToken();
        if (token instanceof UICommand) {
            toCommand((UICommand) token);
            cmd.setDomain(null);
            cmd.setOperate(null);
            return cmd;
        }
        return null;
    }

}
