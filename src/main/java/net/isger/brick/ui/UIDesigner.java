package net.isger.brick.ui;

import net.isger.brick.inject.Scope;
import net.isger.brick.util.AbstractDesigner;
import net.isger.brick.util.anno.Scoped;

@Scoped(Scope.SINGLETON)
public class UIDesigner extends AbstractDesigner {

    protected void prepare() {
        addConversion(ScreensConversion.getInstance());
    }

}
