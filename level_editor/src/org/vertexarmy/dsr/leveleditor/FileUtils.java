package org.vertexarmy.dsr.leveleditor;

import com.beust.jcommander.internal.Lists;
import java.io.File;
import java.util.List;
import org.vertexarmy.dsr.core.Log;

/**
 * created by Alex
 * on 3/31/2015.
 */
public class FileUtils {
    private static final Log log = Log.create();

    public static List<String> discoverAvailableLevels() {
        List<String> result = Lists.newArrayList();

        File levelsDirectory = new File("levels/");
        final File[] files = levelsDirectory.listFiles();

        if (files == null) {
            log.error("Could not read the levels directory.");
        } else {
            for (File possibleLevelFile : files) {
                if (possibleLevelFile.getAbsolutePath().endsWith(".dat")) {
                    result.add(possibleLevelFile.getName().substring(0, possibleLevelFile.getName().length() - 4));
                }
            }
        }

        return result;
    }
}
