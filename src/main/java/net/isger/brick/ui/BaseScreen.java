package net.isger.brick.ui;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.isger.brick.core.Command;
import net.isger.brick.core.Gate;
import net.isger.brick.plugin.Plugin;
import net.isger.brick.plugin.PluginCommand;
import net.isger.brick.plugin.PluginTarget;
import net.isger.util.Reflects;
import net.isger.util.anno.Ignore;
import net.isger.util.anno.Ignore.Mode;
import net.isger.util.reflect.BoundField;

@Ignore
public class BaseScreen extends PluginTarget implements Screen {

    /** 访问路径 */
    @Ignore(mode = Mode.INCLUDE)
    private String path;

    /** 指示参数 */
    private Map<String, Object> directs;

    public BaseScreen() {
        this.directs = new ConcurrentHashMap<String, Object>();
        this.direct("name", "");
    }

    protected String getPath() {
        return this.path;
    }

    protected void direct(String name, Object value) {
        directs.put("@" + name, value);
    }

    protected final UICommand getUICommand() {
        return UICommand.cast(super.getCommand());
    }

    protected <T extends Command> T toExecute(T cmd) {
        getConsole().execute(cmd);
        return cmd;
    }

    protected PluginCommand toPlugin(String domain) {
        PluginCommand cmd = mockPluginCommand();
        cmd.setDomain(domain);
        try {
            getConsole().execute(cmd);
        } finally {
            realCommand();
        }
        return cmd;
    }

    protected PluginCommand toPlugin(String domain, String name) {
        PluginCommand cmd = mockPluginCommand();
        cmd.setDomain(domain);
        cmd.setName(name);
        try {
            getConsole().execute(cmd);
        } finally {
            realCommand();
        }
        return cmd;
    }

    protected PluginCommand toPlugin(String domain, String name, String operate) {
        PluginCommand cmd = mockPluginCommand();
        cmd.setDomain(domain);
        cmd.setName(name);
        cmd.setOperate(operate);
        try {
            getConsole().execute(cmd);
        } finally {
            realCommand();
        }
        return cmd;
    }

    protected Command toService() {
        Command cmd;
        Gate gate = getGate();
        if (gate instanceof Plugin) {
            ((Plugin) gate).service();
            cmd = getCommand();
        } else {
            cmd = getPluginCommand();
            getConsole().execute(cmd);
        }
        return cmd;
    }

    protected PluginCommand toService(String operate) {
        PluginCommand cmd = mockPluginCommand();
        cmd.setOperate(operate);
        try {
            toService();
        } finally {
            realCommand();
        }
        return cmd;
    }

    protected PluginCommand toService(String name, String operate) {
        PluginCommand cmd = mockPluginCommand();
        cmd.setName(name);
        cmd.setOperate(operate);
        try {
            toService();
        } finally {
            realCommand();
        }
        return cmd;
    }

    public Object see(String name, Object... params) {
        Object result = null;
        see: {
            if (name != null) {
                if (name.startsWith("@")) {
                    result = this.directs.get(name);
                } else {
                    BoundField field = Reflects.getBoundField(this.getClass(),
                            name);
                    if (field != null) {
                        result = field.getValue(this);
                    }
                }
                if (result != null) {
                    break see;
                }
            }
            if (params != null && params.length > 0) {
                result = params.length == 1 ? params[0] : params;
            }
        }
        return result;
    }

    public Screen clone() {
        BaseScreen screen = (BaseScreen) super.clone();
        screen.directs = new ConcurrentHashMap<String, Object>(this.directs);
        return screen;
    }
}
