package net.isger.brick.ui;

import net.isger.brick.core.GateModule;
import net.isger.brick.inject.Scope;
import net.isger.brick.inject.anno.Scoped;

@Scoped(Scope.SINGLETON)
public class UIModule extends GateModule {

    @SuppressWarnings("unchecked")
    public Class<? extends UI> getTargetClass() {
        Class<? extends UI> uiType = (Class<? extends UI>) super
                .getTargetClass();
        if (uiType == null) {
            uiType = UI.class;
        } else if (!UI.class.isAssignableFrom(uiType)) {
            throw new IllegalArgumentException("The UI " + uiType
                    + " must implement the " + UI.class);
        }
        return uiType;
    }

    public Class<? extends UI> getImplementClass() {
        return BaseUI.class;
    }

}
