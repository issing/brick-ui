package net.isger.brick.ui;

import java.io.File;
import java.lang.reflect.Type;

import net.isger.brick.util.ScanLoader;
import net.isger.util.Reflects;
import net.isger.util.Strings;
import net.isger.util.reflect.ClassAssembler;
import net.isger.util.reflect.conversion.Conversion;
import net.isger.util.scan.ScanFilter;

public class ScreensConversion extends ScanLoader implements Conversion {

    private static final ScanFilter FILTER;

    private static ScreensConversion INSTANCE;

    static {
        FILTER = new ScanFilter() {
            public boolean isDeep(File root, File path) {
                return true;
            }

            public boolean accept(String name) {
                return Strings.endWithIgnoreCase(name, "Screen[.]class");
            }
        };
    }

    private ScreensConversion() {
        super(Screen.class, FILTER);
    }

    public static ScreensConversion getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ScreensConversion();
        }
        return INSTANCE;
    }

    public boolean isSupport(Type type) {
        return Screens.class.isAssignableFrom(Reflects.getRawClass(type));
    }

    public Object convert(Type type, Object res, ClassAssembler assembler) {
        return new Screens(toList(load(res, assembler)));
    }

    public String toString() {
        return Screens.class.getName();
    }
}
