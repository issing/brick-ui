package net.isger.brick.ui;

import net.isger.util.Operator;

public interface Screen extends Operator, Cloneable {

    public Object see(String name, Object... params);

    public Screen clone();

}
