package net.isger.brick.ui;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.isger.brick.Constants;
import net.isger.brick.core.Console;
import net.isger.brick.plugin.PluginOperator;
import net.isger.brick.stub.model.Meta;
import net.isger.util.Callable;
import net.isger.util.Helpers;
import net.isger.util.Reflects;
import net.isger.util.anno.Alias;
import net.isger.util.anno.Ignore;
import net.isger.util.anno.Ignore.Mode;
import net.isger.util.reflect.BoundField;
import net.isger.util.reflect.TypeToken;

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
        this.operator = new PluginOperator(this);
        this.directs = new HashMap<String, Object>();
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
                    BoundField field = Reflects.getBoundField(this.getClass(), name);
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

    /**
     * TODO 不完善（需要重点改进）
     *
     * @return
     */
    public Callable<Object> createAssemble() {
        return new Callable<Object>() {
            @SuppressWarnings("unchecked")
            public Object call(Object... args) {
                BoundField field = (BoundField) args[0];
                ResultMeta resultMeta = createResultMeta(field);
                Map<String, Object> row = (Map<String, Object>) args[3]; // 行值
                String fieldName = Helpers.toFieldName(resultMeta.sourceColumn);
                Object fieldValue = Helpers.getInstance(row, fieldName);
                if (args[2] == Reflects.UNKNOWN) {
                    args[2] = fieldValue;
                }
                /* 获取元素类型 */
                TypeToken<?> typeToken = field.getToken();
                Class<?> rawClass = typeToken.getRawClass();
                if (Collection.class.isAssignableFrom(rawClass)) {
                    rawClass = (Class<?>) Reflects.getActualType(typeToken.getType());
                } else if (rawClass.isArray()) {
                    rawClass = (Class<?>) Reflects.getComponentType(typeToken.getType());
                }
                if (rawClass.isInterface()) {
                    rawClass = console.getContainer().getInstance(Class.class, (Helpers.toColumnName(rawClass.getSimpleName()).replaceAll("[_]", ".") + ".class").substring(1));
                }
                if (!(args[2] instanceof Map)) {
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put(resultMeta.targetField, args[2]);
                    args[2] = params;
                }
                return Reflects.newInstance(rawClass, (Map<String, Object>) args[2]);
            }
        };
    }

    @SuppressWarnings("unchecked")
    private ResultMeta createResultMeta(BoundField field) {
        ResultMeta resultMeta = new ResultMeta();
        resultMeta.meta = Meta.createMeta(field); // 元字段
        if (resultMeta.meta.toModel() == null) {
            resultMeta.sourceColumn = resultMeta.meta.getName();
            resultMeta.targetField = (String) resultMeta.meta.getValue();
        } else {
            Map<String, Object> params = (Map<String, Object>) resultMeta.meta.getValue();
            Map<String, Object> source = (Map<String, Object>) params.get("source");
            resultMeta.sourceColumn = (String) source.get("name");
            Map<String, Object> target = (Map<String, Object>) params.get("target");
            resultMeta.targetField = Helpers.toFieldName((String) target.get("name"));
        }
        return resultMeta;
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
        screen.directs = new HashMap<String, Object>(this.directs);
        return screen;
    }

    private class ResultMeta {
        Meta meta;
        String sourceColumn;
        String targetField;
    }
}
