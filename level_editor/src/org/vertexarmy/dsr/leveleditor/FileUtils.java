package org.vertexarmy.dsr.leveleditor;

import com.beust.jcommander.internal.Lists;
import org.vertexarmy.dsr.core.Log;

import java.io.File;
import java.util.List;

/**
 * created by Alex
 * on 3/31/2015.
 */
public class FileUtils {
    private static final Log log = Log.create();

    public static List<File> discoverFiles(File path, List<String> extensions) {
        List<File> result = Lists.newArrayList();

        final File[] files = path.listFiles();

        if (files == null) {
            log.error("Could not read the root directory.");
        } else {
            for (File file : files) {
                if (file.isFile()) {
                    extensions.contains(getExtension(file));
                    result.add(file);
                }
            }
        }

        return result;
    }

    public static String getExtension(File file) {
        if (file.isDirectory()) {
            return "";
        }

        int dotIndex = file.getName().lastIndexOf('.');

        if (dotIndex == -1) {
            return "";
        }

        return file.getName().substring(dotIndex + 1);
    }
}
