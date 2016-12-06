package net.isger.brick.ui;

/**
 * 荧幕
 * 
 * @author issing
 *
 */
public interface Screen extends Cloneable {

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
