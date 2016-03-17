package net.isger.brick;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import net.isger.brick.core.Console;
import net.isger.brick.core.ConsoleManager;
import net.isger.brick.core.Module;
import net.isger.brick.inject.ContainerProvider;
import net.isger.brick.plugin.PluginCommand;
import net.isger.brick.plugin.UIPluginModule;
import net.isger.brick.ui.UICommand;
import net.isger.brick.ui.UIDesigner;

public class BrickUITest extends TestCase {

    private ConsoleManager manager;

    public BrickUITest(String testName) {
        super(testName);
        manager = new ConsoleManager();
        manager.getContainerProviders();
        manager.addContainerProvider(new ContainerProvider() {
            public void register(net.isger.brick.inject.ContainerBuilder builder) {
                builder.factory(Module.class, Constants.MOD_PLUGIN,
                        UIPluginModule.class);
                builder.factory(UIDesigner.class, Constants.MOD_PLUGIN);
            }

            public boolean isReload() {
                return false;
            }
        });
    }

    public static Test suite() {
        return new TestSuite(BrickUITest.class);
    }

    public void testUI() {
        Console console = manager.getConsole();
        // 调用界面
        PluginCommand cmd = new UICommand();
        cmd.setDomain("test");
        cmd.setName("test");
        cmd.setOperate("test");
        cmd.setParameter("id", "1");
        cmd.setParameter("name", "first");
        console.execute(cmd);

        // 调用插件
        cmd = PluginCommand.cast(cmd);
        cmd.setParameter("test", "this is test.");
        console.execute(cmd);

        assertTrue(true);
    }

}
