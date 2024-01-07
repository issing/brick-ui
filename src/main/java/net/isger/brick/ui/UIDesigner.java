package net.isger.brick.ui;

import net.isger.brick.inject.Scope;
import net.isger.brick.util.AbstractDesigner;
import net.isger.brick.util.anno.Scoped;

/**
 * UI设计器
 * 
 * @author issing
 */
@Scoped(Scope.SINGLETON)
public class UIDesigner extends AbstractDesigner {

    protected void prepare() {
        this.addConversion(ScreensConversion.getInstance());
    }

}
