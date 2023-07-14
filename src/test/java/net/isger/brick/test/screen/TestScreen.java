package net.isger.brick.test.screen;

import net.isger.brick.core.BaseCommand;
import net.isger.brick.test.bean.TestBean;
import net.isger.brick.ui.BaseScreen;

public class TestScreen extends BaseScreen {

    public void greeting() {
        TestBean bean = BaseCommand.getAction().getParameter(TestBean.class);
        System.out.println("this is plugin.greeting: " + bean);
    }

}
