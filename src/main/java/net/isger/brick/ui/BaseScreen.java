package net.isger.brick.ui;

import java.util.HashMap;
import java.util.Map;

import net.isger.brick.Constants;
import net.isger.brick.core.Console;
import net.isger.brick.plugin.PluginOperator;
import net.isger.util.Asserts;
import net.isger.util.Reflects;
import net.isger.util.anno.Alias;
import net.isger.util.anno.Ignore;
import net.isger.util.anno.Ignore.Mode;
import net.isger.util.reflect.BoundField;

@Ignore
public class BaseScreen implements Screen {

    /** 控制台 */
    @Ignore(mode = Mode.INCLUDE)
    @Alias(Constants.SYSTEM)
    private Console console;

    private PluginOperator operator;

    @Ignore(mode = Mode.INCLUDE)
    protected int code;

    @Ignore(mode = Mode.INCLUDE)
    protected String message;

    @Ignore(mode = Mode.INCLUDE)
    protected Object result;

    /** 指示参数 */
    private Map<String, Object> directs;

    public BaseScreen() {
        operator = new PluginOperator(this);
        directs = new HashMap<String, Object>();
        direct("name", "");
    }

    protected void direct(String name, Object value) {
        directs.put("@" + name, value);
    }

    /**
     * 空操作
     */
    public void operate() {
    }

    public void screen(UICommand cmd) {
        operator.operate(cmd);
    }

    public Object see(String name, Object... params) {
        Object result = null;
        see: {
            if (name != null) {
                if (name.startsWith("@")) {
                    result = directs.get(name);
                } else {
                    BoundField field = Reflects.getBoundField(getClass(), name);
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

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getResult() {
        return result;
    }

    public Screen clone() {
        BaseScreen screen;
        try {
            screen = (BaseScreen) super.clone();
        } catch (CloneNotSupportedException e) {
            throw Asserts.state("Failure to clone screen", e);
        }
        screen.operator = new PluginOperator(screen);
        screen.directs = new HashMap<String, Object>(directs);
        return screen;
    }

}
