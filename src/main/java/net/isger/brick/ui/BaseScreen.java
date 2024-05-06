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

    private volatile transient PluginOperator operator;

    /** 控制台 */
    @Alias(Constants.SYSTEM)
    @Ignore(mode = Mode.INCLUDE, serialize = false)
    protected Console console;

    /** 开发标识 */
    @Ignore(mode = Mode.INCLUDE, serialize = false)
    protected boolean develop;

    @Ignore(mode = Mode.INCLUDE)
    protected int code;

    @Ignore(mode = Mode.INCLUDE)
    protected String message;

    @Ignore(mode = Mode.INCLUDE)
    protected Object result;

    @Ignore(mode = Mode.INCLUDE)
    protected String state;

    /** 指示参数 */
    private volatile transient Map<String, Object> directs;

    public BaseScreen() {
        this.operator = new PluginOperator(this);
        this.directs = new HashMap<String, Object>();
        this.direct("name", "");
    }

    protected void direct(String name, Object value) {
        this.directs.put("@" + name, value);
    }

    /**
     * 空操作
     */
    public void operate() {
    }

    public void screen(UICommand cmd) {
        this.operator.operate(cmd);
    }

    public Object see(String name, Object... params) {
        Object result = null;
        see: {
            if (name != null) {
                if (name.startsWith("@")) {
                    result = this.directs.get(name);
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

    public String getState() {
        return state;
    }

    /**
     * 设置响应
     * 
     * @param code
     * @param message
     */
    protected final void setResponse(int code, String message) {
        this.setResponse(code, message, null, null);
    }

    /**
     * 设置响应
     *
     * @param code
     * @param message
     * @param result
     */
    protected final void setResponse(int code, String message, Object result) {
        this.setResponse(code, message, result, null);
    }

    /**
     * 设置响应
     * 
     * @param code
     * @param message
     * @param result
     * @param state
     */
    protected final void setResponse(int code, String message, Object result, String state) {
        this.code = code;
        this.message = message;
        if (result instanceof Throwable) {
            if (this.develop) {
                ((Throwable) result).printStackTrace();
            }
        } else {
            this.result = result;
        }
        this.state = state;
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
