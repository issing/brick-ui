package net.isger.brick.ui;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.isger.brick.plugin.PluginOperator;
import net.isger.util.Reflects;
import net.isger.util.anno.Ignore;
import net.isger.util.anno.Ignore.Mode;
import net.isger.util.reflect.BoundField;

@Ignore
public class BaseScreen implements Screen {

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
        this.operator = new PluginOperator(this);
        this.directs = new ConcurrentHashMap<String, Object>();
        this.direct("name", "");
    }

    protected void direct(String name, Object value) {
        directs.put("@" + name, value);
    }

    public void screen(UICommand cmd) {
        operator.operate(cmd);
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
            throw new IllegalStateException("Failure to clone screen", e);
        }
        screen.operator = new PluginOperator(screen);
        screen.directs = new ConcurrentHashMap<String, Object>(this.directs);
        return screen;
    }
}
