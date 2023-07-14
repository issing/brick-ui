package net.isger.brick;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import net.isger.brick.core.Console;
import net.isger.brick.core.ConsoleManager;
import net.isger.brick.plugin.PluginCommand;
import net.isger.brick.plugin.UIPluginCommand;
import net.isger.brick.ui.UICommand;

public class BrickUITest extends TestCase {

    private ConsoleManager manager;

    public BrickUITest(String testName) {
        super(testName);
        manager = new ConsoleManager();
        manager.load();
    }

    public static Test suite() {
        return new TestSuite(BrickUITest.class);
    }

    public void testUI() {
        Console console = manager.getConsole();
        // 调用界面
        UICommand cmd = new UICommand();
        cmd.setDomain("test");
        cmd.setName("test");
        cmd.setOperate("greeting");
        cmd.setParameter("id", "1");
        cmd.setParameter("name", "first");
        console.execute(cmd);

        // 调用插件
        PluginCommand pcmd = UIPluginCommand.cast(cmd);
        pcmd.setParameter("test", "this is test.");
        console.execute(pcmd);

        assertTrue(true);
    }

}
