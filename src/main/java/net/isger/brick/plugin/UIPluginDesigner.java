package net.isger.brick.plugin;

import java.util.Map;

import net.isger.brick.StandardConstants;
import net.isger.brick.ui.UIDesigner;
import net.isger.util.anno.Alias;
import net.isger.util.anno.Ignore;
import net.isger.util.anno.Ignore.Mode;

public class UIPluginDesigner extends PluginDesigner {

    @Ignore(mode = Mode.INCLUDE)
    @Alias(StandardConstants.MOD_PLUGIN)
    private UIDesigner designer;

    public void design(Map<String, Object> config) {
        super.design(config);
        designer.design(config);
    }

}
