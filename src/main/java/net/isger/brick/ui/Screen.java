package net.isger.brick.ui;

/**
 * 荧幕
 * 
 * @author issing
 *
 */
public interface Screen extends Cloneable {

    public static final String DIRECT_STREAM = "stream";

    public static final String DIRECT_CONTENT_TYPE = "contentType";

    public static final String DIRECT_NAME = "name";

    public static final String DIRECT_LOCATION = "location";

    public static final String DIRECT_TARGET = "target";

    /**
     * 荧幕入口
     * 
     * @param cmd
     */
    public void screen(UICommand cmd);

    /**
     * 荧幕看点
     * 
     * @param name
     * @param params
     * @return
     */
    public Object see(String name, Object... params);

    /**
     * 荧幕克隆
     * 
     * @return
     */
    public Screen clone();

}
