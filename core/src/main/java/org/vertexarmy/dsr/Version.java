package org.vertexarmy.dsr;

/**
 * created by Alex
 * on 3/27/2015.
 */
public class Version {
    public static final int MAJOR = 1;
    public static final int MINOR = 5;

    public static final String releaseName = "Hatchling";

    public static String value() {
        return MAJOR + "." + MINOR + " \"" + releaseName + "\"";
    }
}
